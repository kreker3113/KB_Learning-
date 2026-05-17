package dev.kbwallet.app.chart.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.kbwallet.app.chart.domain.model.CandleModel
import dev.kbwallet.app.chart.presentation.util.ChartTransform

/**
 * Volume bars drawn below the price chart.
 * Green for up-days (close >= open), red for down-days.
 */
@Composable
fun VolumeBars(
    transform: ChartTransform,
    modifier: Modifier = Modifier,
    upColor: Color = Color(0xFF00FF00).copy(alpha = 0.4f),
    downColor: Color = Color(0xFFFF3B30).copy(alpha = 0.4f),
    heightFraction: Float = 0.15f,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val volumeHeight = size.height * heightFraction
        val volumeTop = size.height - volumeHeight
        val candles = transform.candles
        if (candles.isEmpty()) return@Canvas

        val visStart = transform.visibleStartIdx
        val visEnd = transform.visibleEndIdx
        val count = visEnd - visStart
        if (count == 0) return@Canvas

        val barWidth = (size.width / count * 0.7f).coerceIn(1f, 10f)
        val barSpacing = size.width / count
        val maxVol = transform.volumeRange.endInclusive
        if (maxVol == 0.0) return@Canvas

        for (i in visStart until visEnd) {
            val c = candles[i]
            val localIdx = i - visStart
            val centerX = (localIdx.toFloat() + 0.5f) * barSpacing
            val volFrac = (c.volume / maxVol).toFloat().coerceIn(0f, 1f)
            val barH = volFrac * volumeHeight
            val color = if (c.close >= c.open) upColor else downColor

            drawRect(
                color = color,
                topLeft = Offset(centerX - barWidth / 2, volumeTop + (volumeHeight - barH)),
                size = Size(barWidth, barH),
            )
        }
    }
}

/**
 * RSI overlay line — drawn in a separate panel below the chart.
 * Ranges 0-100, with overbought (70) and oversold (30) reference lines.
 */
@Composable
fun RsiOverlay(
    values: List<Double>,
    transform: ChartTransform,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFFFF9800),
    overboughtColor: Color = Color(0xFFFF3B30).copy(alpha = 0.3f),
    oversoldColor: Color = Color(0xFF00FF00).copy(alpha = 0.3f),
    overboughtLevel: Float = 70f,
    oversoldLevel: Float = 30f,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        if (values.isEmpty()) return@Canvas
        val h = size.height
        val w = size.width
        val visStart = transform.visibleStartIdx
        val visEnd = transform.visibleEndIdx.coerceAtMost(values.size)
        val count = visEnd - visStart
        if (count < 2) return@Canvas

        // Overbought / oversold zones
        drawRect(overboughtColor, Offset(0f, 0f), Size(w, h * (1f - overboughtLevel / 100f)))
        drawRect(oversoldColor, Offset(0f, h * (1f - oversoldLevel / 100f)), Size(w, h * oversoldLevel / 100f))

        // Reference lines
        val obY = h * (1f - overboughtLevel / 100f)
        val osY = h * (1f - oversoldLevel / 100f)
        drawLine(Color.Gray.copy(alpha = 0.4f), Offset(0f, obY), Offset(w, obY), strokeWidth = 1.dp.toPx())
        drawLine(Color.Gray.copy(alpha = 0.4f), Offset(0f, osY), Offset(w, osY), strokeWidth = 1.dp.toPx())

        // RSI line
        val spacing = w / count
        for (i in 1 until count) {
            val idx = visStart + i
            val prevIdx = idx - 1
            if (values[prevIdx].isNaN() || values[idx].isNaN()) continue
            val x0 = ((i - 1).toFloat() + 0.5f) * spacing
            val x1 = (i.toFloat() + 0.5f) * spacing
            val y0 = h * (1f - (values[prevIdx] / 100f).toFloat().coerceIn(0f, 1f))
            val y1 = h * (1f - (values[idx] / 100f).toFloat().coerceIn(0f, 1f))
            drawLine(lineColor, Offset(x0, y0), Offset(x1, y1), strokeWidth = 1.5.dp.toPx())
        }
    }
}

/**
 * MACD overlay: MACD line + signal line + histogram.
 */
