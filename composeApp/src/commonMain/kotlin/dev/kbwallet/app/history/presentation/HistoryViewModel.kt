package dev.kbwallet.app.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kbwallet.app.core.util.formatCoinUnit
import dev.kbwallet.app.core.util.formatFiat
import dev.kbwallet.app.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HistoryViewModel(
    private val portfolioRepository: PortfolioRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState(isLoading = true))
    val state: StateFlow<HistoryState> = _state
        .onStart {
            _state.update { it.copy(isLoading = true) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HistoryState(isLoading = true)
        )

    init {
        viewModelScope.launch {
            val totalTrades = portfolioRepository.getTotalTradeCount()
            val totalBuy = portfolioRepository.getTotalBuyCount()
            val totalSell = portfolioRepository.getTotalSellCount()

            portfolioRepository.getAllTransactions().collect { transactions ->
                val uiTransactions = transactions.map { entity ->
                    val instant = Instant.fromEpochMilliseconds(entity.timestamp)
                    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                    val dateStr = "${localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)} ${localDateTime.dayOfMonth}"
                    val timeStr = "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"

                    TransactionUiModel(
                        id = entity.id,
                        coinName = entity.coinName,
                        coinSymbol = entity.coinSymbol,
                        type = entity.type,
                        amountInFiat = entity.amountInFiat,
                        formattedFiatAmount = formatFiat(entity.amountInFiat),
                        amountInUnit = entity.amountInUnit,
                        formattedUnitAmount = formatCoinUnit(entity.amountInUnit, entity.coinSymbol),
                        pricePerUnit = entity.pricePerUnit,
                        formattedPrice = formatFiat(entity.pricePerUnit),
                        timestamp = entity.timestamp,
                        formattedDate = dateStr,
                        formattedTime = timeStr,
                        status = entity.status,
                    )
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        transactions = uiTransactions,
                        totalTrades = totalTrades,
                        totalBuy = totalBuy,
                        totalSell = totalSell,
                    )
                }
            }
        }
    }
}
