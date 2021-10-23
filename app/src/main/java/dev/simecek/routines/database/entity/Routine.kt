package dev.simecek.routines.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import dev.simecek.routines.utils.constant.DayPhase
import dev.simecek.routines.utils.constant.Icon
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Entity(foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = ["name"],
        childColumns = ["owner_name"],
        onDelete = CASCADE)])
data class Routine(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "icon") var icon: Icon,
    @ColumnInfo(name = "reminder") var reminder: LocalTime,
    @ColumnInfo(name = "owner_name") var ownerName: String,
    @ColumnInfo(name = "last_finished") var lastFinished: LocalDate? = null,
    @ColumnInfo(name = "soft_deleted") var softDeleted: Boolean = false
    ): Serializable {

    companion object {
        const val ICON_PREFIX = "ic_"
    }

    fun getDayPhase(): DayPhase {
        return when(reminder.hour) {
            in 0..3 -> DayPhase.NIGHT
            in 4..10 -> DayPhase.MORNING
            in 11..13 -> DayPhase.NOON
            in 14..17 -> DayPhase.AFTERNOON
            in 18..22 -> DayPhase.EVENING
            in 23..24 -> DayPhase.NIGHT
            else -> DayPhase.MORNING
        }
    }

    fun isFinished(): Boolean {
        return LocalDate.now().equals(lastFinished)
    }

}
