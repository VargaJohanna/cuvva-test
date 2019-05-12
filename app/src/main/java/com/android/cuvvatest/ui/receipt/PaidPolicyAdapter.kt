package com.android.cuvvatest.ui.receipt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.model.PaidPolicy
import kotlinx.android.synthetic.main.row_receipt.view.*
import kotlinx.android.synthetic.main.row_receipt.view.total_paid_value
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.time.Instant
import java.util.*

class PaidPolicyAdapter(
    private var policyList: List<PaidPolicy>
): RecyclerView.Adapter<PaidPolicyAdapter.PaidPolicyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaidPolicyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PaidPolicyViewHolder(inflater.inflate(R.layout.row_receipt, parent, false))
    }

    override fun getItemCount(): Int = policyList.size

    override fun onBindViewHolder(holder: PaidPolicyViewHolder, position: Int) {
        holder.bind(policyList[position])
    }

    inner class PaidPolicyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        fun bind(policy: PaidPolicy) {
            itemView.apply {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'", Locale.UK)
                val date = LocalDateTime.parse(policy.timestamp.replace("T", " "), formatter)
                val englishDateTime = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.UK))
                timestamp.text = englishDateTime
                setValues(insurance_premium_value, policy.totalPremium)
                setValues(insurance_premium_tax_value, policy.ipt)
                setValues(admin_fee_value, policy.extraFees)
                setValues(total_paid_value, policy.totalPayable)
            }
        }

        private fun setValues(item: TextView, value: Float) {
            if(value >= 0) {
                item.text = String.format(item.context.getString(R.string.receipt_value_positive), "%.2f".format(value / 100))
            } else {
                item.text = String.format(item.context.getString(R.string.receipt_value_negative), "%.2f".format(value / 100 * -1))
            }
        }
    }

    fun updateList(newList: List<PaidPolicy>) {
        policyList = newList
        notifyDataSetChanged()
    }
}