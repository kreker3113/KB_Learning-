package dev.kbwallet.app.simulator.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.kbwallet.app.chart.presentation.component.*
import dev.kbwallet.app.chart.presentation.util.ChartTransform
import dev.kbwallet.app.core.domain.currency.Currency
import dev.kbwallet.app.core.domain.currency.CurrencySwitcher
import dev.kbwallet.app.core.domain.currency.CurrencySelectorDialog
import dev.kbwallet.app.simulator.domain.*
import dev.kbwallet.app.theme.DarkLossRedColor
import dev.kbwallet.app.theme.DarkProfitGreenColor
import org.koin.compose.viewmodel.koinViewModel

private val SubtextGray = Color(0xFFAAAAAA)
private val IndicatorToggleBg = Color(0xFF1E1E2E)

@Composable
fun SimulatorScreen(
    onBack: () -> Unit,
    viewModel: SimulatorViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.loadCoins() }

    // Currency dialog
    if (state.showCurrencyDialog) {
        CurrencySelectorDialog(
            current = state.displayCurrency,
            onSelect = { viewModel.selectCurrency(it) },
            onDismiss = { viewModel.hideCurrencyDialog() },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        // ── Header ──
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.width(8.dp))
            Text("Trading Simulator", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))

            // Currency switcher
            CurrencySwitcher(
                current = state.displayCurrency,
                onSelect = { viewModel.toggleCurrency() },
            )

            if (state.activeHint != null) {
                IconButton(onClick = { viewModel.nextHint() }) {
                    Icon(Icons.Default.Info, "Hint", tint = Color(0xFFFFA500))
                }
            }
        }

        if (state.isLoading && state.availableCoins.isEmpty()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // ── Coin Selection ──
            if (state.selectedCoin == null) {
                item { Text("Select a coin to simulate:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold) }
                items(state.availableCoins) { coin ->
                    FilterChip(
                        selected = false,
                        onClick = { viewModel.selectCoin(coin) },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(coin.iconUrl, null, contentScale = ContentScale.Fit, modifier = Modifier.size(20.dp).clip(CircleShape))
                                Spacer(Modifier.width(6.dp))
                                Text("${coin.name} (${coin.symbol.uppercase()})", fontSize = 13.sp)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            if (state.selectedCoin != null && state.candles.isNotEmpty()) {
                val coin = state.selectedCoin!!
                val candle = state.candles.getOrNull(state.currentCandleIndex)

                // ── Coin info + Price ──
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(coin.iconUrl, null, contentScale = ContentScale.Fit, modifier = Modifier.size(28.dp).clip(CircleShape))
                        Spacer(Modifier.width(8.dp))
                        Text("${coin.name} (${coin.symbol.uppercase()})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.weight(1f))
                        if (candle != null) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    viewModel.formatAmount(candle.close),
                                    fontSize = 16.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Text(
                                    "Candle ${state.currentCandleIndex + 1}/${state.candles.size}",
                                    fontSize = 10.sp, color = SubtextGray,
                                )
                            }
                        }
                    }
                }

                // ── Playback Controls ──
                item {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.stepBackward() }) { Icon(Icons.Default.SkipPrevious, "Prev") }
                            IconButton(onClick = { viewModel.togglePlay() }) {
                                Icon(if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, if (state.isPlaying) "Pause" else "Play")
                            }
                            IconButton(onClick = { viewModel.stepForward() }) { Icon(Icons.Default.SkipNext, "Next") }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            PlaySpeed.entries.forEach { speed ->
                                FilterChip(
                                    selected = state.playSpeed == speed,
                                    onClick = { viewModel.setPlaySpeed(speed) },
                                    label = { Text(speed.label, fontSize = 10.sp) },
                                )
                            }
                        }
                    }
                }

                // ── Indicator Toggles ──
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        IndicatorChip("Vol", state.showVolume, viewModel::toggleVolume)
                        IndicatorChip("SMA", state.showSma, viewModel::toggleSma)
                        IndicatorChip("EMA", state.showEma, viewModel::toggleEma)
                        IndicatorChip("BB", state.showBollinger, viewModel::toggleBollinger)
                        IndicatorChip("RSI", state.showRsi, viewModel::toggleRsi)
                        IndicatorChip("MACD", state.showMacd, viewModel::toggleMacd)
                    }
                }

                // ── Chart ──
                item {
                    val transform = remember(state.candles, state.currentCandleIndex) {
                        val visibleRange = if (state.candles.size > 0) {
                            val window = 60.coerceAtMost(state.candles.size)
                            val start = (state.currentCandleIndex - window / 2).coerceIn(0, state.candles.size - window)
                            val end = start + window
                            start.toFloat() / state.candles.size to end.toFloat() / state.candles.size
                        } else 0f to 1f
                        ChartTransform(state.candles, visibleRange.first, visibleRange.second)
                    }

                    val extraHeight = (if (state.showVolume) 40 else 0) + (if (state.showRsi) 70 else 0) + (if (state.showMacd) 65 else 0)
                    val chartAreaH = 200 + extraHeight

                    Box(modifier = Modifier.fillMaxWidth().height(chartAreaH.dp)) {
                        // Main price pane
                        val pricePaneHeight = 200f
                        Box(modifier = Modifier.fillMaxWidth().height(pricePaneHeight.dp)) {
                            ChartGrid(transform = transform)
                            CandlestickChart(transform = transform, bullColor = DarkProfitGreenColor, bearColor = DarkLossRedColor)

                            // SMA overlay
                            if (state.showSma) {
                                SMAOverlay(transform = transform, smaValues = state.sma20.map { it }, color = Color(0xFFFFEB3B))
                                SMAOverlay(transform = transform, smaValues = state.sma50.map { it }, color = Color(0xFFFF9800))
                            }
                            // EMA overlay
                            if (state.showEma) {
                                SMAOverlay(transform = transform, smaValues = state.ema12.map { it }, color = Color(0xFF64B5F6), chartHeightFraction = 0.85f)
                                SMAOverlay(transform = transform, smaValues = state.ema26.map { it }, color = Color(0xFFE57373), chartHeightFraction = 0.85f)
                            }
                            // Bollinger Bands
                            if (state.showBollinger && state.bollinger != null) {
                                val bb = state.bollinger!!
                                BollingerOverlay(
                                    middle = bb.middle, upper = bb.upper, lower = bb.lower,
                                    transform = transform,
                                )
                            }
                        }

                        // Volume pane
                        if (state.showVolume) {
                            Box(modifier = Modifier.fillMaxWidth().height(40.dp).offset(y = pricePaneHeight.dp)) {
                                VolumeBars(transform = transform)
                            }
                        }

                        // RSI pane
                        if (state.showRsi) {
                            val rsiOffset = pricePaneHeight.dp + if (state.showVolume) 40.dp else 0.dp
                            Box(modifier = Modifier.fillMaxWidth().height(70.dp).offset(y = rsiOffset)) {
                                RsiOverlay(values = state.rsiValues, transform = transform)
                            }
                        }

                        // MACD pane
                        if (state.showMacd && state.macd != null) {
                            val macd = state.macd!!
                            val macdOffset = pricePaneHeight.dp + (if (state.showVolume) 40.dp else 0.dp) + (if (state.showRsi) 70.dp else 0.dp)
                            Box(modifier = Modifier.fillMaxWidth().height(65.dp).offset(y = macdOffset)) {
                                MacdOverlay(
                                    macdLine = macd.macdLine,
                                    signalLine = macd.signalLine,
                                    histogram = macd.histogram,
                                    transform = transform,
                                )
                            }
                        }
                    }
                }

                // ── Balance / Equity ──
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SimStatCard("Balance", viewModel.formatAmount(state.cashBalance), Modifier.weight(1f))
                        SimStatCard("Equity", viewModel.formatAmount(state.equity), Modifier.weight(1f), DarkProfitGreenColor)
                        val pnl = state.equity - state.initialBalance
                        SimStatCard("P&L", viewModel.formatAmountSigned(pnl), Modifier.weight(1f), if (pnl >= 0) DarkProfitGreenColor else DarkLossRedColor)
                    }
                }

                // ── Order Form ──
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("New Position", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OrderSideInput.entries.forEach { side ->
                                    FilterChip(
                                        selected = state.orderSide == side,
                                        onClick = { viewModel.onOrderSideChanged(side) },
                                        label = { Text(side.label) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = if (side == OrderSideInput.LONG) DarkProfitGreenColor.copy(alpha = 0.2f) else DarkLossRedColor.copy(alpha = 0.2f),
                                            selectedLabelColor = if (side == OrderSideInput.LONG) DarkProfitGreenColor else DarkLossRedColor,
                                        ),
                                    )
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Amount: ${state.displayCurrency.symbol}", modifier = Modifier.width(70.dp), color = SubtextGray, fontSize = 13.sp)
                                BasicTextField(
                                    value = state.orderAmount,
                                    onValueChange = { viewModel.onOrderAmountChanged(it) },
                                    modifier = Modifier.weight(1f).height(40.dp).background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp),
                                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("SL: ${state.displayCurrency.symbol}", modifier = Modifier.width(70.dp), color = SubtextGray, fontSize = 12.sp)
                                BasicTextField(
                                    value = state.orderStopLoss,
                                    onValueChange = { viewModel.onOrderSLChanged(it) },
                                    modifier = Modifier.weight(1f).height(36.dp).background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp),
                                    textStyle = TextStyle(color = DarkLossRedColor, fontSize = 13.sp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("TP: ${state.displayCurrency.symbol}", modifier = Modifier.width(70.dp), color = SubtextGray, fontSize = 12.sp)
                                BasicTextField(
                                    value = state.orderTakeProfit,
                                    onValueChange = { viewModel.onOrderTPChanged(it) },
                                    modifier = Modifier.weight(1f).height(36.dp).background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp),
                                    textStyle = TextStyle(color = DarkProfitGreenColor, fontSize = 13.sp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Leverage:", modifier = Modifier.width(70.dp), color = SubtextGray, fontSize = 12.sp)
                                BasicTextField(
                                    value = state.orderLeverage,
                                    onValueChange = { viewModel.onOrderLeverageChanged(it) },
                                    modifier = Modifier.width(60.dp).height(36.dp).background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp),
                                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 13.sp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.openPosition() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = DarkProfitGreenColor),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Text("Open ${state.orderSide.label} Position", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // ── Active Positions ──
                if (state.positions.isNotEmpty()) {
                    item { Text("Active Positions", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) }
                    items(state.positions.filter { it.isOpen }, key = { it.id }) { pos ->
                        PositionCard(
                            pos = pos,
                            currencySymbol = state.displayCurrency.symbol,
                            onClose = { viewModel.closePosition(pos.id) },
                        )
                    }
                }

                // ── Closed Trades ──
                if (state.closedTrades.isNotEmpty()) {
                    item { Text("Closed Trades", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold) }
                    items(state.closedTrades.takeLast(10).reversed(), key = { it.id }) { trade ->
                        ClosedTradeCard(trade = trade, currencySymbol = state.displayCurrency.symbol)
                    }
                }

                // ── Performance Metrics ──
                if (state.metrics.totalTrades > 0) {
                    item {
                        val m = state.metrics
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Performance", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(Modifier.height(4.dp))
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        MetricRow("Win Rate", "${"%.0f".format(m.winRate * 100)}%")
                                        MetricRow("Profit Factor", "%.2f".format(m.profitFactor))
                                        MetricRow("Sharpe", "%.2f".format(m.sharpeRatio))
                                    }
                                    Column {
                                        MetricRow("Trades", "${m.totalTrades}")
                                        MetricRow("Max DD", "${"%.1f".format(m.maxDrawdownPercent)}%")
                                        MetricRow("Best/Worst", "${"%.1f".format(m.bestTrade)} / ${"%.1f".format(m.worstTrade)}")
                                    }
                                }
                            }
                        }
                    }
                }

                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

