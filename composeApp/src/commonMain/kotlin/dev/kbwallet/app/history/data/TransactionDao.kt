package dev.kbwallet.app.history.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM TransactionEntity ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT COUNT(*) FROM TransactionEntity")
    suspend fun getTotalTradeCount(): Int

    @Query("SELECT COUNT(*) FROM TransactionEntity WHERE type = 'BUY'")
    suspend fun getTotalBuyCount(): Int

    @Query("SELECT COUNT(*) FROM TransactionEntity WHERE type = 'SELL'")
    suspend fun getTotalSellCount(): Int

    @Query("UPDATE TransactionEntity SET notes = :notes, tags = :tags WHERE id = :id")
    suspend fun updateNotes(id: Long, notes: String, tags: String)
}
