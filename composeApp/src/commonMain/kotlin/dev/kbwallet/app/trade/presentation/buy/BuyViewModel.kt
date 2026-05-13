package dev.kbwallet.app.trade.presentation.buy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kbwallet.app.coins.domain.GetCoinDetailsUseCase
import dev.kbwallet.app.core.domain.Result
import dev.kbwallet.app.core.util.formatFiat
import dev.kbwallet.app.core.util.toUiText
import dev.kbwallet.app.portfolio.domain.PortfolioRepository
import dev.kbwallet.app.trade.domain.BuyCoinUseCase
import dev.kbwallet.app.trade.presentation.common.TradeState
import dev.kbwallet.app.trade.presentation.common.UiTradeCoinItem
import dev.kbwallet.app.trade.presentation.mapper.toCoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BuyViewModel(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val portfolioRepository: PortfolioRepository,
    private val buyCoinUseCase: BuyCoinUseCase,
    private val coinId: String,
) : ViewModel() {

    private val _amount = MutableStateFlow("")
    private val _state = MutableStateFlow(TradeState())
    val state = combine(
        _state,
        _amount,
    ) { state, amount ->
        state.copy(
            amount = amount
        )
    }.onStart {
        val balance = portfolioRepository.cashBalanceFlow().first()
        getCoinDetails(balance)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TradeState(isLoading = true)
    )

    private val _events = Channel<BuyEvents>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private suspend fun getCoinDetails(balance: Double) {
        when (val coinResponse = getCoinDetailsUseCase.execute(coinId)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        coin = UiTradeCoinItem(
                            id = coinResponse.data.coin.id,
                            name = coinResponse.data.coin.name,
                            symbol = coinResponse.data.coin.symbol,
                            iconUrl = coinResponse.data.coin.iconUrl,
                            price = coinResponse.data.price,
                        ),
                        availableAmount = "Available: ${formatFiat(balance)}"
                    )
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = coinResponse.error.toUiText()
                    )
                }
            }
        }
    }

    fun onAmountChanged(amount: String) {
        _amount.value = amount
        if (_state.value.isAmountInUnits && _state.value.coin != null) {
            val num = amount.toDoubleOrNull()
            _state.update {
                it.copy(
                    fiatEquivalent = if (num != null) "≈ ${formatFiat(num * it.coin!!.price)}"
                    else ""
                )
            }
        }
    }

    fun onToggleMode() {
        val currentCoin = _state.value.coin ?: return
        val currentAmount = _amount.value.toDoubleOrNull() ?: 0.0
        _state.update { it.copy(isAmountInUnits = !it.isAmountInUnits, fiatEquivalent = "") }
        // Clear amount when switching mode
        _amount.value = ""
        // Update available amount for the new mode
        viewModelScope.launch {
            val balance = portfolioRepository.cashBalanceFlow().first()
            if (_state.value.isAmountInUnits) {
                val maxUnits = if (currentCoin.price > 0) balance / currentCoin.price else 0.0
                _state.update {
                    it.copy(availableAmount = "Available: ${formatFiat(maxUnits, showDecimal = false)} ${currentCoin.symbol}")
                }
            } else {
                _state.update {
                    it.copy(availableAmount = "Available: ${formatFiat(balance)}")
                }
            }
        }
    }

    fun onBuyClicked() {
        val tradeCoin = state.value.coin ?: return
        val amount = _amount.value.toDoubleOrNull()
        if (amount == null || amount <= 0.0) {
            return
        }
        viewModelScope.launch {
            val fiatAmount = if (_state.value.isAmountInUnits) amount * tradeCoin.price else amount
            val buyCoinResponse = buyCoinUseCase.buyCoin(
                coin = tradeCoin.toCoin(),
                amountInFiat = fiatAmount,
                price = tradeCoin.price,
            )

            when(buyCoinResponse) {
                is Result.Success -> {
                    _events.send(BuyEvents.BuySuccess)
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = buyCoinResponse.error.toUiText(),
                        )
                    }
                }
            }
        }
    }
}

sealed interface BuyEvents {
    data object BuySuccess : BuyEvents
}
