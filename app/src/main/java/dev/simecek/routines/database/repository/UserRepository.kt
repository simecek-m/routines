package dev.simecek.routines.database.repository

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import dev.simecek.routines.database.dao.UserDao
import dev.simecek.routines.database.entity.User
import dev.simecek.routines.exception.UserAlreadyExistsException
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun getAll(): LiveData<List<User>> {
        return userDao.getAll()
    }

    fun getUserByName(name: String): User {
        return userDao.getByName(name)
    }

    suspend fun register(user: User){
        try {
            userDao.insert(user)
        } catch (ex: SQLiteConstraintException) {
            throw UserAlreadyExistsException()
        }

    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }
}
