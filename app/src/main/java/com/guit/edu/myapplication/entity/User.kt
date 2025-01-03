package com.guit.edu.myapplication.entity

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class User(
    val id: Int?,
    val nickname: String?,
    val signature: String?,
    val gender: String?,
    val username: String?,
    val password: String?,
    val assignment: Int?,
    val height: Int?,
    val weight: Int?,
    val cupcapacity: Int?,
    val createdAt: String?
)
class LocalDateDeserializer : JsonDeserializer<String> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
        return json.asJsonPrimitive.asString
    }
}

