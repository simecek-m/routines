package dev.simecek.routines.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.simecek.routines.database.entity.Routine

@Dao
interface RoutineDao {

    @Query("SELECT * FROM Routine ORDER BY reminder ASC")
    fun getAll(): LiveData<List<Routine>>

    @Insert
    suspend fun insert(routine: Routine): Long

    @Delete
    suspend fun delete(routine: Routine)

    @Update
    suspend fun update(routine: Routine)

    @Query("SELECT * FROM Routine ORDER BY reminder ASC")
    suspend fun getAllAsList(): List<Routine>

    @Query("SELECT * FROM Routine WHERE id = :id")
    suspend fun getRoutine(id: Long): Routine

}