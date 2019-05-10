package com.android.cuvvatest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.model.VehicleAndPolicies
import kotlinx.android.synthetic.main.row_home_vehicle.view.*

class VehicleAdapter(
    private var vehicles: List<VehicleAndPolicies>,
    private val itemClickListener: VehicleItemClickListener
) : RecyclerView.Adapter<VehicleAdapter.PolicyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PolicyViewHolder(inflater.inflate(R.layout.row_home_vehicle, parent, false))
    }

    override fun getItemCount(): Int = vehicles.size

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    inner class PolicyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataObject: VehicleAndPolicies) {
            itemView.apply {
                vehicle_make.text = dataObject.vehicle.make
                color.text = dataObject.vehicle.color
                model.text = dataObject.vehicle.model
                reg_plate.text = dataObject.vehicle.prettyVrm
                //Is it too much calculation here?
                total_policies.text = dataObject.createdPolicyList.filter { !it.extensionPolicy }.size.toString()
                setVehicleLogo(dataObject.vehicle.make, vehicle_logo)
            }
        }

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(vehicles[layoutPosition])
            }
        }

        private fun setVehicleLogo(make: String, image: ImageView) {
            when (make) {
                "Volkswagen" -> image.setImageResource(R.drawable.carlogo_volkswagen)
                "Mercedes-Benz" -> image.setImageResource(R.drawable.carlogo_mercedes)
                "MINI" -> image.setImageResource(R.drawable.carlogo_mini)
                else -> image.setImageResource(R.drawable.carlogo_volkswagen)
            }
        }
    }

    fun updateList(newList: List<VehicleAndPolicies>) {
        vehicles = newList
        notifyDataSetChanged()
    }

    interface VehicleItemClickListener {
        fun onItemClick(dataObject: VehicleAndPolicies)
    }
}