// ── Composable helpers ──

@Composable
private fun IndicatorChip(label: String, active: Boolean, onToggle: () -> Unit) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable { onToggle() },
        color = if (active) MaterialTheme.colorScheme.primary.copy(alpha = 0.25f) else IndicatorToggleBg,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 11.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            color = if (active) MaterialTheme.colorScheme.primary else SubtextGray,
        )
    }
}

@Composable
private fun SimStatCard(label: String, value: String, modifier: Modifier = Modifier, valueColor: Color = MaterialTheme.colorScheme.onBackground) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp)).padding(10.dp)) {
        Text(label, fontSize = 11.sp, color = SubtextGray)
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = valueColor, fontFamily = FontFamily.Monospace, maxLines = 1)
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row {
        Text("$label: ", fontSize = 11.sp, color = SubtextGray)
        Text(value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun PositionCard(pos: SimPosition, currencySymbol: String, onClose: () -> Unit) {
    val pnlColor = if (pos.pnl >= 0) DarkProfitGreenColor else DarkLossRedColor
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${pos.side.name} ${pos.coinSymbol.uppercase()}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Text("$currencySymbol${pos.amountInFiat.toInt()}", fontSize = 13.sp, color = SubtextGray)
            }
            Spacer(Modifier.height(4.dp))
            Row {
                Text("Entry: $currencySymbol${pos.entryPrice}", fontSize = 12.sp, color = SubtextGray)
                Spacer(Modifier.width(12.dp))
                Text("Now: $currencySymbol${pos.currentPrice}", fontSize = 12.sp, color = SubtextGray)
                Spacer(Modifier.weight(1f))
                Text("P&L: ${"%.0f".format(pos.pnl)} (${"%.1f".format(pos.pnlPercent)}%)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = pnlColor)
            }
            if (pos.stopLoss != null || pos.takeProfit != null) {
                Spacer(Modifier.height(2.dp))
                Row {
                    if (pos.stopLoss != null) Text("SL: $currencySymbol${pos.stopLoss}", fontSize = 11.sp, color = DarkLossRedColor)
                    Spacer(Modifier.width(12.dp))
                    if (pos.takeProfit != null) Text("TP: $currencySymbol${pos.takeProfit}", fontSize = 11.sp, color = DarkProfitGreenColor)
                }
            }
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = pnlColor.copy(alpha = 0.2f)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.align(Alignment.End),
            ) { Text("Close", fontSize = 12.sp, color = pnlColor) }
        }
    }
}

@Composable
private fun ClosedTradeCard(trade: ClosedTrade, currencySymbol: String) {
    val pnlColor = if (trade.pnl >= 0) DarkProfitGreenColor else DarkLossRedColor
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(if (trade.side == PositionSide.LONG) Icons.Default.TrendingUp else Icons.Default.TrendingDown, null, tint = pnlColor, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("${trade.side.name} ${trade.coinName}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text("Entry: $currencySymbol${trade.entryPrice} → Exit: $currencySymbol${trade.exitPrice}", fontSize = 11.sp, color = SubtextGray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("${"%.1f".format(trade.pnl)} $currencySymbol", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = pnlColor)
                Text("${trade.exitReason.name}", fontSize = 10.sp, color = SubtextGray)
            }
        }
    }
}
