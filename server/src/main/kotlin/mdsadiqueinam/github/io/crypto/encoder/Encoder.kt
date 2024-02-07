package mdsadiqueinam.github.io.crypto.encoder

/**
 * The `Encoder` interface defines the contract for classes that implement encoding functionality.
 * This interface provides methods for encoding and verifying s, as well as upgrading encryption for better security.
 */
interface Encoder {
    /**
     * Encode the raw.
     * Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     */
    fun encode(raw: CharSequence): String

    /**
     * Verify the encoded obtained from storage matches the submitted raw after it too is encoded.
     * Returns true if the s match, false if they do not.
     * The stored itself is never decoded.
     * @param raw the raw to encode and match
     * @param encoded the encoded from storage to compare with
     * @return true if the raw, after encoding, matches the encoded  from
     * storage
     */
    fun matches(raw: CharSequence, encoded: String): Boolean

    /**
     * Returns true if the encoded should be encoded again for better security,
     * else false. The default implementation always returns false.
     * @param encoded the encoded to check
     * @return true if the encoded should be encoded again for better security,
     * else false.
     */
    fun upgradeEncoding(encoded: String): Boolean {
        return false
    }
}