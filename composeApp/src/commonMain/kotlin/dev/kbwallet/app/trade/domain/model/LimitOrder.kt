package dev.kbwallet.app.trade.domain.model

import kotlinx.serialization.Serializable

/**
 * Тип ордера.
 */
@Serializable
enum class OrderType { MARKET, LIMIT, STOP_LOSS, TAKE_PROFIT }

/**
 * Статус лимитного/стоп-ордера.
 */
@Serializable
enum class OrderStatus { ACTIVE, FILLED, CANCELLED }

/**
 * Лимитный / стоп-ордер.
 * Хранится в БД и проверяется симулятором рынка.
 */
data class LimitOrderModel(
    val id: Long = 0,
    val coinId: String,
    val coinName: String,
    val coinSymbol: String,
    val iconUrl: String,
    val type: OrderType,       // LIMIT, STOP_LOSS, TAKE_PROFIT
    val side: String,          // "BUY" or "SELL"
    val targetPrice: Double,   // цена исполнения
    val amountInFiat: Double,  // объём в фиате
    val amountInUnit: Double,  // объём в монетах
    val status: OrderStatus = OrderStatus.ACTIVE,
    val createdAt: Long,       // epoch millis
)
