package com.grantsutcliffe.restaurants.search.presenter

import com.grantsutcliffe.restaurants.core.UiEvent

sealed class RestaurantUiEvent : UiEvent {
    object TryAgainClicked: RestaurantUiEvent()
}