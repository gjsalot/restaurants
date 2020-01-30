package com.grantsutcliffe.restaurants.core.repository

import com.grantsutcliffe.restaurants.core.model.Restaurant
import com.grantsutcliffe.restaurants.core.repository.network.RestaurantsNetworkSource
import io.reactivex.Observable

open class RestaurantsRepository(
    private val networkSource: RestaurantsNetworkSource
) {

    // TODO: Improvement: Could store in a local db source and expose a reactive stream from the db.
    open fun getRestaurants(): Observable<List<Restaurant>> = networkSource.getRestaurants()
        .toObservable()

    open fun getRestaurant(id: String): Observable<Restaurant> = networkSource.getRestaurant(id)
        .toObservable()

}