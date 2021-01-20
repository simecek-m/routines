package dev.simecek.routines.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.simecek.routines.database.dao.RoutineDao
import dev.simecek.routines.database.AppDatabase
import dev.simecek.routines.database.entity.Routine

class RoutineRepository(application: Application) {

    private val db = AppDatabase.getDatabase(application)
    private val routineDao: RoutineDao = db.routineDao()

    fun getAllRoutines(): LiveData<List<Routine>> {
        return routineDao.getAll()
    }

    suspend fun insert(routine: Routine) {
        routineDao.insert(routine)
    }

    suspend fun delete(routine: Routine) {
        routineDao.delete(routine)
    }

}