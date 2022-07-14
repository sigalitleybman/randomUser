package com.example.androidassignment.utils

import com.example.androidassignment.data.User
import com.example.androidassignment.model.Birthday
import com.example.androidassignment.model.Name
import com.example.androidassignment.model.Picture
import com.example.androidassignment.model.UserInfo
import com.example.androidassignment.ui.fragments.EnteryFragment

class ConvertUserListToUserInfoList {
    object Consts{
        const val lastUsers = 10
    }
    companion object{
        fun ConvertUserListToUserInfoList(users: List<User>): List<UserInfo>{
            var firstName: String
            var lastName: String
            var name: Name

            var email: String

            var pictureLarge: String
            var picture: Picture

            var age: Int

            var dob: Birthday

            var listOfUsers: List<User> = users
            val listOfInfoUsers: MutableList<UserInfo> = mutableListOf()

            if(listOfUsers.size > 10){
                listOfUsers = listOfUsers.takeLast(Consts.lastUsers)
            }

            listOfUsers.forEach{user ->
                //converted from User to UserInfo
                firstName = user.name.firstName
                lastName = user.name.lastName
                name = Name(firstName, lastName)

                email = user.email

                pictureLarge = user.picture
                picture = Picture(pictureLarge)

                age = user.age
                dob = Birthday(age)

                //create userInfo object and add it to the list of users
                val userInfo = UserInfo(name, email, picture, dob)

                listOfInfoUsers.add(userInfo)
            }

            return listOfInfoUsers
        }
    }
}
