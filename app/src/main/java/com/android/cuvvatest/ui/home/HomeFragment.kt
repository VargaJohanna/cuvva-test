package com.android.cuvvatest.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.ext.show
import com.android.cuvvatest.model.VehicleAndPolicies
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.androidx.viewmodel.ext.viewModel

class HomeFragment : Fragment(), ActivePolicyAdapter.ItemClickListener, VehicleAdapter.VehicleItemClickListener {
    private val homeViewModel: HomeViewModel by viewModel()
    private var toast: Toast? = null

    override fun onItemClick(dataObject: VehicleAndPolicies) {
        val action = HomeFragmentDirections.fromHomeToVehicle(
            dataObject.vehicle.prettyVrm,
            dataObject.vehicle.make,
            dataObject.vehicle.model
        )
        findNavController().navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activeAdapter = ActivePolicyAdapter(ArrayList(), this)
        val vehicleAdapter = VehicleAdapter(ArrayList(), this)
        showErrorMessage()

        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            generateActiveList(activeAdapter, active_policy_recycler_view)
            observeActiveList(activeAdapter, progress_bar)
            generateVehicleList(vehicleAdapter, vehicles_recycler_view)
            observeVehicleList(vehicleAdapter, progress_bar)
            swipe_refresh.setOnRefreshListener {
                //Refresh data here
                homeViewModel.fetchDataFromNetwork()
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
            if (toast != null) {
                toast?.cancel()
            }
        })
    }

    private fun generateActiveList(activeAdapter: ActivePolicyAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = activeAdapter
        }
    }

    /**
     * Observe the message and show the returned text in a toast
     */
    private fun showErrorMessage() {
        homeViewModel.getMessage().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                toast = Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
                toast?.show()
            } else {
                toast = Toast.makeText(requireContext(), getString(R.string.generic_error), Toast.LENGTH_LONG)
                toast?.show()
            }
        })
    }
}