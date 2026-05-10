package dev.kbwallet.app.portfolio.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    values: List<Float>,
    colors: List<Color>,
    strokeWidth: Float = 40f,
) {
    if (values.isEmpty() || values.sum() == 0f) return

    val total = values.sum()
    val gapDegrees = 2f

    Canvas(
        modifier = modifier.aspectRatio(1f)
    ) {
        val canvasSize = size.minDimension
        val halfStroke = (strokeWidth / 2f)
        val topLeft = Offset(halfStroke, halfStroke)
        val arcSize = Size(canvasSize - strokeWidth, canvasSize - strokeWidth)

        var startAngle = -90f

        values.forEachIndexed { index, value ->
            val sweepAngle = (value / total) * 360f
            val adjustedSweep = if (sweepAngle > gapDegrees) sweepAngle - gapDegrees else sweepAngle

            if (adjustedSweep > 0f) {
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = adjustedSweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Butt,
                    )
                )
                startAngle += sweepAngle
            }
        }
    }
}
