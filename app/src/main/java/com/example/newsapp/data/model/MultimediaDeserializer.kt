package com.example.newsapp.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MultimediaDeserializer : JsonDeserializer<List<Multimedia>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<Multimedia> {
        return if (json.isJsonArray) {
            context.deserialize(json.asJsonArray, typeOfT)
        } else if (json.isJsonObject) {
            listOf(context.deserialize(json.asJsonObject, Multimedia::class.java))
        } else {
            emptyList()
        }
    }
}