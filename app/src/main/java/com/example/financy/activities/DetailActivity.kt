package com.example.financy.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.financy.R
import com.example.financy.TransactionRepository
import com.example.financy.Util
import com.example.financy.models.Transaction
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val transaction = intent.getParcelableExtra<Transaction>("transaction")

        if(transaction != null) {
            transactionNameValue.text = transaction.name
            transactionAmountValue.text = "$ ${transaction.amount.toString()}"
            transactionCategoryValue.text = transaction.category.toString()
            transactionDateValue.text = Util.formatDate(transaction.date.toString())
            transactionNoteValue.text = transaction.note.toString()
        }

        deleteTransactionBtn.setOnClickListener {
            TransactionRepository.deleteRecord(transaction?.transactionId!!).addOnSuccessListener {
                Toast.makeText(this, "Transaction Deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}