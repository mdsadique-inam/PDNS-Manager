package extensions

import exceptions.PDNSClientException
import io.ktor.client.call.*
import io.ktor.client.statement.*
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
            Result.failure(PDNSClientException(body))
        }

        else -> {
            Result.failure(Throwable("Unknown error"))
        }
    }
}