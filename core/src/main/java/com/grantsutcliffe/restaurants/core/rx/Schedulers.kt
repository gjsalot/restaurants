package com.grantsutcliffe.restaurants.core.rx

import io.reactivex.Scheduler

interface Schedulers {

    val ui: Scheduler
    val io: Scheduler
    val computation: Scheduler

}