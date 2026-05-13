package dev.kbwallet.app.history.`data`

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
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
public class LimitOrderDao_Impl(
  __db: RoomDatabase,
) : LimitOrderDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLimitOrderEntity: EntityInsertAdapter<LimitOrderEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLimitOrderEntity = object : EntityInsertAdapter<LimitOrderEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `LimitOrderEntity` (`id`,`coinId`,`coinName`,`coinSymbol`,`iconUrl`,`type`,`side`,`targetPrice`,`amountInFiat`,`amountInUnit`,`status`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LimitOrderEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.coinId)
        statement.bindText(3, entity.coinName)
        statement.bindText(4, entity.coinSymbol)
        statement.bindText(5, entity.iconUrl)
        statement.bindText(6, entity.type)
        statement.bindText(7, entity.side)
        statement.bindDouble(8, entity.targetPrice)
        statement.bindDouble(9, entity.amountInFiat)
        statement.bindDouble(10, entity.amountInUnit)
        statement.bindText(11, entity.status)
        statement.bindLong(12, entity.createdAt)
      }
    }
  }

  public override suspend fun insert(order: LimitOrderEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfLimitOrderEntity.insert(_connection, order)
  }

  public override fun getActiveOrders(): Flow<List<LimitOrderEntity>> {
    val _sql: String =
        "SELECT * FROM LimitOrderEntity WHERE status = 'ACTIVE' ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("LimitOrderEntity")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfCoinId: Int = getColumnIndexOrThrow(_stmt, "coinId")
        val _cursorIndexOfCoinName: Int = getColumnIndexOrThrow(_stmt, "coinName")
        val _cursorIndexOfCoinSymbol: Int = getColumnIndexOrThrow(_stmt, "coinSymbol")
        val _cursorIndexOfIconUrl: Int = getColumnIndexOrThrow(_stmt, "iconUrl")
        val _cursorIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _cursorIndexOfSide: Int = getColumnIndexOrThrow(_stmt, "side")
        val _cursorIndexOfTargetPrice: Int = getColumnIndexOrThrow(_stmt, "targetPrice")
        val _cursorIndexOfAmountInFiat: Int = getColumnIndexOrThrow(_stmt, "amountInFiat")
        val _cursorIndexOfAmountInUnit: Int = getColumnIndexOrThrow(_stmt, "amountInUnit")
        val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<LimitOrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LimitOrderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpCoinId: String
          _tmpCoinId = _stmt.getText(_cursorIndexOfCoinId)
          val _tmpCoinName: String
          _tmpCoinName = _stmt.getText(_cursorIndexOfCoinName)
          val _tmpCoinSymbol: String
          _tmpCoinSymbol = _stmt.getText(_cursorIndexOfCoinSymbol)
          val _tmpIconUrl: String
          _tmpIconUrl = _stmt.getText(_cursorIndexOfIconUrl)
          val _tmpType: String
          _tmpType = _stmt.getText(_cursorIndexOfType)
          val _tmpSide: String
          _tmpSide = _stmt.getText(_cursorIndexOfSide)
          val _tmpTargetPrice: Double
          _tmpTargetPrice = _stmt.getDouble(_cursorIndexOfTargetPrice)
          val _tmpAmountInFiat: Double
          _tmpAmountInFiat = _stmt.getDouble(_cursorIndexOfAmountInFiat)
          val _tmpAmountInUnit: Double
          _tmpAmountInUnit = _stmt.getDouble(_cursorIndexOfAmountInUnit)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_cursorIndexOfStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_cursorIndexOfCreatedAt)
          _item =
              LimitOrderEntity(_tmpId,_tmpCoinId,_tmpCoinName,_tmpCoinSymbol,_tmpIconUrl,_tmpType,_tmpSide,_tmpTargetPrice,_tmpAmountInFiat,_tmpAmountInUnit,_tmpStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllOrders(): Flow<List<LimitOrderEntity>> {
    val _sql: String = "SELECT * FROM LimitOrderEntity ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("LimitOrderEntity")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfCoinId: Int = getColumnIndexOrThrow(_stmt, "coinId")
        val _cursorIndexOfCoinName: Int = getColumnIndexOrThrow(_stmt, "coinName")
        val _cursorIndexOfCoinSymbol: Int = getColumnIndexOrThrow(_stmt, "coinSymbol")
        val _cursorIndexOfIconUrl: Int = getColumnIndexOrThrow(_stmt, "iconUrl")
        val _cursorIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _cursorIndexOfSide: Int = getColumnIndexOrThrow(_stmt, "side")
        val _cursorIndexOfTargetPrice: Int = getColumnIndexOrThrow(_stmt, "targetPrice")
        val _cursorIndexOfAmountInFiat: Int = getColumnIndexOrThrow(_stmt, "amountInFiat")
        val _cursorIndexOfAmountInUnit: Int = getColumnIndexOrThrow(_stmt, "amountInUnit")
        val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<LimitOrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LimitOrderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpCoinId: String
          _tmpCoinId = _stmt.getText(_cursorIndexOfCoinId)
          val _tmpCoinName: String
          _tmpCoinName = _stmt.getText(_cursorIndexOfCoinName)
          val _tmpCoinSymbol: String
          _tmpCoinSymbol = _stmt.getText(_cursorIndexOfCoinSymbol)
          val _tmpIconUrl: String
          _tmpIconUrl = _stmt.getText(_cursorIndexOfIconUrl)
          val _tmpType: String
          _tmpType = _stmt.getText(_cursorIndexOfType)
          val _tmpSide: String
          _tmpSide = _stmt.getText(_cursorIndexOfSide)
          val _tmpTargetPrice: Double
          _tmpTargetPrice = _stmt.getDouble(_cursorIndexOfTargetPrice)
          val _tmpAmountInFiat: Double
          _tmpAmountInFiat = _stmt.getDouble(_cursorIndexOfAmountInFiat)
          val _tmpAmountInUnit: Double
          _tmpAmountInUnit = _stmt.getDouble(_cursorIndexOfAmountInUnit)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_cursorIndexOfStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_cursorIndexOfCreatedAt)
          _item =
              LimitOrderEntity(_tmpId,_tmpCoinId,_tmpCoinName,_tmpCoinSymbol,_tmpIconUrl,_tmpType,_tmpSide,_tmpTargetPrice,_tmpAmountInFiat,_tmpAmountInUnit,_tmpStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getActiveOrdersList(): List<LimitOrderEntity> {
    val _sql: String = "SELECT * FROM LimitOrderEntity WHERE status = 'ACTIVE'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfCoinId: Int = getColumnIndexOrThrow(_stmt, "coinId")
        val _cursorIndexOfCoinName: Int = getColumnIndexOrThrow(_stmt, "coinName")
        val _cursorIndexOfCoinSymbol: Int = getColumnIndexOrThrow(_stmt, "coinSymbol")
        val _cursorIndexOfIconUrl: Int = getColumnIndexOrThrow(_stmt, "iconUrl")
        val _cursorIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _cursorIndexOfSide: Int = getColumnIndexOrThrow(_stmt, "side")
        val _cursorIndexOfTargetPrice: Int = getColumnIndexOrThrow(_stmt, "targetPrice")
        val _cursorIndexOfAmountInFiat: Int = getColumnIndexOrThrow(_stmt, "amountInFiat")
        val _cursorIndexOfAmountInUnit: Int = getColumnIndexOrThrow(_stmt, "amountInUnit")
        val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _cursorIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<LimitOrderEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LimitOrderEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpCoinId: String
          _tmpCoinId = _stmt.getText(_cursorIndexOfCoinId)
          val _tmpCoinName: String
          _tmpCoinName = _stmt.getText(_cursorIndexOfCoinName)
          val _tmpCoinSymbol: String
          _tmpCoinSymbol = _stmt.getText(_cursorIndexOfCoinSymbol)
          val _tmpIconUrl: String
          _tmpIconUrl = _stmt.getText(_cursorIndexOfIconUrl)
          val _tmpType: String
          _tmpType = _stmt.getText(_cursorIndexOfType)
          val _tmpSide: String
          _tmpSide = _stmt.getText(_cursorIndexOfSide)
          val _tmpTargetPrice: Double
          _tmpTargetPrice = _stmt.getDouble(_cursorIndexOfTargetPrice)
          val _tmpAmountInFiat: Double
          _tmpAmountInFiat = _stmt.getDouble(_cursorIndexOfAmountInFiat)
          val _tmpAmountInUnit: Double
          _tmpAmountInUnit = _stmt.getDouble(_cursorIndexOfAmountInUnit)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_cursorIndexOfStatus)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_cursorIndexOfCreatedAt)
          _item =
              LimitOrderEntity(_tmpId,_tmpCoinId,_tmpCoinName,_tmpCoinSymbol,_tmpIconUrl,_tmpType,_tmpSide,_tmpTargetPrice,_tmpAmountInFiat,_tmpAmountInUnit,_tmpStatus,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getActiveOrderCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM LimitOrderEntity WHERE status = 'ACTIVE'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateStatus(id: Long, status: String) {
    val _sql: String = "UPDATE LimitOrderEntity SET status = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(id: Long) {
    val _sql: String = "DELETE FROM LimitOrderEntity WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
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
