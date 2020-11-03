package com.test.weatherforecast.utils

import com.google.gson.JsonArray
import com.google.gson.JsonObject

object ParseUtils {

    fun getStringSafely(json: JsonObject?, name: String, defValue: String? = null): String? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return defValue

        return json.get(name).asString
    }

    fun getLongSafely(json: JsonObject?, name: String, defValue: Long? = null): Long? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return defValue

        return json.get(name).asLong
    }

    fun getDoubleSafely(json: JsonObject?, name: String, defValue: Double? = null): Double? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return defValue

        return json.get(name).asDouble
    }

    fun getIntSafely(json: JsonObject?, name: String, defValue: Int? = null): Int? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return defValue

        return json.get(name).asInt
    }

    fun getBooleanSafely(json: JsonObject?, name: String, defValue: Boolean? = null): Boolean? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return defValue

        return json.get(name).asBoolean
    }

    fun getObjectSafely(json: JsonObject?, name: String): JsonObject? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return null

        return json.get(name).asJsonObject
    }

    fun getArraySafely(json: JsonObject?, name: String): JsonArray? {
        if (json == null || json.has(name).not() || json.get(name).isJsonNull) return null

        return json.get(name).asJsonArray
    }
}