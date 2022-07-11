package com.example.androidassignment.data.repository

import androidx.lifecycle.LiveData
import com.example.androidassignment.data.User
import com.example.androidassignment.data.userdatabase.UserDao
import com.example.androidassignment.data.userdatabase.UserDatabase

//A repository class acts like a FACADE DESIGN PATTERN and SINGLETON DESIGN PATTERN

//not singleton class
/*//Because UserRepository want access the db, there is no need to it to be exposed to whole db,
//thus we it recieves only the UserDao in its constructor.
class UserRepository(private val userDao: UserDao) {
    //By default Room runs SUSPEND queries NOT on the main thread.


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun addAllUsers(usersList: List<User>){
        userDao.addAllUsers(usersList)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}*/

//singleton class
object UserRepository{
    private lateinit var readAllData: LiveData<List<User>>
    private lateinit var  userDao: UserDao

    fun setUserDao(userDao: UserDao){
        this.userDao = userDao
        setReadAllData()
    }

    private fun setReadAllData(){
        readAllData = userDao.readAllData()
    }

    fun getReadAllData(): LiveData<List<User>>{
        return readAllData
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun addAllUsers(usersList: List<User>){
        userDao.addAllUsers(usersList)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}