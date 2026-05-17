package dev.kbwallet.app.chart.domain.indicator

import dev.kbwallet.app.chart.domain.model.CandleModel
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Pure calculation functions for technical indicators.
 * All functions take a list of [CandleModel] and return indicator values
 * aligned to the same index (padding with NaN for leading entries where there isn't enough data).
 */

// ── RSI (Relative Strength Index) ──

fun calculateRsi(candles: List<CandleModel>, period: Int = 14): List<Double> {
    if (candles.size < period + 1) return List(candles.size) { Double.NaN }

    val result = MutableList(candles.size) { Double.NaN }
    val changes = candles.zipWithNext { a, b -> b.close - a.close }
    val gains = changes.map { max(it, 0.0) }
    val losses = changes.map { max(-it, 0.0) }

    // Initial average
    var avgGain = gains.take(period).sum() / period
    var avgLoss = losses.take(period).sum() / period

    result[period] = if (avgLoss == 0.0) 100.0 else 100.0 - 100.0 / (1.0 + avgGain / avgLoss)

    // Wilder's smoothing for remaining values
    for (i in period until changes.size) {
        avgGain = (avgGain * (period - 1) + gains[i]) / period
        avgLoss = (avgLoss * (period - 1) + losses[i]) / period
        result[i + 1] = if (avgLoss == 0.0) 100.0 else 100.0 - 100.0 / (1.0 + avgGain / avgLoss)
    }

    return result
}

// ── MACD ──

data class MacdResult(
    val macdLine: List<Double>,
    val signalLine: List<Double>,
    val histogram: List<Double>,
)

fun calculateMacd(
    candles: List<CandleModel>,
    fastPeriod: Int = 12,
    slowPeriod: Int = 26,
    signalPeriod: Int = 9,
): MacdResult {
    val closes = candles.map { it.close }
    val emaFast = calculateEma(closes, fastPeriod)
    val emaSlow = calculateEma(closes, slowPeriod)

    val macdLine = emaFast.zip(emaSlow).map { (f, s) ->
        if (f.isNaN() || s.isNaN()) Double.NaN else f - s
    }

    val signalLine = calculateEma(macdLine.filterNot { it.isNaN() }, signalPeriod)
    // Pad signal back to full length
    val paddedSignal = MutableList(candles.size) { Double.NaN }
    val signalOffset = candles.size - macdLine.count { !it.isNaN() } + (signalPeriod - 1)
    for (i in signalLine.indices) {
        val targetIdx = signalOffset + i
        if (targetIdx in paddedSignal.indices) paddedSignal[targetIdx] = signalLine[i]
    }

    val histogram = macdLine.zip(paddedSignal).map { (m, s) ->
        if (m.isNaN() || s.isNaN()) Double.NaN else m - s
    }

    return MacdResult(macdLine, paddedSignal, histogram)
}

// ── EMA (Exponential Moving Average) ──

fun calculateEma(values: List<Double>, period: Int): List<Double> {
    if (values.size < period) return List(values.size) { Double.NaN }
    val result = MutableList(values.size) { Double.NaN }
    val multiplier = 2.0 / (period + 1)

    // Initial EMA = SMA of first 'period' values
    result[period - 1] = values.take(period).sum() / period
    for (i in period until values.size) {
        result[i] = (values[i] - result[i - 1]) * multiplier + result[i - 1]
    }
    return result
}

fun calculateEmaFromCandles(candles: List<CandleModel>, period: Int): List<Double> =
    calculateEma(candles.map { it.close }, period)

// ── SMA (Simple Moving Average) ──

fun calculateSma(values: List<Double>, period: Int): List<Double> {
    if (values.size < period) return List(values.size) { Double.NaN }
    val result = MutableList(values.size) { Double.NaN }
    for (i in period - 1 until values.size) {
        result[i] = values.subList(i - period + 1, i + 1).sum() / period
    }
    return result
}

fun calculateSmaFromCandles(candles: List<CandleModel>, period: Int): List<Double> =
    calculateSma(candles.map { it.close }, period)

// ── Bollinger Bands ──

data class BollingerResult(
    val middle: List<Double>,
    val upper: List<Double>,
    val lower: List<Double>,
    val bandwidth: List<Double>,
)

fun calculateBollingerBands(
    candles: List<CandleModel>,
    period: Int = 20,
    multiplier: Double = 2.0,
): BollingerResult {
    val closes = candles.map { it.close }
    val middle = calculateSma(closes, period)
    val upper = MutableList(candles.size) { Double.NaN }
    val lower = MutableList(candles.size) { Double.NaN }
    val bandwidth = MutableList(candles.size) { Double.NaN }

    for (i in period - 1 until candles.size) {
        val window = closes.subList(i - period + 1, i + 1)
        val mean = window.sum() / period
        val variance = window.map { (it - mean) * (it - mean) }.sum() / period
        val stddev = sqrt(variance)
        upper[i] = mean + multiplier * stddev
        lower[i] = mean - multiplier * stddev
        bandwidth[i] = if (mean > 0) (upper[i] - lower[i]) / mean * 100.0 else 0.0
    }

    return BollingerResult(middle, upper, lower, bandwidth)
}

// ── Volume profile (total volume in visible range) ──

fun calculateVolumeProfile(candles: List<CandleModel>, bins: Int = 20): List<Pair<Double, Double>> {
    if (candles.isEmpty()) return emptyList()
    val priceMin = candles.minOf { it.low }
    val priceMax = candles.maxOf { it.high }
    val binSize = (priceMax - priceMin) / bins
    if (binSize <= 0) return candles.map { it.close to it.volume }

    val profile = Array(bins) { 0.0 }
    for (c in candles) {
        val bin = ((c.close - priceMin) / binSize).toInt().coerceIn(0, bins - 1)
        profile[bin] += c.volume
    }
    return profile.mapIndexed { i, vol -> priceMin + binSize * (i + 0.5) to vol }
}
