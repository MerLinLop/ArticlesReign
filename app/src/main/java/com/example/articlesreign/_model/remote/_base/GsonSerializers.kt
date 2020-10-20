package com.example.articlesreign._model.remote._base

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.*

object GsonSerializers {
    @JvmField
    var serializerDateToTimeLong =
        JsonSerializer<Date> { src: Date?, typeOfSrc: Type?, context: JsonSerializationContext? ->
            if (src == null) null else JsonPrimitive(src.time)
        }
    var deserializerDateToTimeLong =
        JsonDeserializer { json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext? ->
            if (json == null) null else Date(json.asLong)
        }
}