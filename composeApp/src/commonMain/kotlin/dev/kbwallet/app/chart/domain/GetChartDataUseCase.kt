package dev.kbwallet.app.chart.domain

import dev.kbwallet.app.chart.domain.api.KlineDataSource
import dev.kbwallet.app.chart.domain.model.CandleModel
import dev.kbwallet.app.chart.domain.model.TimeRange
import dev.kbwallet.app.core.domain.DataError
import dev.kbwallet.app.core.domain.Result

class GetChartDataUseCase(
    private val klineDataSource: KlineDataSource,
) {
    /**
     * Fetch candles for a trading pair symbol (e.g. "BTCUSDT").
     */
    suspend fun execute(
        symbol: String,
        timeRange: TimeRange,
    ): Result<List<CandleModel>, DataError.Remote> {
        return klineDataSource.fetchKlines(symbol, timeRange)
    }
}
