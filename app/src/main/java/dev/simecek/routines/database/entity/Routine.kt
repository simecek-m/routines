package dev.simecek.routines.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.simecek.routines.constant.DayPhase
import dev.simecek.routines.constant.IconPickerSelectedType
import dev.simecek.routines.database.type.Reminder
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Routine(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "icon") var icon: IconPickerSelectedType,
        @ColumnInfo(name = "reminder") var reminder: Reminder,
        @ColumnInfo(name = "last_day_finished") var lastDayFinished: String? = null
): Serializable {

    companion object {
        const val DATE_PATTERN = "yyyy-MM-dd"
        private val sdf = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
    }

    fun getDayPhase(): DayPhase {
        return when(reminder.hour) {
            in 0..11 -> DayPhase.MORNING
            in 12..16 -> DayPhase.DAY
            in 17..20 -> DayPhase.EVENING
            in 20..24 -> DayPhase.NIGHT
            else -> DayPhase.MORNING
        }
    }

    fun isFinished(): Boolean {
        val today: String = sdf.format(Calendar.getInstance().time)
        return lastDayFinished.equals(today)
    }
}
