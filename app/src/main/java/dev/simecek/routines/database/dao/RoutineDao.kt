package dev.simecek.routines.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.simecek.routines.database.entity.Routine

@Dao
interface RoutineDao {

    @Query("SELECT * FROM Routine")
    fun getAll(): LiveData<List<Routine>>

    @Insert
    suspend fun insert(routine: Routine)

    @Delete
    suspend fun delete(routine: Routine)

}