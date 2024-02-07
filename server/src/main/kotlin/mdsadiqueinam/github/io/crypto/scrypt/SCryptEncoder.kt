package mdsadiqueinam.github.io.crypto.scrypt

import mdsadiqueinam.github.io.crypto.encoder.Encoder
import mdsadiqueinam.github.io.crypto.keygen.BytesKeyGenerator
import mdsadiqueinam.github.io.crypto.keygen.KeyGenerator
import org.bouncycastle.crypto.generators.SCrypt
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

@Suppress("SpellCheckingInspection")
/**
 * Implementation of Encoder that uses the SCrypt hashing function.
 * Clients can optionally supply a cpu cost parameter, a memory cost parameter and a parallelization
 * parameter.
 *
 * A few [warnings](http://bouncy-castle.1462172.n4.nabble.com/Java-Bouncy-Castle-scrypt-implementation-td4656832.html):
 * 1. The current implementation uses the Bouncy castle which does not exploit
 *    parallelism/optimizations that password crackers will, so there is an unnecessary
 *    asymmetry between attacker and defender.
 * 2. Scrypt is based on Salsa20, which performs poorly in Java (on par with AES) but
 *    performs awesome (~4-5x faster) on SIMD capable platforms
 * 3. While there are some that would disagree, consider reading -
 *    [Why I Don't Recommend Scrypt](https://blog.ircmaxell.com/2014/03/why-i-dont-recommend-scrypt.html)
 *    (for password storage)
 *
 * @constructor Constructs a SCrypt encoder with the provided parameters.
 * @param cpuCost The cpu cost of the algorithm (as defined in scrypt this is N) must be
 * power of 2 greater than 1. Default is currently 65,536 or 2^16)
 * @param memoryCost The memory cost of the algorithm (as defined in scrypt this is r),
 * default is currently 8.
 * @param parallelization The parallelization of the algorithm (as defined in scrypt
 * this is p), default is currently 1.
 * Note that the implementation does not currently take advantage of parallelization.
 * @param keyLength The key length for the algorithm (as defined in scrypt this is dkLen),
 * the default is currently 32.
 * @param saltLength The salt length (as defined in scrypt this is the length of S), the
 * default is currently 16.
 */
class SCryptEncoder(
    private val cpuCost: Int = DEFAULT_CPU_COST,
    private val memoryCost: Int = DEFAULT_MEMORY_COST,
    private val parallelization: Int = DEFAULT_PARALLELISM,
    private val keyLength: Int = DEFAULT_KEY_LENGTH,
    saltLength: Int = DEFAULT_SALT_LENGTH
) : Encoder {

    private val saltGenerator: BytesKeyGenerator

    init {
        require(cpuCost > 1) { "Cpu cost parameter must be > 1." }
        require(cpuCost < 65536) { "Cpu cost parameter must be < 65536." }
        require(memoryCost >= 1) { "Memory cost must be >= 1." }

        val maxParallel = Int.MAX_VALUE / (128 * memoryCost * 8)
        require(parallelization >= 1) { "Parallelization parameter must be >= 1." }
        require(parallelization <= maxParallel) {
            "Parallelization parameter must be <= $maxParallel"
        }
        require(keyLength >= 1) { "Key length must be >= 1." }
        require(saltLength >= 1) { "Salt length must be >= 1." }
        this.saltGenerator = KeyGenerator.secureRandom(saltLength)
    }

    override fun encode(raw: CharSequence): String {
        return digest(raw, saltGenerator.generateKey())
    }

    override fun matches(raw: CharSequence, encoded: String): Boolean {
        if (encoded.length < this.keyLength) {
            return false
        }
        return decodeAndCheckMatches(raw, encoded)
    }

    override fun upgradeEncoding(encoded: String): Boolean {
        val parts = encoded.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (parts.size != 4) {
            throw IllegalArgumentException("Encoded string does not look like SCrypt: $encoded")
        }
        val params = parts[1].toLong(16)
        val cpuCost = 2.toDouble().pow(((params shr 16) and 0xffffL).toDouble()).toInt()
        val memoryCost = (params.toInt() shr 8) and 0xff
        val parallelization = params.toInt() and 0xff
        return (cpuCost < this.cpuCost) || (memoryCost < this.memoryCost) || (parallelization < this.parallelization)
    }

    private fun decodeAndCheckMatches(raw: CharSequence, encoded: String): Boolean {
        val parts = encoded.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (parts.size != 4) {
            return false
        }
        val params = parts[1].toLong(16)
        val salt = decodePart(parts[2])
        val derived = decodePart(parts[3])
        val cpuCost = 2.toDouble().pow(((params shr 16) and 0xffffL).toDouble()).toInt()
        val memoryCost = (params.toInt() shr 8) and 0xff
        val parallelization = params.toInt() and 0xff
        val generated = SCrypt.generate(
            raw.toString().toByteArray(StandardCharsets.UTF_8),
            salt,
            cpuCost,
            memoryCost,
            parallelization,
            this.keyLength
        )
        return MessageDigest.isEqual(derived, generated)
    }

    private fun digest(raw: CharSequence, salt: ByteArray): String {
        val derived = SCrypt.generate(
            raw.toString().toByteArray(StandardCharsets.UTF_8),
            salt,
            this.cpuCost,
            this.memoryCost,
            this.parallelization,
            this.keyLength
        )
        val params =
            (((ln(cpuCost.toDouble()) / ln(2.0)).toInt() shl 16) or (this.memoryCost shl 8) or this.parallelization).toString(
                    16
                )
        return StringBuilder((salt.size + derived.size) * 2).apply {
            append("$").append(params).append('$')
            append(encodePart(salt)).append('$')
            append(encodePart(derived))
        }.toString()
    }

    private fun decodePart(part: String): ByteArray {
        return Base64.getDecoder().decode(part.toByteArray(StandardCharsets.UTF_8))
    }

    private fun encodePart(part: ByteArray): String {
        return Base64.getEncoder().encode(part).toString(StandardCharsets.UTF_8)
    }

    companion object {
        private const val DEFAULT_CPU_COST = 65536

        private const val DEFAULT_MEMORY_COST = 8

        private const val DEFAULT_PARALLELISM = 1

        private const val DEFAULT_KEY_LENGTH = 32

        private const val DEFAULT_SALT_LENGTH = 16
    }
}