@Composable
fun MacdOverlay(
    macdLine: List<Double>,
    signalLine: List<Double>,
    histogram: List<Double>,
    transform: ChartTransform,
    modifier: Modifier = Modifier,
    macdColor: Color = Color(0xFF2196F3),
    signalColor: Color = Color(0xFFFF5722),
    histUpColor: Color = Color(0xFF00FF00).copy(alpha = 0.5f),
    histDownColor: Color = Color(0xFFFF3B30).copy(alpha = 0.5f),
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val h = size.height
        val w = size.width
        val visStart = transform.visibleStartIdx
        val visEnd = transform.visibleEndIdx.coerceAtMost(macdLine.size)
        val count = visEnd - visStart
        if (count < 2) return@Canvas

        // Determine Y range
        val visibleMacd = macdLine.subList(visStart, visEnd).filterNot { it.isNaN() }
        val visibleHist = histogram.subList(visStart, visEnd).filterNot { it.isNaN() }
        val all = visibleMacd + visibleHist
        val yMin = all.minOrNull() ?: -1.0
        val yMax = all.maxOrNull() ?: 1.0
        val yRange = (yMax - yMin).coerceAtLeast(0.0001)
        val zeroY = h * (1f - ((0f - yMin) / yRange).toFloat().coerceIn(0f, 1f))

        // Zero line
        drawLine(Color.Gray.copy(alpha = 0.4f), Offset(0f, zeroY), Offset(w, zeroY), strokeWidth = 1.dp.toPx())

        fun toY(v: Double): Float = h * (1f - ((v - yMin) / yRange).toFloat().coerceIn(0f, 1f))

        val spacing = w / count
        val barWidth = (spacing * 0.6f).coerceIn(1f, 6f)

        // Histogram
        for (i in 0 until count) {
            val idx = visStart + i
            if (histogram[idx].isNaN()) continue
            val cx = (i.toFloat() + 0.5f) * spacing
            val valY = toY(histogram[idx])
            val color = if (histogram[idx] >= 0) histUpColor else histDownColor
            val top = if (histogram[idx] >= 0) valY else zeroY
            val bottom = if (histogram[idx] >= 0) zeroY else valY
            drawRect(color, Offset(cx - barWidth / 2, top), Size(barWidth, (bottom - top).coerceAtLeast(0.5f)))
        }

        // MACD line
        for (i in 1 until count) {
            val idx = visStart + i
            val prevIdx = idx - 1
            if (macdLine[prevIdx].isNaN() || macdLine[idx].isNaN()) continue
            val x0 = ((i - 1).toFloat() + 0.5f) * spacing
            val x1 = (i.toFloat() + 0.5f) * spacing
            drawLine(macdColor, Offset(x0, toY(macdLine[prevIdx])), Offset(x1, toY(macdLine[idx])), strokeWidth = 1.5.dp.toPx())
        }

        // Signal line
        for (i in 1 until count) {
            val idx = visStart + i
            val prevIdx = idx - 1
            if (signalLine[prevIdx].isNaN() || signalLine[idx].isNaN()) continue
            val x0 = ((i - 1).toFloat() + 0.5f) * spacing
            val x1 = (i.toFloat() + 0.5f) * spacing
            drawLine(signalColor, Offset(x0, toY(signalLine[prevIdx])), Offset(x1, toY(signalLine[idx])), strokeWidth = 1.5.dp.toPx())
        }
    }
}

/**
 * Bollinger Bands overlay on the main price chart.
 * Middle SMA band + upper/lower envelope lines + filled channel.
 */
@Composable
fun BollingerOverlay(
    middle: List<Double>,
    upper: List<Double>,
    lower: List<Double>,
    transform: ChartTransform,
    modifier: Modifier = Modifier,
    middleColor: Color = Color(0xFFFFEB3B).copy(alpha = 0.6f),
    bandColor: Color = Color(0xFF2196F3).copy(alpha = 0.4f),
    fillColor: Color = Color(0xFF2196F3).copy(alpha = 0.05f),
    chartHeightFraction: Float = 0.85f,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val chartHeight = size.height * chartHeightFraction
        val w = size.width
        val visStart = transform.visibleStartIdx
        val visEnd = transform.visibleEndIdx.coerceAtMost(middle.size)
        val count = visEnd - visStart
        if (count < 2) return@Canvas

        fun toY(price: Double): Float = transform.priceToFraction(price) * chartHeight

        val spacing = w / count

        // Fill between bands
        val fillPath = androidx.compose.ui.graphics.Path()
        // Upper band left to right
        val firstValid = (visStart until visEnd).firstOrNull { !upper[it].isNaN() } ?: return@Canvas
        fillPath.moveTo(((firstValid - visStart).toFloat() + 0.5f) * spacing, toY(upper[firstValid]))
        for (i in visStart until visEnd) {
            if (upper[i].isNaN()) continue
            fillPath.lineTo(((i - visStart).toFloat() + 0.5f) * spacing, toY(upper[i]))
        }
        // Lower band right to left
        for (i in visEnd - 1 downTo visStart) {
            if (lower[i].isNaN()) continue
            fillPath.lineTo(((i - visStart).toFloat() + 0.5f) * spacing, toY(lower[i]))
        }
        fillPath.close()
        drawPath(fillPath, fillColor)

        // Middle line
        for (i in 1 until count) {
            val idx = visStart + i
            val prevIdx = idx - 1
            if (middle[prevIdx].isNaN() || middle[idx].isNaN()) continue
            val x0 = ((i - 1).toFloat() + 0.5f) * spacing
            val x1 = (i.toFloat() + 0.5f) * spacing
            drawLine(middleColor, Offset(x0, toY(middle[prevIdx])), Offset(x1, toY(middle[idx])), strokeWidth = 1.dp.toPx())
        }

        // Upper band (dashed style — simplified as thin solid)
        for (i in 1 until count) {
            val idx = visStart + i
            val prevIdx = idx - 1
            if (upper[prevIdx].isNaN() || upper[idx].isNaN()) continue
            val x0 = ((i - 1).toFloat() + 0.5f) * spacing
            val x1 = (i.toFloat() + 0.5f) * spacing
            drawLine(bandColor, Offset(x0, toY(upper[prevIdx])), Offset(x1, toY(upper[idx])), strokeWidth = 1.dp.toPx())
        }

        // Lower band
        for (i in 1 until count) {
            val idx = visStart + i
            val prevIdx = idx - 1
            if (lower[prevIdx].isNaN() || lower[idx].isNaN()) continue
            val x0 = ((i - 1).toFloat() + 0.5f) * spacing
            val x1 = (i.toFloat() + 0.5f) * spacing
            drawLine(bandColor, Offset(x0, toY(lower[prevIdx])), Offset(x1, toY(lower[idx])), strokeWidth = 1.dp.toPx())
        }
    }
}
