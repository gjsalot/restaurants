package com.grantsutcliffe.restaurants.search.di

import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.core.rx.Schedulers
import com.grantsutcliffe.restaurants.search.activity.RestaurantActivity
import com.grantsutcliffe.restaurants.search.presenter.*
import com.grantsutcliffe.restaurants.search.viewmodel.RestaurantViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject


@Module
class RestaurantActivityModule {

    private val restaurantsUiEvents = PublishSubject.create<RestaurantUiEvent>()

    @Provides
    internal fun provideRestaurantView(
        restaurantActivity: RestaurantActivity
    ): PresenterView<RestaurantViewModel, RestaurantAction, RestaurantUiEvent> = restaurantActivity

    @Provides
    internal fun provideRestaurantPresenter(
        restaurantsRepository: RestaurantsRepository,
        schedulers: Schedulers,
        restaurantActivity: RestaurantActivity
    ): RestaurantPresenter = RestaurantPresenter(restaurantsRepository, schedulers, restaurantActivity.intent.getStringExtra(RestaurantActivity.BUNDLE_ARG_RESTAURANT_ID)!!)

    @Provides
    internal fun provideRestaurantUiEvents(): PublishSubject<RestaurantUiEvent> = restaurantsUiEvents

}