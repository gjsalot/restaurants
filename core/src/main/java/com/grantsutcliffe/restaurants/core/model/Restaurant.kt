package com.grantsutcliffe.restaurants.core.model

data class Restaurant(
    val id: String,
    val title: String,
    val subtitle: String,
    val icons: Icons,
    val primary_action: PrimaryAction,
    val categories: List<Category>
)