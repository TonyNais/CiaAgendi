package com.maina.ciaAgendi.data.db

import android.arch.persistence.room.TypeConverter
import java.util.*

class DataTypeConverters {

    @TypeConverter
    fun dateToLong(date: Date): Long = date.time

    @TypeConverter
    fun longToDate(time: Long): Date {
        return Calendar.getInstance().apply { timeInMillis = time }.time
    }
}