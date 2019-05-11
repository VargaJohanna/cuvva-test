package com.android.cuvvatest.ui.vehicle

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.Constants
import com.android.cuvvatest.R
import com.android.cuvvatest.ext.display
import com.android.cuvvatest.ext.show
import com.android.cuvvatest.ext.toCarLogo
import com.android.cuvvatest.model.Policy
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_vehicle.view.*
import kotlinx.android.synthetic.main.fragment_vehicle.view.progress_bar
import kotlinx.android.synthetic.main.fragment_vehicle.view.swipe_refresh
import kotlinx.android.synthetic.main.fragment_vehicle.view.vehicles_recycler_view
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf
import org.threeten.bp.temporal.ChronoUnit

class VehicleFragment : Fragment(), PreviousPolicyAdapter.PreviousPolicyClickListener {
    private val args: VehicleFragmentArgs by navArgs()

    private val vehicleViewModel: VehicleViewModel by viewModel {
        parametersOf(args.prettyVrm)
    }

    override fun onItemClick(policy: Policy) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val previousPolicyAdapter = PreviousPolicyAdapter(ArrayList(), this)
        return inflater.inflate(R.layout.fragment_vehicle, container, false).apply {
            vehicle_make.text = args.make
            vehicle_model.text = args.model
            vehicle_reg_plate.text = args.prettyVrm
            setTotalPolicies(vehicle_total_policies)
            setActivePolicy(policy_card, no_active_policy, vehicle_active_policy, cover_button)
            swipe_refresh.setOnRefreshListener {
                //Refresh data here
                Handler().postDelayed({ swipe_refresh.isRefreshing = false }, 1000)
            }
            closeButton(close_button)
            vehicle_logo.toCarLogo(args.make)

            generateActiveList(previousPolicyAdapter, vehicles_recycler_view)
            observeActiveList(previousPolicyAdapter, progress_bar)
        }
    }

    private fun closeButton(closeButton: ImageView) {
        closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeActiveList(policyAdapter: PreviousPolicyAdapter, progressBar: ProgressBar) {
        progressBar.show(true)
        vehicleViewModel.getLivePolicies().observe(this, Observer { policyList ->
            //Collect extension policy ids
            val extensionIds = mutableListOf<String>()
            policyList.filter { it.createdPolicy.extensionPolicy }
                .forEach { extensionIds.add(it.createdPolicy.policyId) }

            // If a policy is extended then don't add the original policy to the list
            policyAdapter.updateList(policyList.filter { !extensionIds.contains(it.createdPolicy.policyId) })
            progressBar.show(false)
        })
    }

    private fun generateActiveList(previousAdapter: PreviousPolicyAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = previousAdapter
        }
    }

    private fun setActivePolicy(view: MaterialCardView, noActiveText: TextView, activeTitle: TextView, button: Button) {
        vehicleViewModel.getLivePolicies().observe(this, Observer {
            if (it.any { policy -> policy.createdPolicy.active }) {
                noActiveText.display(false)
                view.display(true)
                activeTitle.display(true)
                val activePolicy = it.first { policy -> policy.createdPolicy.active }
                view.vehicle_remaining_time_value.text =
                    Constants.FROM_LOCAL_DATE_TIME.until(activePolicy.createdPolicy.endDate, ChronoUnit.MINUTES)
                        .toString()
                button.text = requireActivity().getString(R.string.vehicle_extend_cover_button)
            } else {
                noActiveText.display(true)
                view.display(false)
                activeTitle.display(false)
                button.text = requireActivity().getString(R.string.create_new_policy)
            }
        })
    }

    private fun setTotalPolicies(vehicleTotalPolicies: TextView) {
        vehicleViewModel.getNumberOfTotalPolicies().observe(requireActivity(), Observer {
            vehicleTotalPolicies.text = it.toString()
        })
    }
}