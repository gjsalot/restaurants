package com.grantsutcliffe.restaurants.launcher.presenter

import com.grantsutcliffe.restaurants.core.Presenter
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.launcher.viewmodel.LauncherViewModel

class LauncherPresenter : Presenter<LauncherViewModel, LauncherActions, LauncherUIEvents>() {

    override fun onStart(view: PresenterView<LauncherViewModel, LauncherActions, LauncherUIEvents>) {
        // Logic to choose the initial screen would go here...based on user, first time visit, etc.
        // For now just launch the search screen
        view.performAction(LauncherActions.LaunchSearch)
    }

    override fun onStop() = Unit

}