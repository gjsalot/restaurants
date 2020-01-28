package com.grantsutcliffe.restaurants

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.launcher.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

            override fun onActivityDestroyed(activity: Activity) = Unit

            override fun onActivityPaused(activity: Activity) = Unit

            override fun onActivityResumed(activity: Activity) = Unit

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

            override fun onActivityStarted(activity: Activity) {
                if (activity is PresenterView<*, *, *>) {
                    activity.presenter.start(activity)
                }
            }

            override fun onActivityStopped(activity: Activity) {
                if (activity is PresenterView<*, *, *>) {
                    activity.presenter.stop()
                }
            }
        })
    }


}