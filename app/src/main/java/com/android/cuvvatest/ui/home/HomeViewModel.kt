package com.android.cuvvatest.ui.home

import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.repositories.NetworkRepository
import com.android.cuvvatest.repositories.home.HomeDataRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    private val rxSchedulers: RxSchedulers,
    private val homeDataRepository: HomeDataRepository,
    networkRepository: NetworkRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    init {
        disposables += networkRepository.fetchData()
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe()
    }

    fun getVehicleData() {
        disposables += homeDataRepository.getVehiclesAndCreatedPolicies()
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