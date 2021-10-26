package dev.simecek.routines.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.simecek.routines.database.converter.IconPickerConverter
import dev.simecek.routines.database.converter.LocalDateConverter
import dev.simecek.routines.database.converter.ReminderConverter
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.dao.UserDao
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.database.entity.User

@Database(entities = [Routine::class, User::class], version = 3)
@TypeConverters(IconPickerConverter::class, ReminderConverter::class, LocalDateConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun userDao(): UserDao

    companion object {
        const val NAME: String = "Routines-DB"
    }

}
