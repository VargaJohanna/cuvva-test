package com.android.cuvvatest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.model.VehicleAndPolicies
import com.android.cuvvatest.repositories.VehicleAndPoliciesRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    private val rxSchedulers: RxSchedulers,
    private val vehicleAndPoliciesRepository: VehicleAndPoliciesRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val activeVehicleList: MutableLiveData<List<VehicleAndPolicies>> = MutableLiveData()
    private val vehicleList: MutableLiveData<List<VehicleAndPolicies>> = MutableLiveData()

    init {
        getActiveVehicleData()
        getVehicleData()
    }

    private fun getActiveVehicleData() {
        disposables += vehicleAndPoliciesRepository.getVehiclesAndCreatedPolicies()
            .subscribeOn(rxSchedulers.io())
            .map { list -> list.filter { it.hasActive } }
            .observeOn(rxSchedulers.main())
            .subscribe{t -> activeVehicleList.postValue(t)}
    }

    private fun getVehicleData() {
        disposables += vehicleAndPoliciesRepository.getVehiclesAndCreatedPolicies()
            .subscribeOn(rxSchedulers.io())
            .map { list -> list.filter { !it.hasActive } }
            .observeOn(rxSchedulers.main())
            .subscribe{t -> vehicleList.postValue(t)}
    }

    fun getActiveVehicleList(): LiveData<List<VehicleAndPolicies>> = activeVehicleList
    fun getVehicleList(): LiveData<List<VehicleAndPolicies>> = vehicleList
    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}