package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import kotlin.reflect.*

/**
 * A config for [RequestValidation] plugin
 */
class RequestValidationConfig {

    internal val validators: MutableList<Validator> = mutableListOf()

    internal var validateContentLength: Boolean = false

    /**
     * Enables validation of the request body length matches the [Content-Length] header.
     * If the length doesn't match, body channel will be cancelled with [IOException].
     */
    fun validateContentLength() {
        validateContentLength = true
    }

    /**
     * Registers [validator]
     */
    fun validate(validator: Validator) {
        validators.add(validator)
    }

    /**
     * Registers [Validator] that should check instances of a [kClass] using [block]
     */
    fun <T : Any> validate(kClass: KClass<T>, block: suspend ApplicationCall.(T) -> ValidationResult) {
        val validator = object : Validator {
            @Suppress("UNCHECKED_CAST")
            override suspend fun validate(value: Any, call: ApplicationCall): ValidationResult {
                var result: ValidationResult
                call.apply {
                    result = block(value as T)
                }
                return result
            }
            override fun filter(value: Any): Boolean = kClass.isInstance(value)
        }
        validate(validator)
    }

    /**
     * Registers [Validator] that should check instances of a [T] using [block]
     */
    inline fun <reified T : Any> validate(noinline block: suspend ApplicationCall.(T) -> ValidationResult) {
        validate(T::class, block)
    }

    /**
     * Registers [Validator] using DSL
     * ```
     * validate {
     *    filter { it is Int }
     *    validation { check(it is Int); ... }
     * }
     * ```
     */
    fun validate(block: ValidatorBuilder.() -> Unit) {
        val builder = ValidatorBuilder().apply(block)
        validate(builder.build())
    }

    class ValidatorBuilder {
        private lateinit var validationBlock: suspend (Any, ApplicationCall) -> ValidationResult
        private lateinit var filterBlock: (Any) -> Boolean

        fun filter(block: (Any) -> Boolean) {
            filterBlock = block
        }

        fun validation(block: suspend (Any, ApplicationCall) -> ValidationResult) {
            validationBlock = block
        }

        internal fun build(): Validator {
            check(::validationBlock.isInitialized) { "`validation { ... } block is not set`" }
            check(::filterBlock.isInitialized) { "`filter { ... } block is not set`" }
            return object : Validator {
                override suspend fun validate(value: Any, call: ApplicationCall) = validationBlock(value, call)
                override fun filter(value: Any): Boolean = filterBlock(value)
            }
        }
    }
}