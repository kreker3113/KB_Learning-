package dev.kbwallet.app.watchlist.`data`

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class WatchlistDao_Impl(
  __db: RoomDatabase,
) : WatchlistDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWatchlistEntity: EntityInsertAdapter<WatchlistEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfWatchlistEntity = object : EntityInsertAdapter<WatchlistEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `WatchlistEntity` (`coinId`,`coinName`,`coinSymbol`,`iconUrl`,`addedPrice`,`addedAt`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WatchlistEntity) {
        statement.bindText(1, entity.coinId)
        statement.bindText(2, entity.coinName)
        statement.bindText(3, entity.coinSymbol)
        statement.bindText(4, entity.iconUrl)
        statement.bindDouble(5, entity.addedPrice)
        statement.bindLong(6, entity.addedAt)
      }
    }
  }

  public override suspend fun addToWatchlist(entity: WatchlistEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfWatchlistEntity.insert(_connection, entity)
  }

  public override fun getAllWatchlistItems(): Flow<List<WatchlistEntity>> {
    val _sql: String = "SELECT * FROM WatchlistEntity ORDER BY addedAt DESC"
    return createFlow(__db, false, arrayOf("WatchlistEntity")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfCoinId: Int = getColumnIndexOrThrow(_stmt, "coinId")
        val _cursorIndexOfCoinName: Int = getColumnIndexOrThrow(_stmt, "coinName")
        val _cursorIndexOfCoinSymbol: Int = getColumnIndexOrThrow(_stmt, "coinSymbol")
        val _cursorIndexOfIconUrl: Int = getColumnIndexOrThrow(_stmt, "iconUrl")
        val _cursorIndexOfAddedPrice: Int = getColumnIndexOrThrow(_stmt, "addedPrice")
        val _cursorIndexOfAddedAt: Int = getColumnIndexOrThrow(_stmt, "addedAt")
        val _result: MutableList<WatchlistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WatchlistEntity
          val _tmpCoinId: String
          _tmpCoinId = _stmt.getText(_cursorIndexOfCoinId)
          val _tmpCoinName: String
          _tmpCoinName = _stmt.getText(_cursorIndexOfCoinName)
          val _tmpCoinSymbol: String
          _tmpCoinSymbol = _stmt.getText(_cursorIndexOfCoinSymbol)
          val _tmpIconUrl: String
          _tmpIconUrl = _stmt.getText(_cursorIndexOfIconUrl)
          val _tmpAddedPrice: Double
          _tmpAddedPrice = _stmt.getDouble(_cursorIndexOfAddedPrice)
          val _tmpAddedAt: Long
          _tmpAddedAt = _stmt.getLong(_cursorIndexOfAddedAt)
          _item =
              WatchlistEntity(_tmpCoinId,_tmpCoinName,_tmpCoinSymbol,_tmpIconUrl,_tmpAddedPrice,_tmpAddedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun isInWatchlist(coinId: String): Boolean {
    val _sql: String = "SELECT EXISTS(SELECT 1 FROM WatchlistEntity WHERE coinId = ?)"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, coinId)
        val _result: Boolean
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp != 0
        } else {
          _result = false
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun removeFromWatchlist(coinId: String) {
    val _sql: String = "DELETE FROM WatchlistEntity WHERE coinId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, coinId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
