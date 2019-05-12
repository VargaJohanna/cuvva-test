package com.android.cuvvatest.repositories.policies.paid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.cuvvatest.model.PaidPolicy
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.util.Arrays.asList

class PaidPolicyRepositoryImplTest {
    @Rule
    @JvmField
    var mockito = InstantTaskExecutorRule()

    private val paidPolicyDao = mock<PaidPolicyDao>()

    @Test
    fun `should return PaidPolicy list when getPolicyById() is called`() {
        //Given
        val repository = givenPaidPolicyRepositoryWithData()

        //When
        val testObserver = repository.getPolicyById("policyId").test()

        //Then
        testObserver
            .assertValues(
                asList(
                    PaidPolicy(
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
                            Instant.ofEpochMilli(1), ZoneOffset.UTC),
                        policyId = "policyId"
                    )
                )
            )
            .assertNoErrors()
            .dispose()
    }

    private fun givenPaidPolicyRepositoryWithData(): PaidPolicyRepositoryImpl {
        whenever(paidPolicyDao.getPoliciesById("policyId")).thenReturn(
            Observable.just(
                asList(
                    PaidPolicyEntity(
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
                        updated = 1L,
                        policyId = "policyId"
                    )
                )
            )
        )
        return PaidPolicyRepositoryImpl(paidPolicyDao)
    }
}