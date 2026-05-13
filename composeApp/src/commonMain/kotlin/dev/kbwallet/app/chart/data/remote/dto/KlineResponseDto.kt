package dev.kbwallet.app.chart.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Binance kline response is a List<List<Any>> where each inner list is:
 * [0] openTime (Long),   [1] open (String),   [2] high (String),
 * [3] low (String),      [4] close (String),  [5] volume (String),
 * [6] closeTime (Long),  [7] quoteVolume,     [8] trades,
 * [9] takerBuyBase,      [10] takerBuyQuote,  [11] ignore
 *
 * We wrap it for convenience.
 */
@Serializable
data class KlineResponseDto(
    val code: Int? = null,
    val msg: String? = null,
)

/**
 * Single kline raw entry that matches Binance JSON array element.
 * Each field is parsed from the array position.
 */
data class KlineRawDto(
    val openTime: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    val closeTime: Long,
    val quoteAssetVolume: Double,
    val numberOfTrades: Int,
    val takerBuyBaseVolume: Double,
    val takerBuyQuoteVolume: Double,
)
