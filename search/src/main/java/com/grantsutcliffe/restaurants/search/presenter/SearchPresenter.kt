package com.grantsutcliffe.restaurants.search.presenter

import android.util.Log
import com.grantsutcliffe.restaurants.core.Presenter
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.core.rx.Schedulers
import com.grantsutcliffe.restaurants.search.model.RestaurantUI
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class SearchPresenter(
    private val restaurantsRepository: RestaurantsRepository,
    private val schedulers: Schedulers
) : Presenter<SearchViewModel, SearchAction, SearchUiEvent>() {

    override fun onStart(view: PresenterView<SearchViewModel, SearchAction, SearchUiEvent>) {
        val restaurantsSubject = BehaviorSubject.create<List<RestaurantUI>>()
        restaurantsSubject.onNext(emptyList())
        view.uiEvents
            .ofType(SearchUiEvent.ScrolledToBottom::class.java)
            .startWith(SearchUiEvent.ScrolledToBottom)
            .flatMapSingle {
                restaurantsRepository.getRestaurants().firstOrError()
                    .subscribeOn(schedulers.io)
                    .observeOn(schedulers.computation)
                    .map { it.map(RestaurantUI.Companion::from) }
            }
            .withLatestFrom(restaurantsSubject, BiFunction {
                    restaurants: List<RestaurantUI>, newRestaurants: List<RestaurantUI> ->
                restaurants + newRestaurants
            })
            .subscribe { restaurantsSubject.onNext(it) }
            .disposeOnStop()

        restaurantsSubject
            .observeOn(schedulers.ui)
            .subscribe {
                view.render(SearchViewModel.Result(it))
            }
            .disposeOnStop()

//        restaurantsRepository.getRestaurants()
//            .firstOrError()
//            .subscribeOn(schedulers.io)
//            .observeOn(schedulers.computation)
//            .map { SearchViewModel.Result(it.map(RestaurantUI.Companion::from)) as SearchViewModel }
//            .observeOn(schedulers.ui)
//            .doOnSubscribe { view.render(SearchViewModel.Loading) }
//            .doOnError {
//                view.render(SearchViewModel.Error)
//
//                Log.d(SearchPresenter::class.java.name, "Error loading restaurants", it)
//            }
//            .retryWhen { view.uiEvents.ofType(SearchUiEvent.TryAgainClicked::class.java) }
//            .subscribe {
//                view.render(it)
//            }
//            .disposeOnStop()

        view.uiEvents
            .subscribe({
                when (it) {
                    is SearchUiEvent.CallClicked -> view.performAction(SearchAction.Call(it.restaurantUI.phoneNumberUri))
                    is SearchUiEvent.RestaurantClicked -> view.performAction(SearchAction.ShowDetail(it.restaurantUI.id))
                }
            }, {
                Log.d(SearchPresenter::class.java.name, "Error on ui event", it)
            })
            .disposeOnStop()
    }

}