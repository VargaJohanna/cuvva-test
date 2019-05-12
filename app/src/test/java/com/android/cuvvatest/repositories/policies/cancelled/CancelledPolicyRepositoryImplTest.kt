package com.android.cuvvatest.repositories.policies.cancelled

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.cuvvatest.model.CancelledPolicy
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.util.Arrays.asList

class CancelledPolicyRepositoryImplTest {
    @Rule
    @JvmField
    var mockito = InstantTaskExecutorRule()

    private val cancelledPolicyDao = mock<CancelledPolicyDao>()

    @Test
    fun `should return a CancelledPolicy when getPolicyById() is called`() {
        //Given
        val cancelledRepository = givenCancelledRepository()

        //When
        val testObserver = cancelledRepository.getPolicyById("policyId").test()

        //Then
        testObserver
            .assertValues(
                CancelledPolicy(
                    policyId = "policyId",
                    timestamp = "timestampt",
                    uniqueKey = "uniqueKey",
                    cancelType = "void",
                    updated = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(0), ZoneOffset.UTC
                    )
                )
            )
            .assertNoErrors()
            .dispose()
    }

    private fun givenCancelledRepository(): CancelledPolicyRepositoryImpl {
        whenever(cancelledPolicyDao.getPoliciesById("policyId")).thenReturn(
            Observable.just(
                asList(
                    CancelledPolicyEntity(
                        policyId = "policyId",
                        timestamp = "timestampt",
                        uniqueKey = "uniqueKey",
                        cancelType = "void",
                        updated = 0L
                    )
                )
            )
        )
        return CancelledPolicyRepositoryImpl(cancelledPolicyDao)
    }
}