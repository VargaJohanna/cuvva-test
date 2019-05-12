package com.android.cuvvatest.ui.receipt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.cuvvatest.ext.plusAssign
import com.android.cuvvatest.model.PaidPolicy
import com.android.cuvvatest.network.NetworkRepository
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyRepository
import com.android.cuvvatest.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class ReceiptViewModel(
    private val policyId: String,
    private val paidPolicyRepository: PaidPolicyRepository,
    private val rxSchedulers: RxSchedulers,
    private val networkRepository: NetworkRepository

) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val paidPolicyList: MutableLiveData<List<PaidPolicy>> = MutableLiveData()

    init {
        getPaidPolicies()
    }

    fun fetchDataFromNetwork() {
        disposables += networkRepository.fetchData()
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe()
    }

    private fun getPaidPolicies() {
        disposables += paidPolicyRepository.getPolicyById(policyId)
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe{t -> paidPolicyList.postValue(t)}
    }

    fun getLivePaidPolicy(): LiveData<List<PaidPolicy>> = paidPolicyList

    override fun onCleared() {
        disposables.clear()
    }
}