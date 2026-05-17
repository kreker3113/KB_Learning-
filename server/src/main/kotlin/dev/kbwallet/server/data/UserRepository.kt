package dev.kbwallet.server.data

import dev.kbwallet.server.models.User
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository {
    private val users = mutableMapOf<String, User>()
    private val emailIndex = mutableMapOf<String, String>() // email -> userId
    private val mutex = Mutex()

    suspend fun findByEmail(email: String): User? = mutex.withLock {
        val userId = emailIndex[email.lowercase()] ?: return null
        users[userId]
    }

    suspend fun findById(id: String): User? = mutex.withLock {
        users[id]
    }

    suspend fun create(user: User): User = mutex.withLock {
        require(emailIndex[user.email.lowercase()] == null) {
            "User with email ${user.email} already exists"
        }
        users[user.id] = user
        emailIndex[user.email.lowercase()] = user.id
        user
    }

    suspend fun update(user: User): User = mutex.withLock {
        val existing = users[user.id] ?: throw NoSuchElementException("User not found: ${user.id}")
        // If email changed, update the index
        if (existing.email != user.email) {
            emailIndex.remove(existing.email.lowercase())
            emailIndex[user.email.lowercase()] = user.id
        }
        users[user.id] = user
        user
    }

    suspend fun delete(id: String): Boolean = mutex.withLock {
        val user = users.remove(id) ?: return false
        emailIndex.remove(user.email.lowercase())
        true
    }

    suspend fun count(): Int = mutex.withLock {
        users.size
    }
}
