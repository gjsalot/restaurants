package com.grantsutcliffe.restaurants

import android.app.Activity
import com.grantsutcliffe.restaurants.launcher.activity.LauncherActivity
import com.grantsutcliffe.restaurants.launcher.di.LauncherActivityComponent
import com.grantsutcliffe.restaurants.search.activity.SearchActivity
import com.grantsutcliffe.restaurants.search.di.SearchActivityComponent
import dagger.android.AndroidInjector
import dagger.android.ActivityKey
import dagger.multibindings.IntoMap
import dagger.Binds
import dagger.Module


@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(LauncherActivity::class)
    internal abstract fun bindLauncherActivity(builder: LauncherActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(SearchActivity::class)
    internal abstract fun bindSearchActivity(builder: SearchActivityComponent.Builder): AndroidInjector.Factory<out Activity>

}