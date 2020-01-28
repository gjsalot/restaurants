package com.grantsutcliffe.restaurants.search.di

import android.content.res.Resources
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.core.rx.Schedulers
import com.grantsutcliffe.restaurants.search.activity.SearchActivity
import com.grantsutcliffe.restaurants.search.adapter.RestaurantAdapter
import com.grantsutcliffe.restaurants.search.presenter.SearchAction
import com.grantsutcliffe.restaurants.search.presenter.SearchPresenter
import com.grantsutcliffe.restaurants.search.presenter.SearchUiEvent
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton


@Module
class SearchActivityModule {

    private val searchUiEvents = PublishSubject.create<SearchUiEvent>()

    @Provides
    internal fun provideSearchView(
        searchActivity: SearchActivity
    ): PresenterView<SearchViewModel, SearchAction, SearchUiEvent> = searchActivity

    @Provides
    internal fun provideSearchPresenter(
        restaurantsRepository: RestaurantsRepository,
        schedulers: Schedulers
    ): SearchPresenter = SearchPresenter(restaurantsRepository, schedulers)

    @Provides
    internal fun provideSearchUiEvents(): PublishSubject<SearchUiEvent> = searchUiEvents

    @Provides
    internal fun provideRestaurantsAdapter(
        searchUiEvents: PublishSubject<SearchUiEvent>,
        resources: Resources
    ): RestaurantAdapter = RestaurantAdapter(searchUiEvents, resources)

}