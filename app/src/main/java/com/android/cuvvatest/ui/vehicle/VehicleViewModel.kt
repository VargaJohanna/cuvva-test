package com.android.cuvvatest.ui.vehicle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.customException.CustomException
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.model.Policy
import com.android.cuvvatest.network.NetworkRepository
import com.android.cuvvatest.repositories.vehicle.VehicleAndPoliciesRepository
import com.android.cuvvatest.repositories.policies.PolicyRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class VehicleViewModel(
    private val vehiclePrettyVrm: String,
    private val rxSchedulers: RxSchedulers,
    private val vehicleAndPoliciesRepository: VehicleAndPoliciesRepository,
    private val policyRepository: PolicyRepository,
    private val networkRepository: NetworkRepository

) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val previousPolicies: MutableLiveData<List<Policy>> = MutableLiveData()
    private val _createdPolicies = BehaviorSubject.create<List<CreatedPolicy>>()
    private val createdPolicies = _createdPolicies
    private val numberOfPolicies: MutableLiveData<Int> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()

    init {
        getCreatedPolicies()
        getPolicies()
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
                    if (it is CustomException) {
                        message.postValue(it.errorMessage)
                    } else {
                        message.postValue(it.message)
                    }
                }
            )
    }

    private fun getCreatedPolicies() {
        disposables += vehicleAndPoliciesRepository.getVehiclesAndCreatedPolicies()
            .subscribeOn(rxSchedulers.io())
            .map { list ->
                list.filter { it.vehicle.prettyVrm == vehiclePrettyVrm }
            }
            .map {
                it.first().createdPolicyList
            }
            .observeOn(rxSchedulers.main())
            .subscribe { t ->
                if (t.isNotEmpty()) {
                    _createdPolicies.onNext(t)
                    numberOfPolicies.postValue(t.filter { !it.extensionPolicy }.size)
                } else {
                    message.postValue("Sorry, there's nothing to show.")
                }
            }
    }

    private fun getPolicies() {
        // Policy contains has a boolean value for cancelled, so the adapter will be able to identify it.
        disposables += policyRepository.getPolicy(createdPolicies)
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe { t -> previousPolicies.postValue(t) }
    }

    fun getNumberOfTotalPolicies(): LiveData<Int> = numberOfPolicies
    fun getLivePolicies(): LiveData<List<Policy>> = previousPolicies
    fun getMessage(): LiveData<String> = message

    override fun onCleared() {
        disposables.clear()
    }
}