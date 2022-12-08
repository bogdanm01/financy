package com.example.financy

import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase

class TransactionRepository {
   companion object {
       public fun deleteRecord(id: String):Task<Void> {
           val dbRef = FirebaseDatabase.getInstance().getReference("Transactions").child(id);
           val mTask = dbRef.removeValue()
           return mTask;
       }

       fun insertTransaction() {

       }

       fun getAllTransactions() {

       }
   }
}