package dev.kbwallet.app.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KBLearningColorsPalette(
    var profitGreen: Color = Color.Unspecified,
    var lossRed: Color = Color.Unspecified,
)

val ProfitGreenColor = Color(color = 0xFF32de84)
val LossRedColor = Color(color = 0xFFD2122E)

val DarkProfitGreenColor = Color(color = 0xFF32de84)
val DarkLossRedColor = Color(color = 0xFFD2122E)

val LightKBLearningColorsPalette = KBLearningColorsPalette(
    profitGreen = ProfitGreenColor,
    lossRed = LossRedColor
)

val DarkKBLearningColorsPalette = KBLearningColorsPalette(
    profitGreen = ProfitGreenColor,
    lossRed = LossRedColor
)

val LocalKBLearningColorsPalette = compositionLocalOf { KBLearningColorsPalette() }