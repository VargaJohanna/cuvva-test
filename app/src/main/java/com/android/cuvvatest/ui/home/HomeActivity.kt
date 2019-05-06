package com.android.cuvvatest.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.cuvvatest.R
import org.koin.androidx.viewmodel.ext.viewModel


class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.fetchPolicies()
        setContentView(R.layout.activity_home)
    }
}
