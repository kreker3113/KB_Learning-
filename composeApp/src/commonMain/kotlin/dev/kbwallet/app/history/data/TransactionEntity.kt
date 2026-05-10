package dev.kbwallet.app.history.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val coinId: String,
    val coinName: String,
    val coinSymbol: String,
    val type: String, // "BUY" or "SELL"
    val amountInFiat: Double,
    val amountInUnit: Double,
    val pricePerUnit: Double,
    val timestamp: Long,
    val status: String = "Completed",
)
