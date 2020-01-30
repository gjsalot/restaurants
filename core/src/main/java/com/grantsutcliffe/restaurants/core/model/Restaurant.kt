package com.grantsutcliffe.restaurants.core.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: String,
    val title: String,
    val subtitle: String,
    val icons: Icons,
    @SerializedName("primary_action")
    val primary_action: PrimaryAction,
    val categories: List<Category>,
    val description: String
)