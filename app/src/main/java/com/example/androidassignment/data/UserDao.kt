package com.example.androidassignment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//In this class we will create all the necessary quires
//which we are going to execute inside our DB
@Dao
interface UserDao {

    //In @Insert annotation we specify OnConflictStrategy
    //that is strategy interface that receives "IGNORE"
    //--> if there is a new exactly the same user, then we're going to
    //just ignore that
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id")
    fun readAllData(): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}