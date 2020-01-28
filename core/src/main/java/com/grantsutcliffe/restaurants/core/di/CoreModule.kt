package com.grantsutcliffe.restaurants.core.di

import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.core.repository.network.RestaurantsNetworkSource
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class CoreModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://storage.googleapis.com/coding-session-rest-api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    internal fun provideRestaurantsNetworkSource(retrofit: Retrofit): RestaurantsNetworkSource =
        RestaurantsNetworkSource(retrofit)

    @Provides
    @Singleton
    internal fun provideRestaurantsRepository(
        networkSource: RestaurantsNetworkSource
    ): RestaurantsRepository = RestaurantsRepository(networkSource)

    @Provides
    @Singleton
    internal fun provideSchedulers(): com.grantsutcliffe.restaurants.core.rx.Schedulers = object : com.grantsutcliffe.restaurants.core.rx.Schedulers {

        override val ui: Scheduler = AndroidSchedulers.mainThread()

        override val io: Scheduler = Schedulers.io()

        override val computation: Scheduler = Schedulers.computation()

    }

}