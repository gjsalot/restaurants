package com.grantsutcliffe.restaurants.search.presenter

import com.grantsutcliffe.restaurants.core.UiEvent
import com.grantsutcliffe.restaurants.search.model.RestaurantUI

sealed class SearchUiEvent : UiEvent {

    data class CallClicked(val restaurantUI: RestaurantUI): SearchUiEvent()

    data class RestaurantClicked(val restaurantUI: RestaurantUI) : SearchUiEvent()

    object TryAgainClicked: SearchUiEvent()

    object ScrolledToBottom: SearchUiEvent()

}