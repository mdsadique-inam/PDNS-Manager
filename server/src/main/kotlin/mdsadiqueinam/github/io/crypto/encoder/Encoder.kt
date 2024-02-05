package mdsadiqueinam.github.io.crypto.encoder

/**
 * The `Encoder` interface defines the contract for classes that implement password encoding functionality.
 * This interface provides methods for encoding and verifying passwords, as well as upgrading encryption for better security.
 */
interface Encoder {
    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     */
    fun encode(rawPassword: CharSequence): String

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean

    /**
     * Returns true if the encoded password should be encoded again for better security,
     * else false. The default implementation always returns false.
     * @param encodedPassword the encoded password to check
     * @return true if the encoded password should be encoded again for better security,
     * else false.
     */
    fun upgradeEncoding(encodedPassword: String): Boolean {
        return false
    }
}