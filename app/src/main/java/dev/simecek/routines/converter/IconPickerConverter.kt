package dev.simecek.routines.converter

import androidx.room.TypeConverter
import dev.simecek.routines.constant.IconPickerSelectedType

class IconPickerConverter {

    @TypeConverter
    fun iconFromInt(value: Int): IconPickerSelectedType {
        return IconPickerSelectedType.values()[value]
    }

    @TypeConverter
    fun iconToInt(icon: IconPickerSelectedType): Int {
        return icon.ordinal
    }
}