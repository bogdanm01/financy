package com.example.financy.model

import java.time.LocalDate

data class Expense(
    val id: Int,
    val userId: Int,
    val categoryId: Int,
    val amount: Double,
    val date: LocalDate,
    val note: String? = null,
)