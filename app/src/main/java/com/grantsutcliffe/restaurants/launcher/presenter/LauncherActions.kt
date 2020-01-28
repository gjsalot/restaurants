package com.grantsutcliffe.restaurants.launcher.presenter

import com.grantsutcliffe.restaurants.core.PresenterAction

sealed class LauncherActions : PresenterAction {

    object LaunchSearch : LauncherActions()

}