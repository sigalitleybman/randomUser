package com.example.androidassignment.utils

import com.example.androidassignment.data.Name
import com.example.androidassignment.data.User
import com.example.androidassignment.model.UserInfo

class ConvertUserInfoListToUserList {

    companion object{
        fun convertUserInfoListToUserList(listOfUserInfo: List<UserInfo>): List<User>{
            val listOfUsers: MutableList<User> = mutableListOf()
            var id = 0

            listOfUserInfo.forEach{user->
                val firstName: String = user.name.first
                val lastName: String = user.name.last
                val name = Name(firstName, lastName)

                val email: String = user.email

                val picture: String = user.picture.large

                val age: Int = user.dob.age

                val user = User(id++, name, email, picture, age)

               listOfUsers.add(user)
            }

            return listOfUsers
        }
    }

}