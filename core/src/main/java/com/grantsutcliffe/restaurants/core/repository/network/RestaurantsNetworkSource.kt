package com.grantsutcliffe.restaurants.core.repository.network

import com.grantsutcliffe.restaurants.core.model.GetRestaurantsResponse
import com.grantsutcliffe.restaurants.core.model.Restaurant
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET

class RestaurantsNetworkSource(retrofit: Retrofit) {

    private val api = retrofit.create(Api::class.java)

    fun getRestaurants(): Single<List<Restaurant>> = api.getRestaurants()
        .map(GetRestaurantsResponse::results)

    interface Api {

        @GET("restaurants")
        fun getRestaurants(): Single<GetRestaurantsResponse>

    }

}