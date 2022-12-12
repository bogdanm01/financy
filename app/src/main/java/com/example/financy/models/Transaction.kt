package com.example.financy.models

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

data class Transaction(
    var transactionId : String? = null,
    val name: String? = null,
    val amount: Double? = 0.0,
    val date: String? = LocalDate.now().toString(),
    val category: String? = null,
    val note: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(transactionId)
        parcel.writeString(name)
        parcel.writeDouble(amount!!)
        parcel.writeString(date)
        parcel.writeString(category)
        parcel.writeString(note)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}