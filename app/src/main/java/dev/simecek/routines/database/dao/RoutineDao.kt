package dev.simecek.routines.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.simecek.routines.database.entity.Routine
import java.time.LocalDate

@Dao
interface RoutineDao {

    @Query("SELECT * FROM Routine WHERE owner_name like :ownerName AND soft_deleted = 0 ORDER BY reminder ASC")
    fun getRoutines(ownerName: String): LiveData<List<Routine>>

    @Insert
    suspend fun insert(routine: Routine): Long

    @Delete
    suspend fun delete(routine: Routine)

    @Update
    suspend fun update(routine: Routine)

    @Query("SELECT * FROM Routine WHERE owner_name like :ownerName AND soft_deleted = 0 ORDER BY reminder ASC")
    suspend fun getRoutinesAsList(ownerName: String): List<Routine>

    @Query("SELECT * FROM Routine WHERE id = :id")
    suspend fun getRoutine(id: Long): Routine

    @Query("SELECT * FROM Routine WHERE owner_name like :ownerName AND soft_deleted = 0 AND(last_finished NOT LIKE :today OR last_finished IS NULL)")
    suspend fun getUnfinishedRoutinesAsList(ownerName: String, today: String = LocalDate.now().toString()): List<Routine>

    @Query("SELECT * FROM Routine WHERE owner_name like :ownerName AND soft_deleted = 1 ORDER BY reminder ASC")
    fun getSoftDeletedRoutines(ownerName: String): LiveData<List<Routine>>

}