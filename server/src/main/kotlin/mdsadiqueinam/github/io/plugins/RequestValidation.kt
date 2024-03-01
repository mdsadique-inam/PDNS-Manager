package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import mdsadiqueinam.github.io.customPlugins.requestValidation.RequestValidation
import mdsadiqueinam.github.io.customPlugins.requestValidation.ValidatedField
import mdsadiqueinam.github.io.customPlugins.requestValidation.ValidationResult
import models.RegisterBody


fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<RegisterBody> { _, it ->
            val fields = mutableListOf<ValidatedField>()

            it.name.apply {
                val fieldErrors = mutableListOf<String>()
                if (isEmpty()) {
                    fieldErrors.add("Name cannot be empty")
                }
                if (fieldErrors.isNotEmpty()) {
                    fields.add(ValidatedField(it::name.name, fieldErrors))
                }
            }

            if (fields.isNotEmpty()) {
                return@validate ValidationResult.Invalid(fields)
            }

            ValidationResult.Valid
        }
    }
}
