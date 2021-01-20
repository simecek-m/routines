package dev.simecek.routines.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.simecek.routines.database.converter.IconPickerConverter
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.entity.Routine

@Database(entities = [Routine::class], version = 1)
@TypeConverters(IconPickerConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun routineDao(): RoutineDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
