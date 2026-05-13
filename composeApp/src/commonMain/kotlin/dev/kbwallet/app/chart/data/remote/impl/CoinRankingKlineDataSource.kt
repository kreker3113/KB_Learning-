package dev.kbwallet.app.chart.data.remote.impl

import dev.kbwallet.app.chart.domain.api.KlineDataSource
import dev.kbwallet.app.chart.domain.model.CandleModel
import dev.kbwallet.app.chart.domain.model.TimeRange
import dev.kbwallet.app.coins.data.mapper.toPriceModel
import dev.kbwallet.app.coins.domain.api.CoinsRemoteDataSource
import dev.kbwallet.app.core.domain.DataError
import dev.kbwallet.app.core.domain.Result

/**
 * CoinRanking-backed OHLC data source.
 * Maps CoinRanking time-period strings and converts price history to pseudo-candles.
 */
class CoinRankingKlineDataSource(
    private val coinsApi: CoinsRemoteDataSource,
) : KlineDataSource {

    override suspend fun fetchKlines(
        symbol: String,   // here: CoinRanking coin UUID
        interval: TimeRange,
    ): Result<List<CandleModel>, DataError.Remote> {
        return when (val result = coinsApi.getPriceHistory(symbol, interval.coinRankingPeriod)) {
            is Result.Success -> {
                val priceModels = result.data.data.history
                    .map { it.toPriceModel() }
                    .sortedBy { it.timestamp }

                // Convert each price point to a candle (O=H=L=C=price)
                val candles = priceModels.map { pm ->
                    CandleModel(
                        openTime = pm.timestamp * 1000L, // CoinRanking uses seconds
                        open = pm.price,
                        high = pm.price,
                        low = pm.price,
                        close = pm.price,
                        volume = 0.0,
                    )
                }
                Result.Success(candles)
            }
            is Result.Error -> result
        }
    }
}


