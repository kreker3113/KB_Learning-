package dev.kbwallet.app.history.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LimitOrderDao {

    @Insert
    suspend fun insert(order: LimitOrderEntity)

    @Query("SELECT * FROM LimitOrderEntity WHERE status = 'ACTIVE' ORDER BY createdAt DESC")
    fun getActiveOrders(): Flow<List<LimitOrderEntity>>

    @Query("SELECT * FROM LimitOrderEntity ORDER BY createdAt DESC")
    fun getAllOrders(): Flow<List<LimitOrderEntity>>

    @Query("UPDATE LimitOrderEntity SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Query("DELETE FROM LimitOrderEntity WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM LimitOrderEntity WHERE status = 'ACTIVE'")
    suspend fun getActiveOrdersList(): List<LimitOrderEntity>

    @Query("SELECT COUNT(*) FROM LimitOrderEntity WHERE status = 'ACTIVE'")
    suspend fun getActiveOrderCount(): Int
}
