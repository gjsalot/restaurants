package com.grantsutcliffe.restaurants.search.model

import com.grantsutcliffe.restaurants.core.model.Category
import com.grantsutcliffe.restaurants.core.model.Restaurant

data class RestaurantUI(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val iconUrl: String,
    val phoneNumberUri: String,
    val categories: String
) {

    companion object {
        fun from(restaurant: Restaurant): RestaurantUI = restaurant.run {
            RestaurantUI(
                id,
                title,
                subtitle,
                icons.result_list.src,
                icons.list_badge,
                primary_action.uri,
                categories.map(Category::name).joinToString(" • ")
            )
        }
    }

}