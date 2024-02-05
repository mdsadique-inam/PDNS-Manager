package mdsadiqueinam.github.io.crypto.keygen

/**
 * A generator for unique string keys.
 */
interface StringKeyGenerator {
    fun generateKey(): String?
}