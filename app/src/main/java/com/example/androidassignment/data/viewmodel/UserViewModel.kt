package com.example.androidassignment.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.data.User
import com.example.androidassignment.data.userdatabase.UserDatabase
import com.example.androidassignment.data.repository.UserRepository
import com.example.androidassignment.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//From UserViewModel we are going to access all our queries from our UserDao class.
//This class communicate between Repository and the UI.
class UserViewModel(application: Application): AndroidViewModel(application){
    //LiveData = notify observer objects when lifeCycle change
    var readAllData: LiveData<List<User>>
    private val repository: UserRepository

    /*companion object{
        lateinit var listOfUsers: List<UserInfo>
    }*/

    init {
        val userDao = UserDatabase.getDatabase(application)!!.userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        //viewModelScope -> part of coroutine = divide cpu time between different jobs.
        //Dispatchers.IO -> means that i want to run this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun addAllUsers(users: List<User>){
        //viewModelScope -> part of coroutine = divide cpu time between different jobs.
        //Dispatchers.IO -> means that i want to run this code in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllUsers(users)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
}