package dev.simecek.routines.database.entity

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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

    @Ignore
    private val sdf = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

    companion object {
        const val DATE_PATTERN = "yyyy-MM-dd"
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

    fun getDrawableIcon(context: Context): Drawable? {
        val id = context.resources.getIdentifier(icon.toString().toLowerCase(Locale.getDefault()), "drawable", context.packageName)
        return ContextCompat.getDrawable(context, id)
    }

}
