package com.grantsutcliffe.restaurants

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.grantsutcliffe.restaurants.launcher.di.LauncherActivityComponent
import com.grantsutcliffe.restaurants.search.di.SearchActivityComponent
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module(subcomponents = [
    LauncherActivityComponent::class,
    SearchActivityComponent::class
])
@Singleton
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideResources(application: Application): Resources = application.resources

}