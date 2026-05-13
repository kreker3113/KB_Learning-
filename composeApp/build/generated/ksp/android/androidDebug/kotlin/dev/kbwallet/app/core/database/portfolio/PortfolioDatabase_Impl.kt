package dev.kbwallet.app.core.database.portfolio

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import dev.kbwallet.app.history.`data`.TransactionDao
import dev.kbwallet.app.history.`data`.TransactionDao_Impl
import dev.kbwallet.app.portfolio.`data`.local.PortfolioDao
import dev.kbwallet.app.portfolio.`data`.local.PortfolioDao_Impl
import dev.kbwallet.app.portfolio.`data`.local.UserBalanceDao
import dev.kbwallet.app.portfolio.`data`.local.UserBalanceDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PortfolioDatabase_Impl : PortfolioDatabase() {
  private val _portfolioDao: Lazy<PortfolioDao> = lazy {
    PortfolioDao_Impl(this)
  }


  private val _userBalanceDao: Lazy<UserBalanceDao> = lazy {
    UserBalanceDao_Impl(this)
  }


  private val _transactionDao: Lazy<TransactionDao> = lazy {
    TransactionDao_Impl(this)
  }


  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(4,
        "9f008f23344d18bb838b55899dca1ff1", "4a661aa16b89fd88a631c5a7f8ed81c2") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `PortfolioCoinEntity` (`coinId` TEXT NOT NULL, `name` TEXT NOT NULL, `symbol` TEXT NOT NULL, `iconUrl` TEXT NOT NULL, `averagePurchasePrice` REAL NOT NULL, `amountOwned` REAL NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`coinId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `UserBalanceEntity` (`id` INTEGER NOT NULL, `cashBalance` REAL NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `TransactionEntity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `coinId` TEXT NOT NULL, `coinName` TEXT NOT NULL, `coinSymbol` TEXT NOT NULL, `type` TEXT NOT NULL, `amountInFiat` REAL NOT NULL, `amountInUnit` REAL NOT NULL, `pricePerUnit` REAL NOT NULL, `timestamp` INTEGER NOT NULL, `status` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9f008f23344d18bb838b55899dca1ff1')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `PortfolioCoinEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `UserBalanceEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `TransactionEntity`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsPortfolioCoinEntity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPortfolioCoinEntity.put("coinId", TableInfo.Column("coinId", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPortfolioCoinEntity.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPortfolioCoinEntity.put("symbol", TableInfo.Column("symbol", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPortfolioCoinEntity.put("iconUrl", TableInfo.Column("iconUrl", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPortfolioCoinEntity.put("averagePurchasePrice",
            TableInfo.Column("averagePurchasePrice", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPortfolioCoinEntity.put("amountOwned", TableInfo.Column("amountOwned", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPortfolioCoinEntity.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPortfolioCoinEntity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPortfolioCoinEntity: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPortfolioCoinEntity: TableInfo = TableInfo("PortfolioCoinEntity",
            _columnsPortfolioCoinEntity, _foreignKeysPortfolioCoinEntity,
            _indicesPortfolioCoinEntity)
        val _existingPortfolioCoinEntity: TableInfo = read(connection, "PortfolioCoinEntity")
        if (!_infoPortfolioCoinEntity.equals(_existingPortfolioCoinEntity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |PortfolioCoinEntity(dev.kbwallet.app.portfolio.data.local.PortfolioCoinEntity).
              | Expected:
              |""".trimMargin() + _infoPortfolioCoinEntity + """
              |
              | Found:
              |""".trimMargin() + _existingPortfolioCoinEntity)
        }
        val _columnsUserBalanceEntity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUserBalanceEntity.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBalanceEntity.put("cashBalance", TableInfo.Column("cashBalance", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUserBalanceEntity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUserBalanceEntity: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUserBalanceEntity: TableInfo = TableInfo("UserBalanceEntity",
            _columnsUserBalanceEntity, _foreignKeysUserBalanceEntity, _indicesUserBalanceEntity)
        val _existingUserBalanceEntity: TableInfo = read(connection, "UserBalanceEntity")
        if (!_infoUserBalanceEntity.equals(_existingUserBalanceEntity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |UserBalanceEntity(dev.kbwallet.app.portfolio.data.local.UserBalanceEntity).
              | Expected:
              |""".trimMargin() + _infoUserBalanceEntity + """
              |
              | Found:
              |""".trimMargin() + _existingUserBalanceEntity)
        }
        val _columnsTransactionEntity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTransactionEntity.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("coinId", TableInfo.Column("coinId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("coinName", TableInfo.Column("coinName", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("coinSymbol", TableInfo.Column("coinSymbol", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("amountInFiat", TableInfo.Column("amountInFiat", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("amountInUnit", TableInfo.Column("amountInUnit", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("pricePerUnit", TableInfo.Column("pricePerUnit", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTransactionEntity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTransactionEntity: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTransactionEntity: TableInfo = TableInfo("TransactionEntity",
            _columnsTransactionEntity, _foreignKeysTransactionEntity, _indicesTransactionEntity)
        val _existingTransactionEntity: TableInfo = read(connection, "TransactionEntity")
        if (!_infoTransactionEntity.equals(_existingTransactionEntity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |TransactionEntity(dev.kbwallet.app.history.data.TransactionEntity).
              | Expected:
              |""".trimMargin() + _infoTransactionEntity + """
              |
              | Found:
              |""".trimMargin() + _existingTransactionEntity)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "PortfolioCoinEntity",
        "UserBalanceEntity", "TransactionEntity")
  }

  public override fun clearAllTables() {
    super.performClear(false, "PortfolioCoinEntity", "UserBalanceEntity", "TransactionEntity")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PortfolioDao::class, PortfolioDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UserBalanceDao::class, UserBalanceDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TransactionDao::class, TransactionDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun portfolioDao(): PortfolioDao = _portfolioDao.value

  public override fun userBalanceDao(): UserBalanceDao = _userBalanceDao.value

  public override fun transactionDao(): TransactionDao = _transactionDao.value
}
