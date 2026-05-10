package dev.kbwallet.app.core.navigation

import kotlinx.serialization.Serializable

// ── App entry ──
@Serializable
object Biometric

// ── Main tabs (BottomNavigationBar) ──
@Serializable
object Dashboard

@Serializable
object Portfolio

@Serializable
object History

@Serializable
object Profile

// ── Secondary screens ──
@Serializable
object Coins

@Serializable
data class Buy(val coinId: String)

@Serializable
data class Sell(val coinId: String)

// ── Profile sub-screens ──
@Serializable
object EditProfile

@Serializable
object NotificationSettings

@Serializable
object SecuritySettings

@Serializable
object HelpSupport
