package com.android.cuvvatest.ui.home

import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.network.PolicyService
import com.android.cuvvatest.repositories.NetworkRepository
import com.android.cuvvatest.repositories.vehicle.VehicleRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    private val service: PolicyService,
    private val rxSchedulers: RxSchedulers,
    private val vehicleRepository: VehicleRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    init {
//        disposables +=  Single.fromCallable {
//            vehicleRepository.insertVehicles()}
//                .subscribeOn(rxSchedulers.io())
//                .observeOn(rxSchedulers.main())
//                .subscribe()

        networkRepository.fetchVehicles()
            .subscribeOn(rxSchedulers.io())
            .subscribe()
    }

    fun fetchPolicies() {
        disposables += service.getPolicies()
            .subscribeOn(rxSchedulers.io())
            .map {
                it
            }
            .observeOn(rxSchedulers.main())
            .subscribe()
    }

    fun getVehicleTestData() {
        disposables += vehicleRepository.getAllVehicles()
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