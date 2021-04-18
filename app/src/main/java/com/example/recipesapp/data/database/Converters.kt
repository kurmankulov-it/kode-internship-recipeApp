package com.example.recipesapp.data.database

import androidx.room.TypeConverter
import com.example.recipesapp.data.database.entities.RecipeBrief
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromImagesList(value: List<String>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toImagesList(value: String?): List<String>? {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBriefList(value: List<RecipeBrief>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<RecipeBrief>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toBriefList(value: String?): List<RecipeBrief>? {
        val gson = Gson()
        val type = object : TypeToken<List<RecipeBrief>>() {}.type
        return gson.fromJson(value, type)
    }
}