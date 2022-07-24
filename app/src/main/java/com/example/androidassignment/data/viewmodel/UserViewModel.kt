package com.example.androidassignment.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.data.User
import com.example.androidassignment.data.userdatabase.UserDatabase
import com.example.androidassignment.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * From UserViewModel we are going to access all our queries from our UserDao class.
 * UserViewModel Acts as a communication center between the Repository (data) and the UI.
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * LiveData NOTIFY observer objects (in UI) every time the data changes in the database.
     * It Holds all the data needed for the UI.
     */
    var readAllData: LiveData<List<User>>
    private val repository: UserRepository

    /*companion object{
        lateinit var listOfUsers: List<UserInfo>
    }*/

    init {
        val userDao = UserDatabase.getDatabase(application)!!.userDao()
        repository = UserRepository
        repository.setUserDao(userDao)
        readAllData = repository.getReadAllData()
    }

    fun addUser(user: User) {
        /**
         * viewModelScope - part of coroutine (divide cpu time between different jobs).
         * A SCOPE controls the lifetime of coroutines through its job.
         * Dispatchers.IO - means that i want to run this code in a background thread.
         */
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun addAllUsers(users: List<User>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllUsers(users)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun deleteAllAndInsertUsers(users: List<User>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAndInsertInTransaction(users)
        }
    }
}