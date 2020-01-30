package com.grantsutcliffe.restaurants.search.model

import android.text.Spannable
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.grantsutcliffe.restaurants.core.model.Category
import com.grantsutcliffe.restaurants.core.model.Restaurant

data class RestaurantDetailUI(
    val id: String,
    val description: Spanned
) {

    companion object {
        fun from(restaurant: Restaurant): RestaurantDetailUI = restaurant.run {
            RestaurantDetailUI(
                id,
                HtmlCompat.fromHtml(restaurant.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            )
        }
    }

}