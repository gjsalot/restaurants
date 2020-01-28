package com.grantsutcliffe.restaurants.search.presenter

import android.util.Log
import com.grantsutcliffe.restaurants.core.Presenter
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.core.rx.Schedulers
import com.grantsutcliffe.restaurants.search.model.RestaurantUI
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel

class SearchPresenter(
    private val restaurantsRepository: RestaurantsRepository,
    private val schedulers: Schedulers
) : Presenter<SearchViewModel, SearchAction, SearchUiEvent>() {

    override fun onStart(view: PresenterView<SearchViewModel, SearchAction, SearchUiEvent>) {
        restaurantsRepository.getRestaurants()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.computation)
            .map { SearchViewModel.Result(it.map(RestaurantUI.Companion::from)) as SearchViewModel }
            .observeOn(schedulers.ui)
            .doOnSubscribe { view.render(SearchViewModel.Loading) }
            .doOnError {
                view.render(SearchViewModel.Error)

                Log.d(SearchPresenter::class.java.name, "Error loading restaurants", it)
            }
            .retryWhen { view.uiEvents.ofType(SearchUiEvent.TryAgainClicked::class.java) }
            .subscribe {
                view.render(it)
            }
            .disposeOnStop()

        view.uiEvents
            .ofType(SearchUiEvent.CallClicked::class.java)
            .subscribe({
                view.performAction(SearchAction.Call(it.restaurantUI.phoneNumberUri))
            }, {
                Log.d(SearchPresenter::class.java.name, "Error calling restaurant", it)
            })
            .disposeOnStop()
    }

}