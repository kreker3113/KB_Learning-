package dev.kbwallet.app.chart.domain.indicator

/**
 * Simple Moving Average.
 * Calculates SMA values for each index in the input list.
 *
 * @param values  The input price series (typically close prices)
 * @param period  The window size
 * @return List of SMA values, same length as input. First (period-1) entries are null.
 */
fun calculateSMA(values: List<Double>, period: Int): List<Double?> {
    if (values.size < period) return List(values.size) { null }

    val result = mutableListOf<Double?>()
    var sum = 0.0

    for (i in values.indices) {
        sum += values[i]
        if (i >= period) {
            sum -= values[i - period]
        }
        if (i >= period - 1) {
            result.add(sum / period)
        } else {
            result.add(null)
        }
    }
    return result
}

/**
 * Exponential Moving Average.
 */
fun calculateEMA(values: List<Double>, period: Int): List<Double?> {
    if (values.isEmpty()) return emptyList()
    val result = mutableListOf<Double?>()
    val multiplier = 2.0 / (period + 1)

    // First EMA value = SMA
    var ema = values.take(period).average()
    for (i in values.indices) {
        if (i < period - 1) {
            result.add(null)
        } else if (i == period - 1) {
            result.add(ema)
        } else {
            ema = (values[i] - ema) * multiplier + ema
            result.add(ema)
        }
    }
    return result
}
