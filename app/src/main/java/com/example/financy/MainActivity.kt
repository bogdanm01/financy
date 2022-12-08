package com.example.financy

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financy.databinding.ActivityMainBinding
import com.example.financy.databinding.ActivitySignUpBinding
import com.example.financy.model.Transaction
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_transaction.*
import java.lang.Math.abs
import java.security.AccessController.getContext
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

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

        transactionList = arrayListOf<com.example.financy.model.Transaction>()

        getData()

        newTransactionSwitch.setOnClickListener {
            val intent = Intent(this, NewTransaction::class.java)
            startActivity(intent)
        }

    }

    private fun updateDashboard() {
        val totalBalance : Double = transactionList.sumOf { it.amount!! }
        val budgetV = transactionList.filter { it.amount!! > 0 }.sumOf { it.amount!! }
        val expenseV = totalBalance - budgetV

        Log.i("TOTAL BALANCE", totalBalance.toString())

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
                        val transaction = transactionItem.getValue(com.example.financy.model.Transaction::class.java)

                        if (transaction != null) {
                            if (LocalDate.parse(transaction.date).month == LocalDate.now().month) {
                                transactionList.add(transaction!!)
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