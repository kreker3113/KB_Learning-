package dev.kbwallet.app.watchlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kbwallet.app.core.util.formatFiat
import dev.kbwallet.app.core.util.formatPercentage
import dev.kbwallet.app.watchlist.domain.WatchlistItem
import dev.kbwallet.app.watchlist.domain.WatchlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class WatchlistState(
    val items: List<UiWatchlistItem> = emptyList(),
    val isLoading: Boolean = true,
)

data class UiWatchlistItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPositive: Boolean,
    val addedPriceFormatted: String,
)

class WatchlistViewModel(
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WatchlistState())
    val state: StateFlow<WatchlistState> = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WatchlistState())

    init {
        viewModelScope.launch {
            watchlistRepository.getWatchlistWithPrices().collect { items ->
                _state.value = WatchlistState(
                    isLoading = false,
                    items = items.map { it.toUi() }
                )
            }
        }
    }

    fun removeItem(coinId: String) {
        viewModelScope.launch {
            watchlistRepository.removeFromWatchlist(coinId)
        }
    }
}

private fun WatchlistItem.toUi(): UiWatchlistItem = UiWatchlistItem(
    id = coin.id,
    name = coin.name,
    symbol = coin.symbol,
    iconUrl = coin.iconUrl,
    formattedPrice = formatFiat(currentPrice),
    formattedChange = formatPercentage(change24h),
    isPositive = change24h >= 0,
    addedPriceFormatted = formatFiat(addedPrice),
)
