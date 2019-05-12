package com.android.cuvvatest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.customException.CustomException
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.model.VehicleAndPolicies
import com.android.cuvvatest.network.NetworkRepository
import com.android.cuvvatest.repositories.VehicleAndPoliciesRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    private val rxSchedulers: RxSchedulers,
    private val vehicleAndPoliciesRepository: VehicleAndPoliciesRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val activeVehicleList: MutableLiveData<List<VehicleAndPolicies>> = MutableLiveData()
    private val vehicleList: MutableLiveData<List<VehicleAndPolicies>> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()

    init {
        getActiveVehicleData()
        getVehicleData()
    }

    fun fetchDataFromNetwork() {
        disposables += networkRepository.fetchData()
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe(
                {
                    message.postValue("Data is successfully updated.")
                },
                {
                    if(it is CustomException) {
                        message.postValue(it.errorMessage)
                    } else {
                        message.postValue(it.message)
                    }
                })
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
    fun getMessage(): LiveData<String> = message

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}