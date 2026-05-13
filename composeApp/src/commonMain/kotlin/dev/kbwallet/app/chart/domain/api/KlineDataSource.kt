package dev.kbwallet.app.chart.domain.api

import dev.kbwallet.app.chart.domain.model.CandleModel
import dev.kbwallet.app.chart.domain.model.TimeRange
import dev.kbwallet.app.core.domain.DataError
import dev.kbwallet.app.core.domain.Result

interface KlineDataSource {
    /**
     * Fetch OHLC candles for [symbol] (e.g. "BTCUSDT") at the given [interval].
     * Returns candles sorted oldest→newest.
     */
    suspend fun fetchKlines(
        symbol: String,
        interval: TimeRange,
    ): Result<List<CandleModel>, DataError.Remote>
}
