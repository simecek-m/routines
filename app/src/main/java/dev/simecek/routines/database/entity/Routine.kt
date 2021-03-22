package dev.simecek.routines.database.entity

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.simecek.routines.utils.constant.DayPhase
import dev.simecek.routines.utils.constant.Icon
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
data class Routine(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "icon") var icon: Icon,
        @ColumnInfo(name = "reminderManager") var reminder: LocalTime,
        @ColumnInfo(name = "last_finished") var lastFinished: LocalDate? = null
): Serializable {

    fun getDayPhase(): DayPhase {
        return when(reminder.hour) {
            in 0..3 -> DayPhase.NIGHT
            in 4..10 -> DayPhase.MORNING
            in 11..13 -> DayPhase.NOON
            in 14..18 -> DayPhase.AFTERNOON
            in 19..23 -> DayPhase.EVENING
            in 23..24 -> DayPhase.NIGHT
            else -> DayPhase.MORNING
        }
    }

    fun isFinished(): Boolean {
        return LocalDate.now().equals(lastFinished)
    }

    fun getDrawableIcon(context: Context): Drawable? {
        val id = context.resources.getIdentifier("ic_${icon.toString().toLowerCase(Locale.getDefault())}", "drawable", context.packageName)
        return ContextCompat.getDrawable(context, id)
    }

}
