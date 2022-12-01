package com.example.financy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.financy.model.Transaction
import org.w3c.dom.Text
import kotlin.math.abs

class TransactionAdapter(private val transactionList: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    var onItemClick: ((Transaction) -> Unit)? = null

    class TransactionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expenseName: TextView = view.findViewById(R.id.expense_name)
        val expenseAmount: TextView = view.findViewById(R.id.expense_amount)
        val expenseDate: TextView = view.findViewById(R.id.expense_date)
        val expenseCategory: TextView = view.findViewById(R.id.expense_category)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = transactionList[position]
        holder.expenseName.text = transaction.name
        holder.expenseCategory.text = transaction.category
        holder.expenseDate.text = transaction.date.toString()

        val context = holder.expenseAmount.context

        if (transaction.amount!! >= 0) {
            holder.expenseAmount.text = "+ $%.2f".format(transaction.amount)
            holder.expenseAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.expenseAmount.text = "- $%.2f".format(abs(transaction.amount))
            holder.expenseAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(transaction)
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}