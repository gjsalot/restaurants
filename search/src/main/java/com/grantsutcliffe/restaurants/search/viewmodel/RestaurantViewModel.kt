package com.grantsutcliffe.restaurants.search.viewmodel

import com.grantsutcliffe.restaurants.core.ViewModel
import com.grantsutcliffe.restaurants.search.model.RestaurantDetailUI

sealed class RestaurantViewModel: ViewModel {

    object Loading : RestaurantViewModel()

    object Error : RestaurantViewModel()

    data class Result(
        val restaurant: RestaurantDetailUI
    ) : RestaurantViewModel()

}