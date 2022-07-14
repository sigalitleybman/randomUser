package com.example.androidassignment.data.userdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidassignment.data.User

/**
 * In this class we will mapping SQL queries (CRUD) to functions which we are
 * going to execute inside our DB.
 */
@Dao
interface UserDao {
    /**
     * In @Insert annotation we specify OnConflictStrategy that is strategy interface that
     * receives "IGNORE". If there is a new exactly the same user,
     * then we're going to just ignore that.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllUsers(users: List<User>)

    @Query("SELECT * FROM user_table ORDER BY id")
    fun readAllData(): LiveData<List<User>>

    /**
     * SUSPEND - is a function that could be started, paused, and resume. It only called from COROUTINE.
     */
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}