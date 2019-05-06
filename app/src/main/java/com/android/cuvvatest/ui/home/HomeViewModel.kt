package com.android.cuvvatest.ui.home

import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.network.PolicyService
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    private val service: PolicyService,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {
    private val disposables = CompositeDisposable()

    fun fetchPolicies() {
        disposables += service.getPolicies()
            .subscribeOn(rxSchedulers.io())
            .map {
                it
            }
            .observeOn(rxSchedulers.main())
            .subscribe()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}