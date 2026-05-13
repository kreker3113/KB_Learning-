package dev.kbwallet.app.watchlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WatchlistEntity")
data class WatchlistEntity(
    @PrimaryKey
    val coinId: String,
    val coinName: String,
    val coinSymbol: String,
    val iconUrl: String,
    val addedPrice: Double,
    val addedAt: Long,
)
