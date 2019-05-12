package com.android.cuvvatest.ui.receipt

import androidx.recyclerview.widget.DiffUtil
import com.android.cuvvatest.model.PaidPolicy

class PaidPolicyDiffUtilCallback(
    private val oldList: List<PaidPolicy>,
    private val newList: List<PaidPolicy>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uniqueKey == newList[newItemPosition].uniqueKey
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