package mdsadiqueinam.github.io.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.io.File

object FileSerializer : KSerializer<File> {
    override val descriptor = PrimitiveSerialDescriptor("File", STRING)

    override fun deserialize(decoder: Decoder): File {
        println("FileSerializer.deserialize: ${decoder.decodeString()}")
        return File(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: File) {
        println("FileSerializer.serialize: $value")
        encoder.encodeString(value.toString())
    }
}

typealias SFile =
    @Serializable(FileSerializer::class)
    File
