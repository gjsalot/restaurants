package com.grantsutcliffe.restaurants.launcher.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grantsutcliffe.restaurants.R
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.launcher.presenter.LauncherActions
import com.grantsutcliffe.restaurants.launcher.presenter.LauncherPresenter
import com.grantsutcliffe.restaurants.launcher.presenter.LauncherUIEvents
import com.grantsutcliffe.restaurants.launcher.viewmodel.LauncherViewModel
import com.grantsutcliffe.restaurants.search.activity.SearchActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LauncherActivity : AppCompatActivity(), PresenterView<LauncherViewModel, LauncherActions, LauncherUIEvents> {

    @Inject override lateinit var presenter: LauncherPresenter

    override val uiEvents: Observable<LauncherUIEvents> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }

    override fun render(viewModel: LauncherViewModel) = Unit

    override fun performAction(action: LauncherActions) {
        when (action) {
            LauncherActions.LaunchSearch -> startActivity(Intent(this, SearchActivity::class.java))
        }
    }

}
