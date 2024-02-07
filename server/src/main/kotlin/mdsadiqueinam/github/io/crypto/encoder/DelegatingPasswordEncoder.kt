package mdsadiqueinam.github.io.crypto.encoder

@Suppress("SpellCheckingInspection")
/**
 * AN encoder that delegates to another Encoder based upon a prefixed identifier.
 *
 * ## Constructing an instance
 *
 * You can construct an instance using [mdsadiqueinam.github.io.crypto.factory.EncoderFactories].
 * Alternatively, you may create your own custom instance. For example:
 *
 * ```
 * String defaultEncoderId = "bcrypt";
 * Map&lt;String,Encoder&gt; encoders = new HashMap&lt;&gt;();
 * encoders.put(defaultEncoderId, new BCryptEncoder());
 * encoders.put("noop", NoOpEncoder.getInstance());
 * encoders.put("pbkdf2", new Pbkdf2Encoder());
 * encoders.put("scrypt", new SCryptEncoder());
 * encoders.put("sha256", new StandardEncoder());
 *
 * Encoder encoder = new DelegatingEncoder(defaultEncoderId, encoders);
 * ```
 * ## Encoded Storage Format
 *
 * The general format for the encoded is:
 *
 * `{id}encoded`
 *
 * Such that "id" is an identifier used to look up which `Encoder` should be used and "encoded" is the
 * original encoded for the selected `Encoder`. The "id" must be at the beginning of the encoded, start
 * with "{" (id prefix) and end with "}" (id suffix). Both id prefix and id suffix can be customized via
 * `DelegatingEncoder`. If the "id" cannot be found, the "id" will be null.
 *
 * ## Encoding
 *
 * The `defaultEncoderId` passed into the constructor determines which `Encoder` will be used for encoding encodeds.
 * In the `DelegatingEncoder` we constructed above, that means that the result of encoding "encoded" would be
 * delegated to `BCryptEncoder` and be prefixed with "{bcrypt}". The end result would look like:
 *
 * `{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG`
 *
 * ## Encoded Matching
 *
 * Matching is done based upon the "id" and the mapping of the "id" to the `Encoder` provided in the constructor.
 * Our example in "Encoded Storage Format" provides a working example of how this is done. By default, the result
 * of invoking `matches` with the encoded with an "id" that is not mapped (including a null id) will result in an
 * `IllegalArgumentException`. This behavior can be customized using `setDefaultEncoderForMatches`.
 */
class DelegatingEncoder(
    private val defaultEncoderId: String,
    private val encoderMap: Map<String, Encoder>,
    private val idPrefix: String = DEFAULT_ID_PREFIX,
    private val idSuffix: String = DEFAULT_ID_SUFFIX
) : Encoder {
    var defaultEncoderForMatches: Encoder = UnmappedIdEncoder()

    init {
        require(idSuffix.isNotEmpty()) { "suffix cannot be empty" }
        require(!idPrefix.contains(idSuffix)) { "idPrefix $idPrefix cannot contain idSuffix $idSuffix" }
        require(encoderMap.containsKey(defaultEncoderId)) { "defaultEncoderId " + defaultEncoderId + "is not found in encoderMap " + encoderMap }
        for (id in encoderMap.keys) {
            require(!(idPrefix.isNotEmpty() && id.contains(idPrefix))) { "id $id cannot contain $idPrefix" }
            require(!id.contains(idSuffix)) { "id $id cannot contain $idSuffix" }
        }
    }

    override fun encode(raw: CharSequence): String {
        return encode(raw, defaultEncoderId)
    }

    fun encode(raw: CharSequence, encoderId: String): String {
        val delegate: Encoder = encoderMap[encoderId]
            ?: throw IllegalArgumentException("There is no Encoder mapped for the id \"$encoderId\"")
        return idPrefix + encoderId + idSuffix + delegate.encode(raw)
    }

    override fun matches(raw: CharSequence, encoded: String): Boolean {
        val id = extractId(encoded)
        val delegate: Encoder = encoderMap[id]
            ?: return defaultEncoderForMatches.matches(raw, encoded)
        val extractedEncoded = extractEncoded(encoded)
        return delegate.matches(raw, extractedEncoded)
    }

    private fun extractId(prefixEncoded: String): String? {
        val start = prefixEncoded.indexOf(this.idPrefix)
        if (start != 0) {
            return null
        }
        val end = prefixEncoded.indexOf(this.idSuffix, start)
        if (end < 0) {
            return null
        }
        return prefixEncoded.substring(start + idPrefix.length, end)
    }

    override fun upgradeEncoding(encoded: String): Boolean {
        val id = extractId(encoded)
        if (!defaultEncoderId.equals(id, ignoreCase = true)) {
            return true
        } else {
            val extractedEncoded = extractEncoded(encoded)
            return encoderMap[id]?.upgradeEncoding(extractedEncoded) ?: true
        }
    }

    private fun extractEncoded(prefixEncoded: String): String {
        val start = prefixEncoded.indexOf(this.idSuffix)
        return prefixEncoded.substring(start + idSuffix.length)
    }

    /**
     * Default [Encoder] that throws an exception telling that a suitable
     * [Encoder] for the id could not be found.
     */
    private inner class UnmappedIdEncoder : Encoder {
        override fun encode(raw: CharSequence): String {
            throw UnsupportedOperationException("encode is not supported")
        }

        override fun matches(raw: CharSequence, encoded: String): Boolean {
            val id = extractId(encoded)
            throw IllegalArgumentException("There is no Encoder mapped for the id \"$id\"")
        }
    }

    companion object {
        private const val DEFAULT_ID_PREFIX = "{"

        private const val DEFAULT_ID_SUFFIX = "}"
    }
}