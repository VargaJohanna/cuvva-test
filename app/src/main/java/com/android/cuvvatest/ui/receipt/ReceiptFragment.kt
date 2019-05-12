package com.android.cuvvatest.ui.receipt

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.cuvvatest.R
import com.android.cuvvatest.ext.show
import com.android.cuvvatest.model.PaidPolicy
import kotlinx.android.synthetic.main.fragment_home.view.progress_bar
import kotlinx.android.synthetic.main.fragment_home.view.swipe_refresh
import kotlinx.android.synthetic.main.fragment_receipt.view.*
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf

class ReceiptFragment : Fragment() {
    private val args: ReceiptFragmentArgs by navArgs()
    private val receiptViewModel: ReceiptViewModel by viewModel {
        parametersOf(args.policyId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val adapter = PaidPolicyAdapter(ArrayList())
        return inflater.inflate(R.layout.fragment_receipt, container, false).apply {
            if(args.cancelled) {
                voided_policy.show(true)
            }
            generateList(adapter, receipt_recycler_view)
            observeList(adapter, progress_bar, grand_total_value)
            swipe_refresh.setOnRefreshListener {
                //Refresh data here
                Handler().postDelayed({ swipe_refresh.isRefreshing = false }, 1000)
            }
        }
    }

    private fun observeList(policyAdapter: PaidPolicyAdapter, progressBar: ProgressBar, grandTotal: TextView) {
        progressBar.show(true)
        receiptViewModel.getLivePaidPolicy().observe(this, Observer {
            policyAdapter.updateList(it)
            calculateGrandTotal(grandTotal, it)
            progressBar.show(false)
        })
    }

    private fun calculateGrandTotal(totalView: TextView, list: List<PaidPolicy>) {
        var grandTotal = 0f
        list.forEach {
            grandTotal += it.totalPayable
        }

        if(grandTotal >= 0) {
            totalView.text = String.format(
                getString(R.string.receipt_value_positive),
                getString(R.string.two_decimal_format).format(grandTotal / 100)
            )
        } else {
            totalView.text = String.format(
                getString(R.string.receipt_value_positive),
                getString(R.string.two_decimal_format).format(grandTotal / 100 * -1))
        }
    }

    private fun generateList(policyAdapter: PaidPolicyAdapter, recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = policyAdapter
        }
    }
}