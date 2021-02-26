package dev.simecek.routines.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {

    companion object {
        private const val DATE_FORMATTER_PATTERN = "yyyy-MM-dd"
        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        return if(date == null) null else dateFormatter.format(date)
    }

    @TypeConverter
    fun stringToDate(formattedDate: String?): LocalDate? {
        return if(formattedDate == null) null else LocalDate.parse(formattedDate)
    }

}