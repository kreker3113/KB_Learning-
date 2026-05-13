package dev.kbwallet.app.history.presentation

data class HistoryState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionUiModel> = emptyList(),
    val totalTrades: Int = 0,
    val totalBuy: Int = 0,
    val totalSell: Int = 0,
    val editingTransactionId: Long? = null,
    val editNotes: String = "",
    val editTags: String = "",
)

data class TransactionUiModel(
    val id: Long,
    val coinName: String,
    val coinSymbol: String,
    val type: String, // "BUY" or "SELL"
    val amountInFiat: Double,
    val formattedFiatAmount: String,
    val amountInUnit: Double,
    val formattedUnitAmount: String,
    val pricePerUnit: Double,
    val formattedPrice: String,
    val timestamp: Long,
    val formattedDate: String,
    val formattedTime: String,
    val status: String,
    val notes: String = "",
    val tags: String = "",
)
