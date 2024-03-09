package extensions

import exceptions.PDNSApiException
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.Error


suspend inline fun <reified T> HttpResponse.process(): Result<T> {
    return when (status.value) {
        in 200..299 -> {
            if (T::class == Unit::class) {
                return Result.success(Unit as T)
            }
            val body = this.body<T>()
            Result.success(body)
        }

        in intArrayOf(400, 404, 422, 500) -> {
            val body = this.body<Error>()
            Result.failure(PDNSApiException(HttpStatusCode.fromValue(status.value), body))
        }

        else -> {
            Result.failure(Throwable("Unknown error"))
        }
    }
}