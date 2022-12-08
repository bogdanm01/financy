package com.example.financy

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import com.example.financy.model.Transaction
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_transaction.*
import kotlinx.android.synthetic.main.transaction_item.*
import java.time.LocalDate
import java.util.*

class NewTransaction : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_transaction)

        val items = listOf("Income", "Bills", "House", "Food", "Entertainment", "Sports", "Health", "Transport", "Pets")
        val adapter = ArrayAdapter(this, R.layout.category_list_item, items)
        (categoryLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        labelInput.addTextChangedListener {
            if (it!!.count() > 0) {
                labelLayout.error = null
            }
        }

        amountInput.addTextChangedListener {
            if (it!!.count() > 0) {
                amountLayout.error = null
            }
        }

        addTransactionBtn.setOnClickListener {
            val expenseName = labelInput.text.toString()
            var expenseAmount = amountInput.text.toString().toDoubleOrNull()
            val expenseCategory = auto_complete_text.text.toString()
            val note = noteInput.text.toString()
            val expenseDate = LocalDate.now().toString()

            var isInputValid: Boolean = true;

            if(expenseName.isEmpty()) {
                labelLayout.error= "Please enter a valid name"
                isInputValid = false
            }

            if(expenseAmount == null) {
                amountLayout.error = "Please enter a valid amount"
                isInputValid = false
            }

            if(isInputValid) {

                if(!expenseCategory.equals("Income")) {
                    if (expenseAmount != null) {
                        expenseAmount *= -1
                    }
                }

                database = FirebaseDatabase.getInstance().getReference("Transactions")

                val transactionId = database.child("Transactions").push().key
                val transaction = Transaction(transactionId, expenseName, expenseAmount, expenseDate, expenseCategory)

                database.child(transactionId!!).setValue(transaction).addOnSuccessListener {

                    labelInput.text?.clear()
                    amountInput.text?.clear()
                    auto_complete_text?.text?.clear()
                    noteInput.text?.clear()

                    Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        closeBtn.setOnClickListener{
            finish()
        }
    }
}