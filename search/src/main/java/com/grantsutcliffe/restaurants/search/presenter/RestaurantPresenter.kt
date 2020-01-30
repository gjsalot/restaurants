package com.grantsutcliffe.restaurants.search.presenter

import android.util.Log
import com.grantsutcliffe.restaurants.core.Presenter
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.core.rx.Schedulers
import com.grantsutcliffe.restaurants.search.model.RestaurantDetailUI
import com.grantsutcliffe.restaurants.search.model.RestaurantUI
import com.grantsutcliffe.restaurants.search.viewmodel.RestaurantViewModel
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel

class RestaurantPresenter(
    private val restaurantsRepository: RestaurantsRepository,
    private val schedulers: Schedulers,
    private val restaurantId: String
) : Presenter<RestaurantViewModel, RestaurantAction, RestaurantUiEvent>() {

    override fun onStart(view: PresenterView<RestaurantViewModel, RestaurantAction, RestaurantUiEvent>) {
        restaurantsRepository.getRestaurant(restaurantId)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.computation)
            .map { RestaurantViewModel.Result(RestaurantDetailUI.from(it)) }
            .observeOn(schedulers.ui)
            .doOnSubscribe { view.render(RestaurantViewModel.Loading) }
            .doOnError {
                view.render(RestaurantViewModel.Error)

                Log.d(RestaurantPresenter::class.java.name, "Error loading restaurant", it)
            }
            .retryWhen { view.uiEvents.ofType(RestaurantUiEvent.TryAgainClicked::class.java) }
            .subscribe {
                view.render(it)
            }
            .disposeOnStop()
    }

}