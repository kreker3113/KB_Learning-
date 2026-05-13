package dev.kbwallet.app.chart.presentation.util

import dev.kbwallet.app.chart.domain.model.CandleModel
import kotlin.math.max

/**
 * Maps between data space (candle index / price) and screen fractions (0..1).
 * Supports a visible sub-range for pan & zoom.
 */
class ChartTransform(
    val candles: List<CandleModel>,
    private var _visibleStart: Float = 0f,
    private var _visibleEnd: Float = 1f,
) {
    val visibleStartIdx: Int get() = (candles.size * _visibleStart).toInt().coerceIn(0, candles.lastIndex)
    val visibleEndIdx: Int get() = (candles.size * _visibleEnd).toInt().coerceIn(visibleStartIdx + 1, candles.size)
    val visibleCount: Int get() = max(1, visibleEndIdx - visibleStartIdx)

    val visibleCandles: List<CandleModel>
        get() = candles.subList(visibleStartIdx, visibleEndIdx)

    val priceRange: ClosedFloatingPointRange<Double>
        get() {
            val vs = visibleCandles
            if (vs.isEmpty()) return 0.0..1.0
            val high = vs.maxOf { it.high }
            val low = vs.minOf { it.low }
            val pad = (high - low) * 0.05
            return (low - pad)..(high + pad)
        }

    val volumeRange: ClosedFloatingPointRange<Double>
        get() {
            val maxVol = visibleCandles.maxOfOrNull { it.volume } ?: 1.0
            return 0.0..(maxVol * 1.05)
        }

    val span: Float get() = _visibleEnd - _visibleStart

    fun pan(deltaFraction: Float) {
        val d = deltaFraction.coerceIn(-_visibleStart, 1f - _visibleEnd)
        _visibleStart += d
        _visibleEnd += d
    }

    fun zoom(scaleFactor: Float, anchorFraction: Float) {
        val newSpan = (span / scaleFactor).coerceIn(0.02f, 1f)
        val anchor = _visibleStart + anchorFraction * span
        _visibleStart = (anchor - anchorFraction * newSpan).coerceIn(0f, 1f - newSpan)
        _visibleEnd = _visibleStart + newSpan
    }

    fun indexToFraction(globalIndex: Int): Float {
        val s = visibleEndIdx - visibleStartIdx
        if (s == 0) return 0f
        return ((globalIndex - visibleStartIdx).toFloat() / s).coerceIn(0f, 1f)
    }

    fun priceToFraction(price: Double): Float {
        val r = priceRange
        val s = r.endInclusive - r.start
        if (s == 0.0) return 0.5f
        return (1.0 - (price - r.start) / s).toFloat().coerceIn(0f, 1f)
    }

    fun volumeToFraction(volume: Double): Float {
        val maxV = volumeRange.endInclusive
        if (maxV == 0.0) return 0f
        return (volume / maxV).toFloat().coerceIn(0f, 1f)
    }

    fun reset() { _visibleStart = 0f; _visibleEnd = 1f }
}
