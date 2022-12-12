package com.example.financy

import com.example.financy.models.Transaction
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase

class TransactionRepository {
   companion object {
       private val dbInstance = FirebaseDatabase.getInstance()
       private val dbReference = dbInstance.getReference("Transactions")

       fun insertTransaction(transaction: Transaction): Task<Void> {
           val transactionId = dbReference.child("Transactions").push().key
           transaction.transactionId = transactionId
           return dbReference.child(transaction.transactionId!!).setValue(transaction)
       }

       fun deleteRecord(id: String): Task<Void> {
           val itemToDelete = dbReference.child(id)
           return itemToDelete.removeValue()
       }
   }
}