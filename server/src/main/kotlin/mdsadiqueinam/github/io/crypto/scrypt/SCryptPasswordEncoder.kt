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


/**
 *
 *
 * Implementation of PasswordEncoder that uses the SCrypt hashing function. Clients can
 * optionally supply a cpu cost parameter, a memory cost parameter and a parallelization
 * parameter.
 *
 *
 *
 *
 * A few [
 * warnings](http://bouncy-castle.1462172.n4.nabble.com/Java-Bouncy-Castle-scrypt-implementation-td4656832.html):
 *
 *
 *
 *  * The currently implementation uses Bouncy castle which does not exploit
 * parallelism/optimizations that password crackers will, so there is an unnecessary
 * asymmetry between attacker and defender.
 *  * Scrypt is based on Salsa20 which performs poorly in Java (on par with AES) but
 * performs awesome (~4-5x faster) on SIMD capable platforms
 *  * While there are some that would disagree, consider reading -
 * [ Why I
 * Don't Recommend Scrypt](https://blog.ircmaxell.com/2014/03/why-i-dont-recommend-scrypt.html) (for password storage)
 *
 * Constructs a SCrypt password encoder with the provided parameters.
 * @param cpuCost cpu cost of the algorithm (as defined in scrypt this is N). must be
 * power of 2 greater than 1. Default is currently 65,536 or 2^16)
 * @param memoryCost memory cost of the algorithm (as defined in scrypt this is r)
 * Default is currently 8.
 * @param parallelization the parallelization of the algorithm (as defined in scrypt
 * this is p) Default is currently 1. Note that the implementation does not currently
 * take advantage of parallelization.
 * @param keyLength key length for the algorithm (as defined in scrypt this is dkLen).
 * The default is currently 32.
 * @param saltLength salt length (as defined in scrypt this is the length of S). The
 * default is currently 16.
 */
class SCryptPasswordEncoder(
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

    override fun encode(rawPassword: CharSequence): String {
        return digest(rawPassword, saltGenerator.generateKey())
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        if (encodedPassword.length < this.keyLength) {
            return false
        }
        return decodeAndCheckMatches(rawPassword, encodedPassword)
    }

    fun upgradeEncoding(encodedPassword: String?): Boolean {
        if (encodedPassword.isNullOrEmpty()) {
            return false
        }
        val parts = encodedPassword.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (parts.size != 4) {
            throw IllegalArgumentException("Encoded password does not look like SCrypt: $encodedPassword")
        }
        val params = parts[1].toLong(16)
        val cpuCost = 2.toDouble().pow(((params shr 16) and 0xffffL).toDouble()).toInt()
        val memoryCost = (params.toInt() shr 8) and 0xff
        val parallelization = params.toInt() and 0xff
        return (cpuCost < this.cpuCost) || (memoryCost < this.memoryCost) || (parallelization < this.parallelization)
    }

    private fun decodeAndCheckMatches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        val parts = encodedPassword.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
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
            rawPassword.toString().toByteArray(StandardCharsets.UTF_8),
            salt,
            cpuCost,
            memoryCost,
            parallelization,
            this.keyLength
        )
        return MessageDigest.isEqual(derived, generated)
    }

    private fun digest(rawPassword: CharSequence, salt: ByteArray): String {
        val derived = SCrypt.generate(
            rawPassword.toString().toByteArray(StandardCharsets.UTF_8),
            salt,
            this.cpuCost,
            this.memoryCost,
            this.parallelization,
            this.keyLength
        )
        val params =
            (((ln(cpuCost.toDouble()) / ln(2.0)).toInt() shl 16L.toInt()) or (this.memoryCost shl 8) or this.parallelization).toString(
                16
            )
        val sb = StringBuilder((salt.size + derived.size) * 2)
        sb.append("$").append(params).append('$')
        sb.append(encodePart(salt)).append('$')
        sb.append(encodePart(derived))
        return sb.toString()
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