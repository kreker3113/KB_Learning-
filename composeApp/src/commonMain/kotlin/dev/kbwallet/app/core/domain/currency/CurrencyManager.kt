package dev.kbwallet.app.core.domain.currency

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Central manager for fiat currency selection and conversion.
 * All prices are stored in USD; this class converts on-the-fly.
 */
class CurrencyManager {
    private val mutex = Mutex()

    var currentCurrency by mutableStateOf(Currency.USD)
        private set

    /** Exchange rate: 1 USD = X RUB */
    var rubPerUsd by mutableStateOf(90.0)
        private set

    suspend fun setCurrency(currency: Currency) = mutex.withLock {
        currentCurrency = currency
    }

    suspend fun updateRubRate(rate: Double) = mutex.withLock {
        rubPerUsd = rate
    }

    fun convert(usdAmount: Double): Double = when (currentCurrency) {
        Currency.USD -> usdAmount
        Currency.RUB -> usdAmount * rubPerUsd
    }

    fun format(usdAmount: Double, showDecimal: Boolean = true): String {
        val converted = convert(usdAmount)
        val absConverted = kotlin.math.abs(converted)
        val formattedNumber = when {
            !showDecimal -> formatWithCommas(converted, 0)
            absConverted >= 1_000 -> formatWithCommas(converted, 0)
            absConverted >= 1.0 -> roundToString(converted, 2)
            absConverted >= 0.01 -> roundToString(converted, 4)
            absConverted >= 0.0001 -> roundToString(converted, 6)
            else -> roundToString(converted, 8)
        }
        return "${currentCurrency.symbol}$formattedNumber"
    }

    fun formatSigned(usdAmount: Double, showDecimal: Boolean = true): String {
        val converted = convert(usdAmount)
        val prefix = if (converted >= 0) "+" else ""
        val absConverted = kotlin.math.abs(converted)
        val formattedNumber = when {
            !showDecimal -> formatWithCommas(absConverted, 0)
            absConverted >= 1_000 -> formatWithCommas(absConverted, 0)
            absConverted >= 1.0 -> roundToString(absConverted, 2)
            absConverted >= 0.01 -> roundToString(absConverted, 4)
            else -> roundToString(absConverted, 8)
        }
        return "$prefix${currentCurrency.symbol}$formattedNumber"
    }

    private fun formatWithCommas(value: Double, decimals: Int): String {
        val factor = pow10(decimals)
        val rounded = kotlin.math.round(value * factor) / factor
        val intPart = rounded.toLong()
        val sign = if (value < 0) "-" else ""
        val absInt = kotlin.math.abs(intPart)
        val str = absInt.toString().reversed().chunked(3).joinToString(",").reversed()
        val frac = if (decimals > 0) {
            val fracVal = kotlin.math.abs(rounded - intPart)
            "." + (fracVal * factor).toLong().toString().padStart(decimals, '0')
        } else ""
        return "$sign$str$frac"
    }

    private fun roundToString(value: Double, decimals: Int): String {
        val factor = pow10(decimals)
        val rounded = kotlin.math.round(value * factor) / factor
        val intPart = rounded.toLong()
        val fracPart = kotlin.math.abs(rounded - intPart)
        val fracStr = if (decimals > 0) {
            "." + (fracPart * factor).toLong().toString().padStart(decimals, '0')
        } else ""
        return "$intPart$fracStr"
    }

    private fun pow10(n: Int): Double {
        var r = 1.0; repeat(n) { r *= 10.0 }; return r
    }
}
