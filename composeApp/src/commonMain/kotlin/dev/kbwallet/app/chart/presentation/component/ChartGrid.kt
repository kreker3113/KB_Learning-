package dev.kbwallet.app.chart.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.kbwallet.app.chart.presentation.util.ChartTransform

private val GridCol = Color.White.copy(alpha = 0.05f)

@Composable
fun ChartGrid(
    transform: ChartTransform,
    modifier: Modifier = Modifier,
    chartArea: Float = 0.85f,
    lines: Int = 4,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val h = size.height * chartArea
        for (i in 0..lines) {
            val y = i.toFloat() / lines * h
            drawLine(GridCol, Offset(0f, y), Offset(size.width, y), 1f)
        }
    }
}
