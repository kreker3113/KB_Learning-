package dev.kbwallet.server.security

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.util.Base64

class PasswordHasher(
    private val iterations: Int = 10000,
    private val keyLength: Int = 256
) {
    private val algorithm = "PBKDF2WithHmacSHA256"
    private val saltLength = 16

    fun hash(password: String): String {
        val salt = ByteArray(saltLength)
        SecureRandom().nextBytes(salt)

        val spec = PBEKeySpec(password.toCharArray(), salt, iterations, keyLength)
        val factory = SecretKeyFactory.getInstance(algorithm)
        val hash = factory.generateSecret(spec).encoded

        val saltBase64 = Base64.getEncoder().encodeToString(salt)
        val hashBase64 = Base64.getEncoder().encodeToString(hash)

        return "$iterations:$saltBase64:$hashBase64"
    }

    fun verify(password: String, storedHash: String): Boolean {
        val parts = storedHash.split(":")
        if (parts.size != 3) return false

        val storedIterations = parts[0].toIntOrNull() ?: return false
        val salt = Base64.getDecoder().decode(parts[1])
        val storedHashBytes = Base64.getDecoder().decode(parts[2])

        val spec = PBEKeySpec(password.toCharArray(), salt, storedIterations, keyLength)
        val factory = SecretKeyFactory.getInstance(algorithm)
        val computedHash = factory.generateSecret(spec).encoded

        return computedHash.contentEquals(storedHashBytes)
    }
}
