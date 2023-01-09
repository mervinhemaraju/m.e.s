package com.th3pl4gu3.mes.models

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object MesAppSettingsSerializer : Serializer<MesAppSettings> {

    override val defaultValue: MesAppSettings
        get() = MesAppSettings()

    override suspend fun readFrom(input: InputStream): MesAppSettings {
        return try {
            Json.decodeFromString(
                deserializer = MesAppSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: MesAppSettings, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = MesAppSettings.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }

}