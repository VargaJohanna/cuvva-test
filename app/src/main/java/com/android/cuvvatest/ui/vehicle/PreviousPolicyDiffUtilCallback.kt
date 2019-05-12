package com.android.cuvvatest.ui.vehicle

import androidx.recyclerview.widget.DiffUtil
import com.android.cuvvatest.model.Policy

class PreviousPolicyDiffUtilCallback(
    private val oldList: List<Policy>,
    private val newList: List<Policy>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].createdPolicy == newList[newItemPosition].createdPolicy
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