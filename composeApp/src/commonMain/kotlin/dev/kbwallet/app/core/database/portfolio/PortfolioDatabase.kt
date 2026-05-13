package dev.kbwallet.app.core.database.portfolio

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kbwallet.app.history.data.LimitOrderDao
import dev.kbwallet.app.history.data.LimitOrderEntity
import dev.kbwallet.app.history.data.TransactionDao
import dev.kbwallet.app.history.data.TransactionEntity
import dev.kbwallet.app.portfolio.data.local.PortfolioCoinEntity
import dev.kbwallet.app.portfolio.data.local.PortfolioDao
import dev.kbwallet.app.portfolio.data.local.UserBalanceDao
import dev.kbwallet.app.portfolio.data.local.UserBalanceEntity
import dev.kbwallet.app.watchlist.data.WatchlistDao
import dev.kbwallet.app.watchlist.data.WatchlistEntity

@ConstructedBy(PortfolioDatabaseCreator::class)
@Database(
    entities = [
        PortfolioCoinEntity::class,
        UserBalanceEntity::class,
        TransactionEntity::class,
        LimitOrderEntity::class,
        WatchlistEntity::class,
    ],
    version = 7
)
abstract class PortfolioDatabase: RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
    abstract fun userBalanceDao(): UserBalanceDao
    abstract fun transactionDao(): TransactionDao
    abstract fun limitOrderDao(): LimitOrderDao
    abstract fun watchlistDao(): WatchlistDao
}
