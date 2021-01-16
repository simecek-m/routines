package dev.simecek.routines.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.simecek.routines.converter.IconPickerConverter
import dev.simecek.routines.dao.RoutineDao
import dev.simecek.routines.model.Routine

@Database(entities = [Routine::class], version = 1)
@TypeConverters(IconPickerConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun routineDao(): RoutineDao
}
