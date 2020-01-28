package com.grantsutcliffe.restaurants.search.di

import com.grantsutcliffe.restaurants.search.activity.SearchActivity
import dagger.android.AndroidInjector
import dagger.Subcomponent


@Subcomponent(modules = [
    SearchActivityModule::class
])
interface SearchActivityComponent : AndroidInjector<SearchActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SearchActivity>()

}