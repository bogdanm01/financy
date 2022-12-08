package com.example.financy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.financy.model.Transaction
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detail.*
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val transaction = intent.getParcelableExtra<Transaction>("transaction")

        if(transaction != null) { // set transaction data
            transactionLabel.text = transaction.name;
            transactionAmountValue.text = transaction.amount.toString();
            transactionCategoryValue.text = transaction.category.toString();
            transactionDateValue.text = formatDate(transaction.date.toString());
        }

        deleteTransactionBtn.setOnClickListener() {
//            deleteRecord(transaction?.transactionId!!)

            TransactionRepository.deleteRecord(transaction?.transactionId!!).addOnSuccessListener {
                Toast.makeText(this, "Transaction Deleted", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

//    private fun deleteRecord(id: String) {
//        val dbRef = FirebaseDatabase.getInstance().getReference("Transactions").child(id);
//        val mTask = dbRef.removeValue()
//
//        mTask.addOnSuccessListener {
//            Toast.makeText(this, "Transaction Deleted", Toast.LENGTH_SHORT).show()
//
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }

    private fun formatDate(date:String):String {
        val array = date.split("-")
        return array.reversed().joinToString(separator = "/")
    }
}