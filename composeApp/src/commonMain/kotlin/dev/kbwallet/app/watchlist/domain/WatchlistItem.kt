package dev.kbwallet.app.watchlist.domain

import dev.kbwallet.app.core.domain.coin.Coin

data class WatchlistItem(
    val coin: Coin,
    val currentPrice: Double,
    val change24h: Double,
    val addedPrice: Double,
    val addedAt: Long,
)
