package com.grantsutcliffe.restaurants.launcher.di

import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.launcher.activity.LauncherActivity
import com.grantsutcliffe.restaurants.launcher.presenter.LauncherActions
import com.grantsutcliffe.restaurants.launcher.presenter.LauncherPresenter
import com.grantsutcliffe.restaurants.launcher.presenter.LauncherUIEvents
import com.grantsutcliffe.restaurants.launcher.viewmodel.LauncherViewModel
import dagger.Module
import dagger.Provides


@Module
class LauncherActivityModule {

    @Provides
    internal fun provideLauncherView(
        launcherActivity: LauncherActivity
    ): PresenterView<LauncherViewModel, LauncherActions, LauncherUIEvents> = launcherActivity

    @Provides
    internal fun provideLauncherPresenter(): LauncherPresenter = LauncherPresenter()
}