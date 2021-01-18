package dev.simecek.routines.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.simecek.routines.model.Routine

@Dao
interface RoutineDao {

    @Query("SELECT * FROM Routine")
    fun getAll(): LiveData<List<Routine>>

    @Insert
    fun insert(routine: Routine)

    @Delete
    fun delete(routine: Routine)

}