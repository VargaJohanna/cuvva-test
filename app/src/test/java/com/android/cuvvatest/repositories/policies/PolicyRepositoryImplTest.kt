package com.android.cuvvatest.repositories.policies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class PolicyRepositoryImplTest {
    @Rule
    @JvmField
    var mockito = InstantTaskExecutorRule()

    private val cancelledPolicyDao = mock<CancelledPolicyDao>()

    @Test
    fun `should`() {

    }
}