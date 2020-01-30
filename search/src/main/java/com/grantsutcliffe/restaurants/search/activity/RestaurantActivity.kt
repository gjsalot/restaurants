package com.grantsutcliffe.restaurants.search.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.search.R
import com.grantsutcliffe.restaurants.search.adapter.RestaurantAdapter
import com.grantsutcliffe.restaurants.search.presenter.*
import com.grantsutcliffe.restaurants.search.viewmodel.RestaurantViewModel
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel
import dagger.android.AndroidInjection
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_restaurant.*
import javax.inject.Inject

class RestaurantActivity : AppCompatActivity(), PresenterView<RestaurantViewModel, RestaurantAction, RestaurantUiEvent> {

    @Inject override lateinit var presenter: RestaurantPresenter
    @Inject override lateinit var uiEvents: PublishSubject<RestaurantUiEvent>

    private val idTextView by lazy { findViewById<TextView>(R.id.idTextView) }
    private val constraintLayout by lazy { findViewById<ConstraintLayout>(R.id.constraintLayout) }
    private val tryAgainButton by lazy { findViewById<Button>(R.id.tryAgainButton) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    private val loadingConstraintSet = ConstraintSet()
    private val resultConstraintSet = ConstraintSet()
    private val errorConstraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        initConstraints()

        tryAgainButton.setOnClickListener { uiEvents.onNext(RestaurantUiEvent.TryAgainClicked) }
    }

    private fun initConstraints() {
        loadingConstraintSet.clone(constraintLayout)
        loadingConstraintSet.apply {
            setVisibility(progressBar.id, ConstraintSet.VISIBLE)
        }

        resultConstraintSet.clone(constraintLayout)

        errorConstraintSet.clone(constraintLayout)
        errorConstraintSet.apply {
            setVisibility(tryAgainButton.id, ConstraintSet.VISIBLE)
        }
    }

    override fun render(viewModel: RestaurantViewModel) {
        when (viewModel) {
            is RestaurantViewModel.Loading -> renderLoading()
            is RestaurantViewModel.Error -> renderError()
            is RestaurantViewModel.Result -> renderResult(viewModel)
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

    private fun renderResult(viewModel: RestaurantViewModel.Result) {
        TransitionManager.beginDelayedTransition(constraintLayout)
        resultConstraintSet.applyTo(constraintLayout)

        // TODO: Show restaurant details
        idTextView.text = viewModel.restaurant.id
        descriptionTextView.text = viewModel.restaurant.description
    }

    override fun performAction(action: RestaurantAction) {
        when (action) {
            // No actions yet
        }
    }

    companion object {
        const val BUNDLE_ARG_RESTAURANT_ID = "restaurantId"
    }
}

