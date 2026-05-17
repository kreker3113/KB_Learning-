package dev.kbwallet.app.core.domain.currency

/**
 * Supported fiat currencies with their display properties.
 */
enum class Currency(
    val symbol: String,
    val code: String,
    val label: String,
) {
    USD(symbol = "$", code = "USD", label = "US Dollar"),
    RUB(symbol = "₽", code = "RUB", label = "₽убль");

    companion object {
        fun fromCode(code: String): Currency =
            entries.find { it.code.equals(code, ignoreCase = true) } ?: USD
    }
}
