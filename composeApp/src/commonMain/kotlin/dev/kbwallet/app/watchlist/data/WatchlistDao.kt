package dev.kbwallet.app.watchlist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Insert
    suspend fun addToWatchlist(entity: WatchlistEntity)

    @Query("DELETE FROM WatchlistEntity WHERE coinId = :coinId")
    suspend fun removeFromWatchlist(coinId: String)

    @Query("SELECT * FROM WatchlistEntity ORDER BY addedAt DESC")
    fun getAllWatchlistItems(): Flow<List<WatchlistEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM WatchlistEntity WHERE coinId = :coinId)")
    suspend fun isInWatchlist(coinId: String): Boolean
}
