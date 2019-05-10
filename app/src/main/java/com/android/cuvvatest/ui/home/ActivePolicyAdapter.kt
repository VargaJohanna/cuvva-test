package com.android.cuvvatest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.model.VehicleAndPolicies
import kotlinx.android.synthetic.main.row_home_active_policy.view.*

class ActivePolicyAdapter(
    private var activeVehicles: List<VehicleAndPolicies>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ActivePolicyAdapter.PolicyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PolicyViewHolder(inflater.inflate(R.layout.row_home_active_policy, parent, false))
    }

    override fun getItemCount(): Int = activeVehicles.size

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        holder.bind(activeVehicles[position])
    }

    inner class PolicyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataObject: VehicleAndPolicies) {
            itemView.apply {
                home_vehicle_make.text = dataObject.vehicle.make
                color.text = dataObject.vehicle.color
                model.text = dataObject.vehicle.model
                reg_plate.text = dataObject.vehicle.prettyVrm
                //Is it too much calculation here?
                total_policies.text = dataObject.createdPolicyList.filter { !it.extensionPolicy }.size.toString()
                remaining_time.text = String.format(
                    context.getString(R.string.remaining_time),
                    dataObject.remainingTimeOfActive.toString()
                )
                setVehicleLogo(dataObject.vehicle.model, vehicle_logo)
            }
        }

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(activeVehicles[layoutPosition])
            }
        }

        private fun setVehicleLogo(make: String, image: ImageView) {
            when(make) {
                "Volkswagen" -> image.setImageResource(R.drawable.carlogo_volkswagen)
                "Mercedes-Benz" -> image.setImageResource(R.drawable.carlogo_mercedes)
                "MINI" -> image.setImageResource(R.drawable.carlogo_mini)
                else -> image.setImageResource(R.drawable.carlogo_volkswagen)
            }
        }
    }

    fun updateList(newList: List<VehicleAndPolicies>) {
        activeVehicles = newList
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(dataObject: VehicleAndPolicies)
    }
}