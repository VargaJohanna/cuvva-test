package com.android.cuvvatest.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.cuvvatest.R
import org.koin.androidx.viewmodel.ext.viewModel


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
