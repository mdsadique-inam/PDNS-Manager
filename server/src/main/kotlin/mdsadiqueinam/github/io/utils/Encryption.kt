package mdsadiqueinam.github.io.utils

import mdsadiqueinam.github.io.extensions.toHex
import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


class Encryption private constructor(private val appKey: String) {
    enum class Algorithm(val value: String) {
        SHA1("SHA-1"),
        SHA224("SHA-224"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512"),
        ARGON2("argon2"),
        BCRYPT("bcrypt"),
        SCRYPT("scrypt")
    }

    companion object {
        private const val ITERATIONS = 2
        private const val MEM_LIMIT = 66536
        private const val HASH_LENGTH = 32
        private const val PARALLELISM = 1

        private lateinit var _instance: Encryption
        val instance: Encryption
            get() {
                if (!::_instance.isInitialized) {
                    throw UninitializedPropertyAccessException("Encryption")
                }
                return _instance
            }
        fun encryption(appKey: String): Encryption {
            if (!::_instance.isInitialized) {
                _instance = Encryption(appKey)
            }
            return _instance
        }
    }

    private val argon2Parameters: Argon2Parameters by lazy {
        Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
            .withVersion(Argon2Parameters.ARGON2_VERSION_13)
            .withIterations(ITERATIONS)
            .withMemoryAsKB(MEM_LIMIT)
            .withParallelism(PARALLELISM)
            .withSalt(appKey.toByteArray())
            .build()
    }

    private val argon2BytesGenerator: Argon2BytesGenerator by lazy {
        Argon2BytesGenerator().apply {
            init(argon2Parameters)
        }
    }

    /**
     * Hashes the input string using the specified algorithm.
     *
     * @param input The string to be hashed.
     * @param algorithm The algorithm to be used for hashing. Default value is Algorithm.ARGON2.
     * @return The hashed string.
     */
    fun hash(input: String, algorithm: Algorithm = Algorithm.ARGON2): String {
        return when(algorithm) {
            Algorithm.ARGON2 -> hashWithArgon2(input)
            Algorithm.BCRYPT -> TODO()
            Algorithm.SCRYPT -> TODO()
            else -> hashWithSha(input, algorithm)
        }
    }

    private fun hashWithArgon2(input: String): String {
        val inputBytes = input.toByteArray(StandardCharsets.UTF_8)
        val output = ByteArray(HASH_LENGTH)
        argon2BytesGenerator.generateBytes(inputBytes, output)
        return output.toHex()
    }

    private fun hashWithSha(input: String, algorithm: Algorithm): String {
        val messageDigest = MessageDigest.getInstance(algorithm.value)
        val hashBytes = messageDigest.digest(input.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.toHex()
    }

    /**
     * Verifies the input string against the hashed string using the specified algorithm.
     *
     * @param input The string to be verified.
     * @param hash The hashed string to compare against.
     * @param algorithm The algorithm used for hashing. Default value is Algorithm.ARGON2.
     * @return True if the input string matches the hashed string, otherwise false.
     */
    fun verify(input: String, hash: String, algorithm: Algorithm = Algorithm.ARGON2): Boolean {
        return hash(input, algorithm) == hash
    }
}