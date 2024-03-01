package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import mdsadiqueinam.github.io.customPlugins.requestValidation.RequestValidation
import mdsadiqueinam.github.io.customPlugins.requestValidation.ValidatedField
import mdsadiqueinam.github.io.customPlugins.requestValidation.ValidationResult
import models.RegisterBody


fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<RegisterBody> {
            val fields = mutableListOf<ValidatedField>()

            it.name.apply {
                val fieldError = ValidatedField(it::name.name, mutableListOf())
                if (isEmpty()) {
                    fieldError.errors.plus("Name cannot be empty")
                }
                if (fieldError.errors.isNotEmpty()) {
                    fields.plus(fieldError)
                }
            }

            if (fields.isNotEmpty()) {
                ValidationResult.Invalid(fields)
            }

            ValidationResult.Valid
        }
    }
}
