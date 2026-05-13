package dev.kbwallet.app.history.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LimitOrderEntity")
data class LimitOrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val coinId: String,
    val coinName: String,
    val coinSymbol: String,
    val iconUrl: String,
    val type: String,          // "LIMIT", "STOP_LOSS", "TAKE_PROFIT"
    val side: String,          // "BUY" or "SELL"
    val targetPrice: Double,
    val amountInFiat: Double,
    val amountInUnit: Double,
    val status: String = "ACTIVE", // "ACTIVE", "FILLED", "CANCELLED"
    val createdAt: Long,
)
