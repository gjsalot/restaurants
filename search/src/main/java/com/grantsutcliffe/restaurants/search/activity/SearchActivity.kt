package com.grantsutcliffe.restaurants.search.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.search.R
import com.grantsutcliffe.restaurants.search.adapter.RestaurantAdapter
import com.grantsutcliffe.restaurants.search.presenter.SearchAction
import com.grantsutcliffe.restaurants.search.presenter.SearchPresenter
import com.grantsutcliffe.restaurants.search.presenter.SearchUiEvent
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel
import dagger.android.AndroidInjection
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), PresenterView<SearchViewModel, SearchAction, SearchUiEvent> {

    @Inject override lateinit var presenter: SearchPresenter
    @Inject override lateinit var uiEvents: PublishSubject<SearchUiEvent>
    @Inject lateinit var adapter: RestaurantAdapter

    private val constraintLayout by lazy { findViewById<ConstraintLayout>(R.id.constraintLayout) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val tryAgainButton by lazy { findViewById<Button>(R.id.tryAgainButton) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    private val loadingConstraintSet = ConstraintSet()
    private val resultConstraintSet = ConstraintSet()
    private val errorConstraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initRecyclerView()
        initConstraints()

        tryAgainButton.setOnClickListener { uiEvents.onNext(SearchUiEvent.TryAgainClicked) }
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initConstraints() {
        loadingConstraintSet.clone(constraintLayout)

        resultConstraintSet.clone(constraintLayout)
        resultConstraintSet.apply {
            setVisibility(recyclerView.id, ConstraintSet.VISIBLE)
            setVisibility(progressBar.id, ConstraintSet.GONE)
        }

        errorConstraintSet.clone(constraintLayout)
        errorConstraintSet.apply {
            setVisibility(tryAgainButton.id, ConstraintSet.VISIBLE)
            setVisibility(progressBar.id, ConstraintSet.GONE)
        }
    }

    override fun render(viewModel: SearchViewModel) {
        when (viewModel) {
            is SearchViewModel.Loading -> renderLoading()
            is SearchViewModel.Error -> renderError()
            is SearchViewModel.Result -> renderResult(viewModel)
        }
    }

    private fun renderLoading() {
        TransitionManager.beginDelayedTransition(constraintLayout)
        loadingConstraintSet.applyTo(constraintLayout)
    }

    private fun renderError() {
        TransitionManager.beginDelayedTransition(constraintLayout)
        errorConstraintSet.applyTo(constraintLayout)
    }

    private fun renderResult(viewModel: SearchViewModel.Result) {
        TransitionManager.beginDelayedTransition(constraintLayout)
        resultConstraintSet.applyTo(constraintLayout)

        adapter.items = viewModel.restaurants
    }

    override fun performAction(action: SearchAction) {
        when (action) {
            is SearchAction.Call -> handleCallAction(action.phoneNumberUri)
        }
    }

    private fun handleCallAction(phoneNumberUri: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(phoneNumberUri)
        startActivity(intent)
    }
}

