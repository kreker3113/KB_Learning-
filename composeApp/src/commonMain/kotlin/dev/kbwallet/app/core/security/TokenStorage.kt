package dev.kbwallet.app.core.security

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Thread-safe in-memory token storage.
 * On Android, prefer EncryptedSharedPreferences.
 * On iOS, prefer the Keychain.
 */
class TokenStorage {
    private val mutex = Mutex()
    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var userId: String? = null

    suspend fun saveTokens(access: String, refresh: String) = mutex.withLock {
        accessToken = access
        refreshToken = refresh
    }

    suspend fun getAccessToken(): String? = mutex.withLock {
        accessToken
    }

    suspend fun getRefreshToken(): String? = mutex.withLock {
        refreshToken
    }

    suspend fun setUserId(id: String) = mutex.withLock {
        userId = id
    }

    suspend fun getUserId(): String? = mutex.withLock {
        userId
    }

    suspend fun clear() = mutex.withLock {
        accessToken = null
        refreshToken = null
        userId = null
    }

    suspend fun isLoggedIn(): Boolean = mutex.withLock {
        accessToken != null
    }
}

/**
 * Platform-specific secure storage factory.
 */
expect class SecureTokenStorage() {
    suspend fun save(key: String, value: String)
    suspend fun get(key: String): String?
    suspend fun remove(key: String)
    suspend fun clear()
}
