package mdsadiqueinam.github.io.extensions

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any> Map<String, Any>.toObject(): T {
    val clazz = T::class
    val constructor = clazz.constructors.elementAt(1)
    val args = constructor.parameters.associateWith { parameter ->
        val paramName = parameter.name
        val paramType = parameter.type.classifier as KClass<*>
        val value = this[paramName]

        if (paramType.isInstance(value)) {
            // When the parameter type and value type match, associate the value directly
            value
        } else {
            // Deserialize the value using kotlinx.serialization
            val serializedValue = this[paramName].toString()
            val serializer = paramType.serializer() as KSerializer<*>
            Json.decodeFromString(serializer, serializedValue)
        }
    }
    return constructor.callBy(args)
}
