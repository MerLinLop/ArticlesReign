package com.example.articlesreign._model.local.converters

import androidx.room.TypeConverter
import com.example.articlesreign._model.local.articles.HighlightResultEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HighlightResultConverter {

    @TypeConverter
    fun fromCountryLangList(countryLang: HighlightResultEntity?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<HighlightResultEntity?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toCountryLangList(countryLangString: String?): HighlightResultEntity? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<HighlightResultEntity?>() {}.type
        return gson.fromJson(countryLangString, type)
    }
}