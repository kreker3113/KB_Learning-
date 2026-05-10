package dev.kbwallet.app.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kbwallet.app.coins.domain.GetCoinsListUseCase
import dev.kbwallet.app.core.domain.Result
import dev.kbwallet.app.core.util.formatFiat
import dev.kbwallet.app.core.util.formatPercentage
import dev.kbwallet.app.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val portfolioRepository: PortfolioRepository,
    private val getCoinsListUseCase: GetCoinsListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState(isLoading = true))
    val state: StateFlow<DashboardState> = _state
        .onStart {
            _state.update { it.copy(isLoading = true) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState(isLoading = true)
        )

    init {
        // ── Portfolio coins (reactive) ──
        viewModelScope.launch {
            portfolioRepository.allPortfolioCoinsFlow().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val coins = result.data
                        val summaryItems = coins.take(3).map { coin ->
                            DashboardCoinItem(
                                id = coin.coin.id,
                                name = coin.coin.name,
                                symbol = coin.coin.symbol,
                                iconUrl = coin.coin.iconUrl,
                                formattedPrice = formatFiat(coin.ownedAmountInFiat),
                                formattedChange = formatPercentage(coin.performancePercent),
                                isPositive = coin.performancePercent >= 0,
                            )
                        }
                        _state.update {
                            it.copy(
                                isLoading = false,
                                coinCount = coins.size,
                                portfolioSummaryCoins = summaryItems,
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }

        // ── Top coins (one-shot) ──
        viewModelScope.launch {
            when (val coinsResult = getCoinsListUseCase.execute()) {
                is Result.Success -> {
                    val topCoins = coinsResult.data.take(5).map { coin ->
                        DashboardCoinItem(
                            id = coin.coin.id,
                            name = coin.coin.name,
                            symbol = coin.coin.symbol,
                            iconUrl = coin.coin.iconUrl,
                            formattedPrice = formatFiat(coin.price),
                            formattedChange = formatPercentage(coin.change),
                            isPositive = coin.change >= 0,
                        )
                    }
                    _state.update { it.copy(topCoins = topCoins) }
                }
                is Result.Error -> { /* ignore */ }
            }
        }
    }
}
