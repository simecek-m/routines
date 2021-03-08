package dev.simecek.routines.database.converter

import androidx.room.TypeConverter
import dev.simecek.routines.constant.Icon

class IconPickerConverter {

    @TypeConverter
    fun iconFromInt(value: Int): Icon {
        return Icon.values()[value]
    }

    @TypeConverter
    fun iconToInt(icon: Icon): Int {
        return icon.ordinal
    }
}