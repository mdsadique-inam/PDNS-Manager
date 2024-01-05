package mdsadiqueinam.github.io.extensions

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import java.io.File
import java.util.UUID


suspend inline fun <reified T : Any> ApplicationCall.receiveMultipart(): T {
    val valueMap = mutableMapOf<String, Any>()
    val multipart: MultiPartData = receive()
    multipart.forEachPart { part ->
        part.name?.let { name ->
            val value: Any? = when (part) {
                is PartData.FormItem -> part.value
                is PartData.FileItem -> {
                    val fileName = part.originalFileName as String
                    val extension = fileName.substringAfterLast('.', "")
                    val fileBytes = part.streamProvider().readBytes()
                    File("src/main/resources/tmp/uploads/${UUID.randomUUID()}.$extension")
                        .apply { writeBytes(fileBytes) }
                }
                else -> null
            }
            value?.let {
                if (valueMap.containsKey(name)) {
                    val existingValue = valueMap[name]
                    if (existingValue is List<*>) {
                        valueMap[name] = existingValue + value
                    } else {
                        valueMap[name] = mutableListOf(existingValue, value)
                    }
                } else {
                    valueMap[name] = value
                }
            }
        }
        part.dispose()
    }

    return valueMap.toObject<T>()
}
