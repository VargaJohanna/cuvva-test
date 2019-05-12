package com.android.cuvvatest.ui.vehicle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.model.Policy
import com.android.cuvvatest.repositories.VehicleAndPoliciesRepository
import com.android.cuvvatest.repositories.policies.PolicyRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class VehicleViewModel(
    private val vehiclePrettyVrm: String,
    private val rxSchedulers: RxSchedulers,
    private val vehicleAndPoliciesRepository: VehicleAndPoliciesRepository,
    private val policyRepository: PolicyRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val previousPolicies: MutableLiveData<List<Policy>> = MutableLiveData()
    private val _createdPolicies = BehaviorSubject.create<List<CreatedPolicy>>()
    private val createdPolicies = _createdPolicies
    private val numberOfPolicies: MutableLiveData<Int> = MutableLiveData()

    init {
        getCreatedPolicies()
        getPolicies()
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
                _createdPolicies.onNext(t)
                numberOfPolicies.postValue(t.filter { !it.extensionPolicy }.size)
            }
    }

    private fun getPolicies() {
        // Policies contains has a boolean value for cancelled, so the adapter will be able to identify it.
        disposables += policyRepository.getPolicy(createdPolicies)
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe { t -> previousPolicies.postValue(t) }
    }

    fun getNumberOfTotalPolicies(): LiveData<Int> = numberOfPolicies
    fun getLivePolicies(): LiveData<List<Policy>> = previousPolicies

    override fun onCleared() {
        disposables.clear()
    }
}