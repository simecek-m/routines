package dev.simecek.routines.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.simecek.routines.database.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User ORDER BY name ASC")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE name like :name")
    fun getByName(name: String): User

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

}
