package com.android.cuvvatest.ui.vehicle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.repositories.VehicleAndPoliciesRepository
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class VehicleViewModel(
    private val rxSchedulers: RxSchedulers,
    private val vehicleAndPoliciesRepository: VehicleAndPoliciesRepository,
    private val cancelledPolicyRepository: CancelledPolicyRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val previousPolicies: MutableLiveData<List<Any>> = MutableLiveData()

    fun getCreatedPolicies(vrm: String) {
        disposables += vehicleAndPoliciesRepository.getVehiclesAndCreatedPolicies()
            .subscribeOn(rxSchedulers.io())
//            .flatMap {
//                it.forEach {
//                    if(it.vehicle.vrm == vrm) {
//                        it.createdPolicyList
//                    }
//                }
//            }
            .observeOn(rxSchedulers.main())
            .subscribe{

            }
    }

}