package dev.kbwallet.app.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KBLearningColorsPalette(
    val profitGreen: Color = Color.Unspecified,
    val lossRed: Color = Color.Unspecified,
)

val ProfitGreenColor = Color(0xFF00FF00)
val LossRedColor = Color(0xFFFF3B30)

val DarkProfitGreenColor = Color(0xFF00FF00)
val DarkLossRedColor = Color(0xFFFF3B30)

val LightKBLearningColorsPalette = KBLearningColorsPalette(
    profitGreen = ProfitGreenColor,
    lossRed = LossRedColor
)

val DarkKBLearningColorsPalette = KBLearningColorsPalette(
    profitGreen = DarkProfitGreenColor,
    lossRed = DarkLossRedColor
)

val LocalKBLearningColorsPalette = compositionLocalOf { KBLearningColorsPalette() }
