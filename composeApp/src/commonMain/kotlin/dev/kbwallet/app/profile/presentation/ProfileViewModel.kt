package dev.kbwallet.app.profile.presentation

import androidx.lifecycle.ViewModel
import dev.kbwallet.app.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val portfolioRepository: PortfolioRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        // Stats would be loaded from repository
        // For now, using defaults from ProfileState
    }

    fun updateDisplayName(name: String) {
        _state.update {
            it.copy(
                displayName = name,
                avatarInitial = name.firstOrNull()?.uppercase() ?: "Q"
            )
        }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun togglePushNotifications() {
        _state.update { it.copy(pushNotifications = !it.pushNotifications) }
    }

    fun toggleEmailNotifications() {
        _state.update { it.copy(emailNotifications = !it.emailNotifications) }
    }

    fun togglePriceAlerts() {
        _state.update { it.copy(priceAlerts = !it.priceAlerts) }
    }

    fun toggleTradeConfirmations() {
        _state.update { it.copy(tradeConfirmations = !it.tradeConfirmations) }
    }

    fun toggleNewsUpdates() {
        _state.update { it.copy(newsUpdates = !it.newsUpdates) }
    }

    fun toggleBiometricAuth() {
        _state.update { it.copy(biometricAuth = !it.biometricAuth) }
    }

    fun toggleTwoFactorAuth() {
        _state.update { it.copy(twoFactorAuth = !it.twoFactorAuth) }
    }
}
