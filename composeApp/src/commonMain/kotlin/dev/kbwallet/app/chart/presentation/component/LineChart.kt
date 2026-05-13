package dev.kbwallet.app.chart.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dev.kbwallet.app.chart.presentation.util.ChartTransform

@Composable
fun LineChart(
    transform: ChartTransform,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF00FF00),
    chartArea: Float = 0.85f,
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(transform) {
                detectTapGestures { /* no-op — clean look */ }
            }
    ) {
        val h = size.height * chartArea
        val w = size.width
        val candles = transform.candles
        if (candles.isEmpty()) return@Canvas

        // ── Build path from all visible points ──
        val path = Path()
        var firstX = 0f
        var started = false

        for (i in transform.visibleStartIdx until transform.visibleEndIdx) {
            val c = candles[i]
            val x = transform.indexToFraction(i) * w
            val y = transform.priceToFraction(c.close) * h
            if (!started) {
                path.moveTo(x, y)
                firstX = x
                started = true
            } else {
                path.lineTo(x, y)
            }
        }

        if (!started) return@Canvas

        val lastX = transform.indexToFraction(transform.visibleEndIdx - 1) * w

        // ── Gradient fill under the line ──
        val fillPath = Path().apply {
            addPath(path)
            lineTo(lastX, h)
            lineTo(firstX, h)
            close()
        }

        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                0.0f to lineColor.copy(alpha = 0.18f),
                0.6f to lineColor.copy(alpha = 0.04f),
                1.0f to Color.Transparent,
                startY = 0f,
                endY = h,
            ),
        )

        // ── Line on top ──
        drawPath(
            path,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
        )

        // ── Subtle glow ──
        drawPath(
            path,
            color = lineColor.copy(alpha = 0.30f),
            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
        )

        // ── Last price dot ──
        val last = candles.last()
        val lx = transform.indexToFraction(candles.size - 1) * w
        val ly = transform.priceToFraction(last.close) * h
        drawCircle(Color.White, 4.dp.toPx(), Offset(lx, ly))
        drawCircle(lineColor, 5.5.dp.toPx(), Offset(lx, ly), style = Stroke(1.5.dp.toPx()))

        // ── Max / Min dots ──
        val vis = transform.visibleCandles
        if (vis.isNotEmpty()) {
            val maxC = vis.maxBy { it.high }
            val minC = vis.minBy { it.low }
            val maxIdx = candles.indexOf(maxC)
            val minIdx = candles.indexOf(minC)
            if (maxIdx >= 0) {
                val mx = transform.indexToFraction(maxIdx) * w
                val my = transform.priceToFraction(maxC.high) * h
                drawCircle(lineColor.copy(alpha = 0.3f), 3.dp.toPx(), Offset(mx, my))
            }
            if (minIdx >= 0) {
                val mx = transform.indexToFraction(minIdx) * w
                val my = transform.priceToFraction(minC.low) * h
                drawCircle(Color(0xFFFF3B30).copy(alpha = 0.3f), 3.dp.toPx(), Offset(mx, my))
            }
        }
    }
}
