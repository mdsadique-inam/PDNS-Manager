package mdsadiqueinam.github.io.crypto.encoder

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Create a new Digester.
 * @param algorithm the digest algorithm: for example, "SHA-1" or "SHA-256".
 * @param iterations the number of times to apply the digest algorithm to the input
 */
internal class Digester(private val algorithm: String, var iterations: UInt = 0u) {

    init {
        // eagerly validate the algorithm
        createDigest(algorithm)
    }

    fun digest(value: ByteArray): ByteArray {
        var digested = value
        val messageDigest = createDigest(this.algorithm)
        for (i in 0 until iterations.toInt()) {
            digested = messageDigest.digest(digested)
        }
        return digested
    }

    companion object {
        private fun createDigest(algorithm: String): MessageDigest {
            try {
                return MessageDigest.getInstance(algorithm)
            } catch (ex: NoSuchAlgorithmException) {
                throw IllegalStateException("No such hashing algorithm", ex)
            }
        }
    }
}