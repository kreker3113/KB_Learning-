package dev.kbwallet.app.chart.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.kbwallet.app.chart.domain.model.TimeRange
import dev.kbwallet.app.theme.DarkProfitGreenColor

@Composable
fun TimeRangeSelector(
    selected: TimeRange,
    onSelect: (TimeRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TimeRange.entries.forEach { range ->
            val isSel = range == selected
            Text(
                text = range.label,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                color = if (isSel) DarkProfitGreenColor else Color.White.copy(alpha = 0.35f),
                modifier = Modifier.clickable { onSelect(range) },
            )
        }
    }
}
