package com.android.cuvvatest.ui.vehicle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.model.Policy
import kotlinx.android.synthetic.main.row_previous_policy.view.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

class PreviousPolicyAdapter(
    private var policyList: List<Policy>,
    private val itemClickListener: PreviousPolicyClickListener
) : RecyclerView.Adapter<PreviousPolicyAdapter.PolicyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PolicyViewHolder(inflater.inflate(R.layout.row_previous_policy, parent, false))
    }

    override fun getItemCount(): Int = policyList.size

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        holder.bind(policyList[position])
    }

    inner class PolicyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(policy: Policy) {
            itemView.apply {
                if (policy.cancelled) {
                    previous_duration.text = context.getString(R.string.previous_voided)
                    previous_duration.setTextColor(ContextCompat.getColor(itemView.context, R.color.alert))
                } else {
                    previous_duration.text = String.format(
                        context.getString(R.string.duration_of_policy),
                        policy.createdPolicy.startDate.until(
                            policy.createdPolicy.endDate,
                            ChronoUnit.MINUTES
                        ).toString()
                    )
                    previous_duration.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.abc_primary_text_material_light
                        )
                    )

                }
                val date = policy.createdPolicy.startDate
                val englishDate = date.format(DateTimeFormatter.ofPattern("d MMM YYYY", Locale.UK))
                previous_date.text = String.format(
                    "${date.dayOfWeek.toString().substring(0, 3).toLowerCase().capitalize()}, $englishDate"
                )
            }
        }

        init {
            itemView.setOnClickListener {
                if(layoutPosition >= 0) {
                    itemClickListener.onItemClick(policyList[layoutPosition])
                }
            }
        }
    }

    fun updateList(newList: List<Policy>) {
        val diffResult = DiffUtil.calculateDiff(PreviousPolicyDiffUtilCallback(policyList, newList))
        this.policyList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface PreviousPolicyClickListener {
        fun onItemClick(policy: Policy)
    }
}