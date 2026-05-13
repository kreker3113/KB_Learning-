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
public class TransactionDao_Impl(
  __db: RoomDatabase,
) : TransactionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTransactionEntity: EntityInsertAdapter<TransactionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTransactionEntity = object : EntityInsertAdapter<TransactionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `TransactionEntity` (`id`,`coinId`,`coinName`,`coinSymbol`,`type`,`amountInFiat`,`amountInUnit`,`pricePerUnit`,`timestamp`,`status`,`notes`,`tags`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TransactionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.coinId)
        statement.bindText(3, entity.coinName)
        statement.bindText(4, entity.coinSymbol)
        statement.bindText(5, entity.type)
        statement.bindDouble(6, entity.amountInFiat)
        statement.bindDouble(7, entity.amountInUnit)
        statement.bindDouble(8, entity.pricePerUnit)
        statement.bindLong(9, entity.timestamp)
        statement.bindText(10, entity.status)
        statement.bindText(11, entity.notes)
        statement.bindText(12, entity.tags)
      }
    }
  }

  public override suspend fun insert(transaction: TransactionEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfTransactionEntity.insert(_connection, transaction)
  }

  public override fun getAllTransactions(): Flow<List<TransactionEntity>> {
    val _sql: String = "SELECT * FROM TransactionEntity ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("TransactionEntity")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfCoinId: Int = getColumnIndexOrThrow(_stmt, "coinId")
        val _cursorIndexOfCoinName: Int = getColumnIndexOrThrow(_stmt, "coinName")
        val _cursorIndexOfCoinSymbol: Int = getColumnIndexOrThrow(_stmt, "coinSymbol")
        val _cursorIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _cursorIndexOfAmountInFiat: Int = getColumnIndexOrThrow(_stmt, "amountInFiat")
        val _cursorIndexOfAmountInUnit: Int = getColumnIndexOrThrow(_stmt, "amountInUnit")
        val _cursorIndexOfPricePerUnit: Int = getColumnIndexOrThrow(_stmt, "pricePerUnit")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _cursorIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _cursorIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _cursorIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _result: MutableList<TransactionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TransactionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpCoinId: String
          _tmpCoinId = _stmt.getText(_cursorIndexOfCoinId)
          val _tmpCoinName: String
          _tmpCoinName = _stmt.getText(_cursorIndexOfCoinName)
          val _tmpCoinSymbol: String
          _tmpCoinSymbol = _stmt.getText(_cursorIndexOfCoinSymbol)
          val _tmpType: String
          _tmpType = _stmt.getText(_cursorIndexOfType)
          val _tmpAmountInFiat: Double
          _tmpAmountInFiat = _stmt.getDouble(_cursorIndexOfAmountInFiat)
          val _tmpAmountInUnit: Double
          _tmpAmountInUnit = _stmt.getDouble(_cursorIndexOfAmountInUnit)
          val _tmpPricePerUnit: Double
          _tmpPricePerUnit = _stmt.getDouble(_cursorIndexOfPricePerUnit)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_cursorIndexOfStatus)
          val _tmpNotes: String
          _tmpNotes = _stmt.getText(_cursorIndexOfNotes)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_cursorIndexOfTags)
          _item =
              TransactionEntity(_tmpId,_tmpCoinId,_tmpCoinName,_tmpCoinSymbol,_tmpType,_tmpAmountInFiat,_tmpAmountInUnit,_tmpPricePerUnit,_tmpTimestamp,_tmpStatus,_tmpNotes,_tmpTags)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalTradeCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM TransactionEntity"
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

  public override suspend fun getTotalBuyCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM TransactionEntity WHERE type = 'BUY'"
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

  public override suspend fun getTotalSellCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM TransactionEntity WHERE type = 'SELL'"
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

  public override suspend fun updateNotes(
    id: Long,
    notes: String,
    tags: String,
  ) {
    val _sql: String = "UPDATE TransactionEntity SET notes = ?, tags = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, notes)
        _argIndex = 2
        _stmt.bindText(_argIndex, tags)
        _argIndex = 3
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
