package com.android.cuvvatest.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxSchedulersImpl  : RxSchedulers {
    override fun io() = Schedulers.io()

    override fun main() = AndroidSchedulers.mainThread()
}