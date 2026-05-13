package dev.kbwallet.app.chart.data.mapper

import dev.kbwallet.app.chart.data.remote.dto.KlineRawDto
import dev.kbwallet.app.chart.domain.model.CandleModel

fun KlineRawDto.toCandleModel(): CandleModel = CandleModel(
    openTime = openTime,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume,
)

fun List<KlineRawDto>.toCandleModels(): List<CandleModel> = map { it.toCandleModel() }
