package com.android.cuvvatest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.model.HomeDataObject
import com.android.cuvvatest.repositories.NetworkRepository
import com.android.cuvvatest.repositories.home.HomeDataRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.LocalDateTime

class HomeViewModel(
    private val rxSchedulers: RxSchedulers,
    private val homeDataRepository: HomeDataRepository,
    networkRepository: NetworkRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val activeVehicleList: MutableLiveData<List<HomeDataObject>> = MutableLiveData()
    private val vehicleList: MutableLiveData<List<HomeDataObject>> = MutableLiveData()

    init {
        disposables += networkRepository.fetchData()
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe()

        getActiveVehicleData()
        getVehicleData()
    }

    private fun getActiveVehicleData() {
        disposables += homeDataRepository.getVehiclesAndCreatedPolicies()
            .subscribeOn(rxSchedulers.io())
            .map { list -> list.filter { it.hasActive } }
            .observeOn(rxSchedulers.main())
            .subscribe{t -> activeVehicleList.postValue(t)}
    }

    private fun getVehicleData() {
        disposables += homeDataRepository.getVehiclesAndCreatedPolicies()
            .subscribeOn(rxSchedulers.io())
            .map { list -> list.filter { !it.hasActive } }
            .observeOn(rxSchedulers.main())
            .subscribe{t -> vehicleList.postValue(t)}
    }

    fun getActiveVehicleList(): LiveData<List<HomeDataObject>> = activeVehicleList
    fun getVehicleList(): LiveData<List<HomeDataObject>> = vehicleList
    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}