package com.android.cuvvatest.repositories.policies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.model.Policy
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.util.Arrays.asList

class PolicyRepositoryImplTest {
    @Rule
    @JvmField
    var mockito = InstantTaskExecutorRule()

    private val cancelledPolicyDao = mock<CancelledPolicyDao>()
    private val createdPolicy = CreatedPolicy(
        policyId = "policyId",
        timestamp = "timestamp",
        uniqueKey = "uniqueKey",
        userId = "userId",
        originalPolicyId = "originalPolicyId",
        startDate = LocalDateTime.of(2019, 5, 15, 10, 10),
        endDate = LocalDateTime.of(2019, 5, 15, 12, 10),
        extensionPolicy = false,
        updated = LocalDateTime.ofInstant(Instant.ofEpochMilli(1L), ZoneOffset.UTC),
        active = false
    )
    private val cancelledEntity = CancelledPolicyEntity(
        policyId = "policyId",
        timestamp = "timestamp",
        uniqueKey = "uniqueKey",
        updated = 2L,
        cancelType = "void"
    )

    private val policy = Policy(
        createdPolicy = createdPolicy,
        cancelled = true
    )

    @Test
    fun `should return a list of Policies when getPolicy() is called`() {
        //Given
        val repository = givenPolicyRepository()

        //When
        val testObserver = repository.getPolicy(Observable.just(asList(createdPolicy))).test()

        //Then
        testObserver
            .assertValues(
                asList(policy)
            )
            .assertNoErrors()
            .dispose()
    }

    private fun givenPolicyRepository(): PolicyRepositoryImpl {
        whenever(cancelledPolicyDao.getAll()).thenReturn(Observable.just(asList(cancelledEntity)))
        return PolicyRepositoryImpl(cancelledPolicyDao)
    }
}