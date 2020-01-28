package com.grantsutcliffe.restaurants.launcher

import android.app.Application
import com.grantsutcliffe.restaurants.ActivityBuilder
import com.grantsutcliffe.restaurants.App
import com.grantsutcliffe.restaurants.AppModule
import com.grantsutcliffe.restaurants.core.di.CoreModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class,
    CoreModule::class
])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}