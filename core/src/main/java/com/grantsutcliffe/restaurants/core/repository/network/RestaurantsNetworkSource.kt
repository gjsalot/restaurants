package com.grantsutcliffe.restaurants.core.repository.network

import com.grantsutcliffe.restaurants.core.model.GetRestaurantsResponse
import com.grantsutcliffe.restaurants.core.model.Restaurant
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class RestaurantsNetworkSource(retrofit: Retrofit) {

    private val api = retrofit.create(Api::class.java)

    fun getRestaurants(): Single<List<Restaurant>> = api.getRestaurants()
        .map(GetRestaurantsResponse::results)

    fun getRestaurant(id: String): Single<Restaurant> = api.getRestaurant(id)

    interface Api {

        @GET("restaurants2")
        fun getRestaurants(): Single<GetRestaurantsResponse>

        @GET("{id}")
        fun getRestaurant(@Path("id") id: String): Single<Restaurant>

    }

}