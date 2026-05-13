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
import dev.kbwallet.app.history.`data`.LimitOrderDao
import dev.kbwallet.app.history.`data`.LimitOrderDao_Impl
import dev.kbwallet.app.history.`data`.TransactionDao
import dev.kbwallet.app.history.`data`.TransactionDao_Impl
import dev.kbwallet.app.portfolio.`data`.local.PortfolioDao
import dev.kbwallet.app.portfolio.`data`.local.PortfolioDao_Impl
import dev.kbwallet.app.portfolio.`data`.local.UserBalanceDao
import dev.kbwallet.app.portfolio.`data`.local.UserBalanceDao_Impl
import dev.kbwallet.app.watchlist.`data`.WatchlistDao
import dev.kbwallet.app.watchlist.`data`.WatchlistDao_Impl
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


  private val _limitOrderDao: Lazy<LimitOrderDao> = lazy {
    LimitOrderDao_Impl(this)
  }


  private val _watchlistDao: Lazy<WatchlistDao> = lazy {
    WatchlistDao_Impl(this)
  }


  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(7,
        "da4654599407371404ab0fbfce5f98f2", "1131301d53736f7175756cfb4fbbb510") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `PortfolioCoinEntity` (`coinId` TEXT NOT NULL, `name` TEXT NOT NULL, `symbol` TEXT NOT NULL, `iconUrl` TEXT NOT NULL, `averagePurchasePrice` REAL NOT NULL, `amountOwned` REAL NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`coinId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `UserBalanceEntity` (`id` INTEGER NOT NULL, `cashBalance` REAL NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `TransactionEntity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `coinId` TEXT NOT NULL, `coinName` TEXT NOT NULL, `coinSymbol` TEXT NOT NULL, `type` TEXT NOT NULL, `amountInFiat` REAL NOT NULL, `amountInUnit` REAL NOT NULL, `pricePerUnit` REAL NOT NULL, `timestamp` INTEGER NOT NULL, `status` TEXT NOT NULL, `notes` TEXT NOT NULL, `tags` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `LimitOrderEntity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `coinId` TEXT NOT NULL, `coinName` TEXT NOT NULL, `coinSymbol` TEXT NOT NULL, `iconUrl` TEXT NOT NULL, `type` TEXT NOT NULL, `side` TEXT NOT NULL, `targetPrice` REAL NOT NULL, `amountInFiat` REAL NOT NULL, `amountInUnit` REAL NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `WatchlistEntity` (`coinId` TEXT NOT NULL, `coinName` TEXT NOT NULL, `coinSymbol` TEXT NOT NULL, `iconUrl` TEXT NOT NULL, `addedPrice` REAL NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`coinId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'da4654599407371404ab0fbfce5f98f2')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `PortfolioCoinEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `UserBalanceEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `TransactionEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `LimitOrderEntity`")
        connection.execSQL("DROP TABLE IF EXISTS `WatchlistEntity`")
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
        _columnsTransactionEntity.put("notes", TableInfo.Column("notes", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTransactionEntity.put("tags", TableInfo.Column("tags", "TEXT", true, 0, null,
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
        val _columnsLimitOrderEntity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLimitOrderEntity.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("coinId", TableInfo.Column("coinId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("coinName", TableInfo.Column("coinName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("coinSymbol", TableInfo.Column("coinSymbol", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("iconUrl", TableInfo.Column("iconUrl", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("side", TableInfo.Column("side", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("targetPrice", TableInfo.Column("targetPrice", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("amountInFiat", TableInfo.Column("amountInFiat", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("amountInUnit", TableInfo.Column("amountInUnit", "REAL", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLimitOrderEntity.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLimitOrderEntity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLimitOrderEntity: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLimitOrderEntity: TableInfo = TableInfo("LimitOrderEntity",
            _columnsLimitOrderEntity, _foreignKeysLimitOrderEntity, _indicesLimitOrderEntity)
        val _existingLimitOrderEntity: TableInfo = read(connection, "LimitOrderEntity")
        if (!_infoLimitOrderEntity.equals(_existingLimitOrderEntity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |LimitOrderEntity(dev.kbwallet.app.history.data.LimitOrderEntity).
              | Expected:
              |""".trimMargin() + _infoLimitOrderEntity + """
              |
              | Found:
              |""".trimMargin() + _existingLimitOrderEntity)
        }
        val _columnsWatchlistEntity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWatchlistEntity.put("coinId", TableInfo.Column("coinId", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchlistEntity.put("coinName", TableInfo.Column("coinName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchlistEntity.put("coinSymbol", TableInfo.Column("coinSymbol", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchlistEntity.put("iconUrl", TableInfo.Column("iconUrl", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchlistEntity.put("addedPrice", TableInfo.Column("addedPrice", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchlistEntity.put("addedAt", TableInfo.Column("addedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWatchlistEntity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWatchlistEntity: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWatchlistEntity: TableInfo = TableInfo("WatchlistEntity", _columnsWatchlistEntity,
            _foreignKeysWatchlistEntity, _indicesWatchlistEntity)
        val _existingWatchlistEntity: TableInfo = read(connection, "WatchlistEntity")
        if (!_infoWatchlistEntity.equals(_existingWatchlistEntity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |WatchlistEntity(dev.kbwallet.app.watchlist.data.WatchlistEntity).
              | Expected:
              |""".trimMargin() + _infoWatchlistEntity + """
              |
              | Found:
              |""".trimMargin() + _existingWatchlistEntity)
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
        "UserBalanceEntity", "TransactionEntity", "LimitOrderEntity", "WatchlistEntity")
  }

  public override fun clearAllTables() {
    super.performClear(false, "PortfolioCoinEntity", "UserBalanceEntity", "TransactionEntity",
        "LimitOrderEntity", "WatchlistEntity")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PortfolioDao::class, PortfolioDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UserBalanceDao::class, UserBalanceDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TransactionDao::class, TransactionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LimitOrderDao::class, LimitOrderDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(WatchlistDao::class, WatchlistDao_Impl.getRequiredConverters())
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

  public override fun limitOrderDao(): LimitOrderDao = _limitOrderDao.value

  public override fun watchlistDao(): WatchlistDao = _watchlistDao.value
}
