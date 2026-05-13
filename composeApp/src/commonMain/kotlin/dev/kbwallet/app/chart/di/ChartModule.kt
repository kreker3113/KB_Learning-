package dev.kbwallet.app.chart.di

import dev.kbwallet.app.chart.data.remote.impl.CoinRankingKlineDataSource
import dev.kbwallet.app.chart.domain.GetChartDataUseCase
import dev.kbwallet.app.chart.domain.api.KlineDataSource
import dev.kbwallet.app.chart.presentation.ChartViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val chartModule = module {
    single<KlineDataSource> { CoinRankingKlineDataSource(get()) }
    factoryOf(::GetChartDataUseCase)
    factoryOf(::ChartViewModel)
}
