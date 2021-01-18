package dev.simecek.routines.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.simecek.routines.dao.RoutineDao
import dev.simecek.routines.database.AppDatabase
import dev.simecek.routines.model.Routine

class RoutineRepository(application: Application) {

    private val db = AppDatabase.getDatabase(application)
    private val routineDao: RoutineDao = db.routineDao()


    fun getAllRoutines(): LiveData<List<Routine>> {
        return routineDao.getAll()
    }

    fun insert(routine: Routine) {
        routineDao.insert(routine)
    }

    fun delete(routine: Routine) {
        routineDao.delete(routine)
    }

}