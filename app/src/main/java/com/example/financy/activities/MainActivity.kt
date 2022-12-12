package com.example.financy.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financy.R
import com.example.financy.TransactionAdapter
import com.example.financy.databinding.ActivityMainBinding
import com.example.financy.models.Transaction
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_transaction.*
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var transactionList : ArrayList<Transaction>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview_transactions)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        transactionList = arrayListOf()

        getData()

        newTransactionSwitch.setOnClickListener {
            val intent = Intent(this, NewTransaction::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateDashboard() {
        val totalBalance : Double = transactionList.sumOf { it.amount!! }
        val budgetV = transactionList.filter { it.amount!! > 0 }.sumOf { it.amount!! }
        val expenseV = totalBalance - budgetV

        balance.text =  "$ %.2f".format(totalBalance)
        budget.text = "$ %.2f".format(budgetV)
        expense.text = "$ %.2f".format(kotlin.math.abs(expenseV))
    }

    private fun getData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions")
        databaseReference.addValueEventListener(object: ValueEventListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    transactionList.clear()
                    for (transactionItem in snapshot.children) {
                        val transaction = transactionItem.getValue(Transaction::class.java)

                        if (transaction != null) {
                            if (LocalDate.parse(transaction.date).month == LocalDate.now().month) {
                                transactionList.add(transaction)
                            }
                        }
                    }

                    updateDashboard()

                    transactionAdapter = TransactionAdapter(transactionList)
                    recyclerView.adapter = transactionAdapter

                    transactionAdapter.onItemClick = {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("transaction", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}