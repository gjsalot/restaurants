package com.grantsutcliffe.restaurants.launcher.di

import com.grantsutcliffe.restaurants.launcher.activity.LauncherActivity
import dagger.android.AndroidInjector
import dagger.Subcomponent


@Subcomponent(modules = [
    LauncherActivityModule::class
])
interface LauncherActivityComponent : AndroidInjector<LauncherActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<LauncherActivity>()

}