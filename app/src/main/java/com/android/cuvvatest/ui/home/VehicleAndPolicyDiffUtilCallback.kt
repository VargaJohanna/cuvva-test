package com.android.cuvvatest.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.android.cuvvatest.model.VehicleAndPolicies

class VehicleAndPolicyDiffUtilCallback(
    private val oldList: List<VehicleAndPolicies>,
    private val newList: List<VehicleAndPolicies>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].vehicle == newList[newItemPosition].vehicle
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}