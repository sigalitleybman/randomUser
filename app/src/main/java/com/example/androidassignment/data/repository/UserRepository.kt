package com.example.androidassignment.data.repository

import androidx.lifecycle.LiveData
import com.example.androidassignment.data.User
import com.example.androidassignment.data.UserDao

//A repository class abstracts access to multiple data sources
class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}