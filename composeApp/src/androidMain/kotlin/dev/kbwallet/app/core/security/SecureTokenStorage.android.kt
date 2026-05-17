package dev.kbwallet.app.core.security

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Android implementation using in-memory storage.
 * In production, use EncryptedSharedPreferences from AndroidX Security.
 */
actual class SecureTokenStorage {
    private val mutex = Mutex()
    private val store = mutableMapOf<String, String>()

    actual suspend fun save(key: String, value: String) = mutex.withLock {
        store[key] = value
    }

    actual suspend fun get(key: String): String? = mutex.withLock {
        store[key]
    }

    actual suspend fun remove(key: String) { mutex.withLock { store.remove(key) } }

    actual suspend fun clear() = mutex.withLock {
        store.clear()
    }
}
