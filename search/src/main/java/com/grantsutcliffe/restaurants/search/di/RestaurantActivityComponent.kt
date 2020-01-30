package com.grantsutcliffe.restaurants.search.di

import com.grantsutcliffe.restaurants.search.activity.RestaurantActivity
import dagger.android.AndroidInjector
import dagger.Subcomponent


@Subcomponent(modules = [
    RestaurantActivityModule::class
])
interface RestaurantActivityComponent : AndroidInjector<RestaurantActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RestaurantActivity>()

}