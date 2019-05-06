package com.android.cuvvatest.rx

import io.reactivex.Scheduler

interface RxSchedulers {
    fun io(): Scheduler
    fun main(): Scheduler
}