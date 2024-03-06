package mdsadiqueinam.github.io.extensions

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex()
    return matches(emailRegex)
}

fun String.isNotValidEmail(): Boolean {
    return !isValidEmail()
}

fun String.toUUID(): java.util.UUID {
    return java.util.UUID.fromString(this)
}