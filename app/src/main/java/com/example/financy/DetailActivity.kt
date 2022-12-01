package com.example.financy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.financy.model.Transaction
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val transaction = intent.getParcelableExtra<Transaction>("transaction")
        if(transaction != null) {
            val textView : TextView = findViewById(R.id.test)

            textView.text = transaction.name
        }
    }
}