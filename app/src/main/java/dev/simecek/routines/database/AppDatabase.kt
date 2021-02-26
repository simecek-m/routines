package dev.simecek.routines.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.simecek.routines.database.converter.IconPickerConverter
import dev.simecek.routines.database.converter.LocalDateConverter
import dev.simecek.routines.database.converter.ReminderConverter
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.entity.Routine

@Database(entities = [Routine::class], version = 1, exportSchema = false)
@TypeConverters(IconPickerConverter::class, ReminderConverter::class, LocalDateConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun routineDao(): RoutineDao

    companion object {
        const val NAME: String = "Routine-DB"
    }
}
