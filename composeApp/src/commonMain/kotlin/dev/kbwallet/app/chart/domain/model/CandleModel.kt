package dev.kbwallet.app.chart.domain.model

/**
 * Single OHLC candle with volume.
 */
data class CandleModel(
    val openTime: Long,       // epoch millis
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,       // base asset volume
)

/**
 * True when close >= open (green / bullish).
 */
val CandleModel.isBullish: Boolean get() = close >= open
