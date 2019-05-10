package com.android.cuvvatest.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.ext.show
import com.android.cuvvatest.model.HomeDataObject
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.viewModel

class HomeFragment : Fragment(), ActivePolicyAdapter.ItemClickListener, VehicleAdapter.VehicleItemClickListener {
    private val homeViewModel: HomeViewModel by viewModel()
    override fun onItemClick(dataObject: HomeDataObject) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activeAdapter = ActivePolicyAdapter(ArrayList(), this)
        val vehicleAdapter = VehicleAdapter(ArrayList(), this)
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            generateActiveList(activeAdapter, active_policy_recycler_view)
            observeActiveList(activeAdapter, progress_bar)
            generateVehicleList(vehicleAdapter, vehicles_recycler_view)
            observeVehicleList(vehicleAdapter, progress_bar)
            swipe_refresh.setOnRefreshListener {
                //Refresh data here
                Handler().postDelayed({ swipe_refresh.isRefreshing = false }, 1000)
            }
        }
    }

    private fun observeVehicleList(vehicleAdapter: VehicleAdapter, progressBar: ProgressBar) {
        progressBar.show(true)
        homeViewModel.getVehicleList().observe(this, Observer {
            vehicleAdapter.updateList(it)
            progressBar.show(false)
        })
    }

    private fun generateVehicleList(vehicleAdapter: VehicleAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = vehicleAdapter
        }
    }

    private fun observeActiveList(adapter: ActivePolicyAdapter, progressBar: ProgressBar) {
        progressBar.show(true)
        homeViewModel.getActiveVehicleList().observe(this, Observer {
            adapter.updateList(it)
            progressBar.show(false)
        })
    }

    private fun generateActiveList(activeAdapter: ActivePolicyAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = activeAdapter
        }
    }
}