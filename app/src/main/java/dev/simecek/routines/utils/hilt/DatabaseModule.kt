package dev.simecek.routines.utils.hilt

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.simecek.routines.database.AppDatabase
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.dao.UserDao
import dev.simecek.routines.database.migration.MIGRATION_1_2
import dev.simecek.routines.database.migration.MIGRATION_2_3
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideRoutineDao(appDatabase: AppDatabase): RoutineDao {
        return appDatabase.routineDao()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

}