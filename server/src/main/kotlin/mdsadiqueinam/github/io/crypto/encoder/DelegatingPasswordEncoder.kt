package mdsadiqueinam.github.io.crypto.encoder


/**
 * A password encoder that delegates to another Encoder based upon a prefixed
 * identifier.
 *
 * <h2>Constructing an instance</h2>
 *
 * You can easily construct an instance using
 * [org.springframework.security.crypto.factory.EncoderFactories].
 * Alternatively, you may create your own custom instance. For example:
 *
 * <pre>
 * String idForEncode = "bcrypt";
 * Map&lt;String,Encoder&gt; encoders = new HashMap&lt;&gt;();
 * encoders.put(idForEncode, new BCryptEncoder());
 * encoders.put("noop", NoOpEncoder.getInstance());
 * encoders.put("pbkdf2", new Pbkdf2Encoder());
 * encoders.put("scrypt", new SCryptEncoder());
 * encoders.put("sha256", new StandardEncoder());
 *
 * Encoder Encoder = new DelegatingEncoder(idForEncode, encoders);
</pre> *
 *
 *
 * <h2>Password Storage Format</h2>
 *
 * The general format for a password is:
 *
 * <pre>
 * {id}encodedPassword
</pre> *
 *
 * Such that "id" is an identifier used to look up which [Encoder] should be
 * used and "encodedPassword" is the original encoded password for the selected
 * [Encoder]. The "id" must be at the beginning of the password, start with
 * "{" (id prefix) and end with "}" (id suffix). Both id prefix and id suffix can be
 * customized via [.DelegatingEncoder]. If the
 * "id" cannot be found, the "id" will be null.
 *
 * For example, the following might be a list of passwords encoded using different "id".
 * All of the original passwords are "password".
 *
 * <pre>
 * {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
 * {noop}password
 * {pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc
 * {scrypt}$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc=
 * {sha256}97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0
</pre> *
 *
 * For the DelegatingEncoder that we constructed above:
 *
 *
 *  1. The first password would have a `Encoder` id of "bcrypt" and
 * encodedPassword of "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG". When
 * matching it would delegate to
 * [org.springframework.security.crypto.bcrypt.BCryptEncoder]
 *  1. The second password would have a `Encoder` id of "noop" and
 * encodedPassword of "password". When matching it would delegate to
 * [NoOpEncoder]
 *  1. The third password would have a `Encoder` id of "pbkdf2" and
 * encodedPassword of
 * "5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc".
 * When matching it would delegate to [Pbkdf2Encoder]
 *  1. The fourth password would have a `Encoder` id of "scrypt" and
 * encodedPassword of
 * "$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc="
 * When matching it would delegate to
 * [org.springframework.security.crypto.scrypt.SCryptEncoder]
 *  1. The final password would have a `Encoder` id of "sha256" and
 * encodedPassword of
 * "97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0".
 * When matching it would delegate to [StandardEncoder]
 *
 *
 * <h2>Password Encoding</h2>
 *
 * The `idForEncode` passed into the constructor determines which
 * [Encoder] will be used for encoding passwords. In the
 * `DelegatingEncoder` we constructed above, that means that the result of
 * encoding "password" would be delegated to `BCryptEncoder` and be prefixed
 * with "{bcrypt}". The end result would look like:
 *
 * <pre>
 * {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
</pre> *
 *
 * <h2>Password Matching</h2>
 *
 * Matching is done based upon the "id" and the mapping of the "id" to the
 * [Encoder] provided in the constructor. Our example in "Password Storage
 * Format" provides a working example of how this is done.
 *
 * By default the result of invoking [.matches] with a
 * password with an "id" that is not mapped (including a null id) will result in an
 * [IllegalArgumentException]. This behavior can be customized using
 * [.setDefaultEncoderForMatches].
 */
class DelegatingEncoder(
    private val defaultEncoderId: String,
    private val encoderMap: Map<String, Encoder>,
    private val idPrefix: String = DEFAULT_ID_PREFIX,
    private val idSuffix: String = DEFAULT_ID_SUFFIX
) : Encoder {
    private val defaultEncoder: Encoder

    private var defaultEncoderForMatches: Encoder = UnmappedIdEncoder()

    /**
     * Creates a new instance
     * @param idForEncode the id used to lookup which [Encoder] should be
     * used for [.encode]
     * @param idToEncoder a Map of id to [Encoder] used to determine
     * which [Encoder] should be used for
     * @param idPrefix the prefix that denotes the start of the id in the encoded results
     * @param idSuffix the suffix that denotes the end of an id in the encoded results
     * [.matches]
     */
    init {
        require(idSuffix.isNotEmpty()) { "suffix cannot be empty" }
        require(!idPrefix.contains(idSuffix)) { "idPrefix $idPrefix cannot contain idSuffix $idSuffix" }

        require(encoderMap.containsKey(defaultEncoderId)) { "idForEncode " + defaultEncoderId + "is not found in idToEncoder " + encoderMap }
        for (id in encoderMap.keys) {
            require(!(idPrefix.isNotEmpty() && id.contains(idPrefix))) { "id $id cannot contain $idPrefix" }
            require(!id.contains(idSuffix)) { "id $id cannot contain $idSuffix" }
        }
        this.defaultEncoder = encoderMap[defaultEncoderId]
            ?: throw IllegalArgumentException("defaultEncoderId $defaultEncoderId is not found in encoderMap $encoderMap")
    }

    override fun encode(rawPassword: CharSequence): String {
        return this.idPrefix + this.defaultEncoderId + this.idSuffix + defaultEncoder.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence, prefixEncodedPassword: String): Boolean {
        val id = extractId(prefixEncodedPassword)
        val delegate: Encoder = encoderMap[id]
            ?: return defaultEncoderForMatches.matches(rawPassword, prefixEncodedPassword)
        val encodedPassword = extractEncodedPassword(prefixEncodedPassword)
        return delegate.matches(rawPassword, encodedPassword)
    }

    private fun extractId(prefixEncodedPassword: String): String? {
        val start = prefixEncodedPassword.indexOf(this.idPrefix)
        if (start != 0) {
            return null
        }
        val end = prefixEncodedPassword.indexOf(this.idSuffix, start)
        if (end < 0) {
            return null
        }
        return prefixEncodedPassword.substring(start + idPrefix.length, end)
    }

    override fun upgradeEncoding(prefixEncodedPassword: String): Boolean {
        val id = extractId(prefixEncodedPassword)
        if (!defaultEncoderId.equals(id, ignoreCase = true)) {
            return true
        } else {
            val encodedPassword = extractEncodedPassword(prefixEncodedPassword)
            return encoderMap[id]?.upgradeEncoding(encodedPassword) ?: true
        }
    }

    private fun extractEncodedPassword(prefixEncodedPassword: String): String {
        val start = prefixEncodedPassword.indexOf(this.idSuffix)
        return prefixEncodedPassword.substring(start + idSuffix.length)
    }

    /**
     * Default [Encoder] that throws an exception telling that a suitable
     * [Encoder] for the id could not be found.
     */
    private inner class UnmappedIdEncoder : Encoder {
        override fun encode(rawPassword: CharSequence): String {
            throw UnsupportedOperationException("encode is not supported")
        }

        override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
            val id = extractId(encodedPassword)
            throw IllegalArgumentException("There is no Encoder mapped for the id \"$id\"")
        }
    }

    companion object {
        private const val DEFAULT_ID_PREFIX = "{"

        private const val DEFAULT_ID_SUFFIX = "}"
    }
}