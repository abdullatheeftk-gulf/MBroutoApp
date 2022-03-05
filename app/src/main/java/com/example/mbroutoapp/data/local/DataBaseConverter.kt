package com.example.mbroutoapp.data.local

import androidx.room.TypeConverter

class DataBaseConverter {
    private val separator = ""

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        val stringBuilder = StringBuilder()
        list.forEach { item ->
            stringBuilder.append(item).append(separator)
        }
        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
            return string.split(separator)
    }
}