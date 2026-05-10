package dev.kbwallet.app.profile.presentation

data class ProfileState(
    val displayName: String = "Quartz",
    val email: String = "quartz@email.com",
    val avatarInitial: String = "Q",
    val pushNotifications: Boolean = true,
    val emailNotifications: Boolean = false,
    val priceAlerts: Boolean = true,
    val tradeConfirmations: Boolean = true,
    val newsUpdates: Boolean = false,
    val biometricAuth: Boolean = false,
    val twoFactorAuth: Boolean = false,
    val totalTrades: Int = 0,
    val winRate: String = "+0%",
    val daysActive: String = "1",
)
