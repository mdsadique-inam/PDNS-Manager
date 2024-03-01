package mdsadiqueinam.github.io.customPlugins.requestValidation

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.Serializable
import models.RegisterBody

@Serializable
data class ValidatedField(
    val field: String,
    val errors: List<String>
) {
    override fun toString(): String {
        return "$field has following errors ${errors.joinToString { ", " }}"
    }
}

/**
 * A result of validation.
 */
sealed class ValidationResult {
    /**
     * A successful result of validation.
     */
    data object Valid : ValidationResult()

    /**
     * An unsuccessful result of validation. All errors are stored in the [reasons] list.
     */
    class Invalid(
        /**
         * List of errors.
         */
        val fields: List<ValidatedField>
    ) : ValidationResult() {
        constructor (field: ValidatedField) : this(listOf(field))
    }
}

/**
 * A validator that should be registered with [RequestValidation] plugin
 */
interface Validator {
    /**
     * Validates the [value].
     */
    suspend fun validate(value: Any, ctx: PipelineContext<Any, ApplicationCall>): ValidationResult

    /**
     * Checks if the [value] should be checked by this validator.
     */
    fun filter(value: Any): Boolean
}

/**
 * A plugin that checks a request body using [Validator].
 * Example:
 * ```
 * install(RequestValidation) {
 *     validate<String> {
 *         if (!it.startsWith("+")) ValidationResult.Invalid("$it should start with \"+\"")
 *         else ValidationResult.Valid
 *     }
 * }
 * install(StatusPages) {
 *     exception<RequestValidationException> { call, cause ->
 *         call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
 *     }
 * }
 * ```
 */
val RequestValidation: RouteScopedPlugin<RequestValidationConfig> = createRouteScopedPlugin(
    "RequestValidation",
    ::RequestValidationConfig
) {

    val validators = pluginConfig.validators

    on(RequestBodyTransformed) { content ->
        val failures = validators.filter { it.filter(content) }
            .map { it.validate(content, this) }
            .filterIsInstance<ValidationResult.Invalid>()
        if (failures.isNotEmpty()) {
            throw RequestValidationException(content, failures.flatMap { it.fields })
        }
    }

    if (!pluginConfig.validateContentLength) return@createRouteScopedPlugin

    on(ReceiveRequestBytes) { call, body ->
        val contentLength = call.request.contentLength() ?: return@on body

        return@on application.writer {
            val count = body.copyTo(channel)
            if (count != contentLength) {
                throw IOException("Content length mismatch. Actual $count, expected $contentLength.")
            }
        }.channel
    }
}

/**
 * Thrown when validation fails.
 * @property value - invalid request body
 * @property reasons - combined reasons of all validation failures for this request
 */
class RequestValidationException(
    val value: Any,
    val fields: List<ValidatedField>
) : IllegalArgumentException("Validation failed for $value. Reasons: ${fields.joinToString(". \n")}")

private object RequestBodyTransformed : Hook<suspend PipelineContext<Any, ApplicationCall>.(content: Any) -> Unit> {
    override fun install(
        pipeline: ApplicationCallPipeline,
        handler: suspend PipelineContext<Any, ApplicationCall>.(content: Any) -> Unit
    ) {
        pipeline.receivePipeline.intercept(ApplicationReceivePipeline.After) {
            handler(subject)
        }
    }
}
