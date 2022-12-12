package com.example.financy.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.example.financy.R
import com.example.financy.TransactionRepository
import com.example.financy.models.Transaction
import kotlinx.android.synthetic.main.activity_new_transaction.*
import kotlinx.android.synthetic.main.transaction_item.*
import java.time.LocalDate
import java.util.*

class NewTransaction : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_transaction)

        val items = listOf("Income", "Bills", "House", "Food", "Entertainment", "Sports", "Health", "Transport", "Pets")
        val adapter = ArrayAdapter(this, R.layout.category_list_item, items)
        (categoryLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        labelInput.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                labelLayout.error = null
            }
        }

        amountInput.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                amountLayout.error = null
            }
        }

        addTransactionBtn.setOnClickListener {
            val expenseName = labelInput.text.toString()
            var expenseAmount = amountInput.text.toString().toDoubleOrNull()
            val expenseCategory = auto_complete_text.text.toString()
            val note = noteInput.text.toString()
            val expenseDate = LocalDate.now().toString()

            resetErrors()

            if(validateFields(expenseName, expenseAmount, expenseCategory)) {
                if(expenseCategory != "Income" && expenseAmount != null) {
                    expenseAmount *= -1
                }

                val transaction = Transaction(null, expenseName, expenseAmount, expenseDate, expenseCategory, note)

                TransactionRepository.insertTransaction(transaction).addOnSuccessListener {
                    clearInputFields()
                    Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun validateFields(
        expenseName: String,
        expenseAmount: Double?,
        expenseCategory: String
    ): Boolean {
        if (expenseName.isEmpty()) {
            labelLayout.error = "Please enter a valid name"
            return false
        }

        if (expenseAmount == null) {
            amountLayout.error = "Please enter a valid amount"
            return false
        }

        if (expenseCategory.isEmpty()) {
            categoryLayout.error = "Please select a valid category"
            return false
        }

        return true
    }

    private fun resetErrors() {
        labelLayout.error = ""
        amountLayout.error = ""
        categoryLayout.error = ""
    }

    private fun clearInputFields() {
        labelInput.text?.clear()
        amountInput.text?.clear()
        auto_complete_text?.text?.clear()
        noteInput.text?.clear()
    }
}