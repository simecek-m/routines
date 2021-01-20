package dev.simecek.routines.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.simecek.routines.constant.IconPickerSelectedType

@Entity
data class Routine(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "icon") var icon: IconPickerSelectedType,
        @ColumnInfo(name = "time") var time: String,
        @ColumnInfo(name = "last_day_finished") var lastDayFinished: String? = null
)
