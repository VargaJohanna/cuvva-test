package com.android.cuvvatest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.model.HomeDataObject
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
            observeActiveList(activeAdapter)
            generateVehicleList(vehicleAdapter, vehicles_recycler_view)
            observeVehicleList(vehicleAdapter)
        }
    }

    private fun observeVehicleList(vehicleAdapter: VehicleAdapter) {
        homeViewModel.getVehicleList().observe(this, Observer {
            vehicleAdapter.updateList(it)
        })
    }

    private fun generateVehicleList(vehicleAdapter: VehicleAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = vehicleAdapter
        }
    }

    private fun observeActiveList(adapter: ActivePolicyAdapter) {
        homeViewModel.getActiveVehicleList().observe(this, Observer {
            adapter.updateList(it)
        })
    }

    private fun generateActiveList(activeAdapter: ActivePolicyAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = activeAdapter
        }
    }
}