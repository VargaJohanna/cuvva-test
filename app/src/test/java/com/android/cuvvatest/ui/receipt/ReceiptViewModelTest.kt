package com.android.cuvvatest.ui.receipt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.cuvvatest.TestScheduler
import com.android.cuvvatest.customException.CustomException
import com.android.cuvvatest.model.PaidPolicy
import com.android.cuvvatest.network.NetworkRepository
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.util.Arrays.asList

class ReceiptViewModelTest {
    @Rule
    @JvmField
    var mockito = InstantTaskExecutorRule()

    private val paidPolicyRepository = mock<PaidPolicyRepository>()
    private val networkRepository = mock<NetworkRepository>()
    private val paidPolicy = PaidPolicy(
        uniqueKey = "uniqueKey",
        timestamp = "",
        underwriterPremium = 1f,
        commission = 1f,
        totalPremium = 1f,
        ipt = 1f,
        iptRate = 1f,
        extraFees = 1f,
        vat = 1f,
        deductions = 1f,
        totalPayable = 1f,
        updated = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(1), ZoneOffset.UTC
        ),
        policyId = "policyId"
    )

    @Test
    fun `should get list of PaidPolicy when getLivePaidPolicy() is called`() {
        //Given
        val receiptRepository = givenReceiptViewModel()

        //When
        receiptRepository.getLivePaidPolicy().observeForever(mock())

        //Then
        assertEquals(
            asList(paidPolicy),
            receiptRepository.getLivePaidPolicy().value
        )
    }

    @Test
    fun `should update error message when getPolicyById() returns an error`() {
        //Given
        val receiptRepository = givenReceiptViewModelWithEmptyPolicyList()

        //When
        receiptRepository.getMessage().observeForever(mock())

        //Then
        assertEquals(
            "Sorry, there's nothing to show.",
            receiptRepository.getMessage().value
        )
    }

    @Test
    fun `should update error message when there's a network error`() {
        //Given
        val receiptRepository = givenReceiptViewModelWithNetworkError()
        receiptRepository.fetchDataFromNetwork()

        //When
        receiptRepository.getMessage().observeForever(mock())

        //Then
        assertEquals(
            "error",
            receiptRepository.getMessage().value
        )
    }

    private fun givenReceiptViewModel(): ReceiptViewModel {
        whenever(networkRepository.fetchData()).thenReturn(Completable.never())
        whenever(paidPolicyRepository.getPolicyById("policyId")).then { Observable.just(asList(paidPolicy)) }
        return ReceiptViewModel("policyId", paidPolicyRepository, TestScheduler(), networkRepository)
    }

    private fun givenReceiptViewModelWithEmptyPolicyList(): ReceiptViewModel {
        whenever(networkRepository.fetchData()).thenReturn(Completable.never())
        whenever(paidPolicyRepository.getPolicyById("policyId")).then { Observable.just(emptyList<PaidPolicy>()) }
        return ReceiptViewModel("policyId", paidPolicyRepository, TestScheduler(), networkRepository)
    }

    private fun givenReceiptViewModelWithNetworkError(): ReceiptViewModel {
        whenever(networkRepository.fetchData()).thenReturn(Completable.error(CustomException("error")))
        whenever(paidPolicyRepository.getPolicyById("policyId")).then { Observable.just(asList(paidPolicy)) }
        return ReceiptViewModel("policyId", paidPolicyRepository, TestScheduler(), networkRepository)
    }
}