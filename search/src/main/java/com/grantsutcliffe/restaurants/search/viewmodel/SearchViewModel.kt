package com.grantsutcliffe.restaurants.search.viewmodel

import com.grantsutcliffe.restaurants.core.ViewModel
import com.grantsutcliffe.restaurants.search.model.RestaurantUI

sealed class SearchViewModel: ViewModel {

    object Loading : SearchViewModel()

    object Error : SearchViewModel()

    data class Result(
        val restaurants: List<RestaurantUI>
    ) : SearchViewModel()

}