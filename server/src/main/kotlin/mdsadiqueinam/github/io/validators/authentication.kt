package mdsadiqueinam.github.io.validators

import mdsadiqueinam.github.io.customPlugins.requestValidation.RequestValidationConfig
import mdsadiqueinam.github.io.customPlugins.requestValidation.ValidationResult
import mdsadiqueinam.github.io.extensions.isNotValidEmail
import models.RegisterBody
import models.ValidatedField

fun RequestValidationConfig.validateAuthentication() {
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

        it.username.apply {
            val fieldErrors = mutableListOf<String>()
            if (isEmpty()) {
                fieldErrors.add("Username cannot be empty")
            }
            if (fieldErrors.isNotEmpty()) {
                fields.add(ValidatedField(it::username.name, fieldErrors))
            }
        }

        it.email.apply {
            val fieldErrors = mutableListOf<String>()
            if (isEmpty()) {
                fieldErrors.add("Email cannot be empty")
            }

            if (isNotValidEmail()) {
                fieldErrors.add("Email is not valid")
            }

            if (fieldErrors.isNotEmpty()) {
                fields.add(ValidatedField(it::email.name, fieldErrors))
            }
        }

        it.password.apply {
            val fieldErrors = mutableListOf<String>()
            if (isEmpty()) {
                fieldErrors.add("Password cannot be empty")
            }
            if (fieldErrors.isNotEmpty()) {
                fields.add(ValidatedField(it::password.name, fieldErrors))
            }
        }

        ValidationResult.result(fields)
    }
}