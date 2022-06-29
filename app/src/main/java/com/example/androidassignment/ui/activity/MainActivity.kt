package com.example.androidassignment.ui.activity

//import androidx.fragment.app.Fragment
//import android.R
//import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidassignment.R
import com.example.androidassignment.data.User
import com.example.androidassignment.data.viewmodel.UserViewModel
import com.example.androidassignment.model.Birthday
import com.example.androidassignment.model.Name
import com.example.androidassignment.model.Picture
import com.example.androidassignment.model.UserInfo
import com.example.androidassignment.ui.fragments.EnteryFragment


class MainActivity : AppCompatActivity() {
    //lateinit var binding: ActivityMainBinding
    val TAG =  "MainActivity"
    lateinit var userListSavingState: List<UserInfo>
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        //for identify the current displaying fragment
        val userDetailsFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.navHostFragment)

        //Toast.makeText(this, "entered onBackPressed", Toast.LENGTH_SHORT).show()

        if (userDetailsFragment != null && userDetailsFragment.isVisible){
            //Toast.makeText(this, "entered if in onBackPressed", Toast.LENGTH_SHORT).show()
            userViewModel.readAllData.observe(this, Observer {
                    users -> setUserListToEnteryFragment(users)
            })

        }else{
           // (enteryFragment as EnteryFragment).userList = userListSavingState
        }
    }

    private fun setUserListToEnteryFragment(users: List<User>){
        var firstName: String
        var lastName: String
        var name: Name
        var email: String
        var pictureLarge: String
        var picture: Picture
        var age: Int
        var dob: Birthday
        val listOfUsers: MutableList<UserInfo> = mutableListOf()

        users.forEach{
            //converted from User to UserInfo
            firstName = it.firstName
            lastName = it.lastName
            name = Name(firstName, lastName)

            email = it.email

            pictureLarge = it.picture
            picture = Picture(pictureLarge)

            age = it.age
            dob = Birthday(age)

            //crreate userInfo object and add it to the list of users
            val userInfo: UserInfo = UserInfo(name, email, picture,dob)

            listOfUsers.add(userInfo)
        }

        EnteryFragment.userList = listOfUsers
    }
}
