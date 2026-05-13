package dev.kbwallet.app.simulator.domain

/**
 * Позиция в симуляторе — Long или Short.
 */
data class SimPosition(
    val id: Long,
    val coinId: String,
    val coinName: String,
    val coinSymbol: String,
    val side: PositionSide,       // LONG or SHORT
    val entryPrice: Double,
    val currentPrice: Double,
    val amountInFiat: Double,     // размер позиции в $
    val leverage: Double = 1.0,   // кредитное плечо
    val stopLoss: Double? = null,  // цена стоп-лосса
    val takeProfit: Double? = null, // цена тейк-профита
    val entryTime: Int,            // индекс свечи входа
    val isOpen: Boolean = true,
    val pnl: Double = 0.0,         // текущий P&L
    val pnlPercent: Double = 0.0,
)

enum class PositionSide { LONG, SHORT }

/**
 * Закрытая сделка (запись в истории).
 */
data class ClosedTrade(
    val id: Long,
    val coinId: String,
    val coinName: String,
    val side: PositionSide,
    val entryPrice: Double,
    val exitPrice: Double,
    val amountInFiat: Double,
    val pnl: Double,
    val pnlPercent: Double,
    val entryTime: Int,
    val exitTime: Int,
    val exitReason: ExitReason,
)

enum class ExitReason { MANUAL, STOP_LOSS, TAKE_PROFIT, LIQUIDATION, END_OF_DATA }

/**
 * Агрегированные метрики симуляции.
 */
data class SimulatorMetrics(
    val totalPnl: Double = 0.0,
    val totalPnlPercent: Double = 0.0,
    val winRate: Double = 0.0,         // 0..1
    val profitFactor: Double = 0.0,    // gross profit / gross loss
    val maxDrawdown: Double = 0.0,     // максимальная просадка в $
    val maxDrawdownPercent: Double = 0.0,
    val sharpeRatio: Double = 0.0,     // упрощённый (mean / stddev)
    val totalTrades: Int = 0,
    val winningTrades: Int = 0,
    val losingTrades: Int = 0,
    val avgWin: Double = 0.0,
    val avgLoss: Double = 0.0,
    val bestTrade: Double = 0.0,
    val worstTrade: Double = 0.0,
)
