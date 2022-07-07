package com.example.androidassignment.ui.activity

//import androidx.fragment.app.Fragment
//import android.R
//import android.R
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.androidassignment.R
import com.example.androidassignment.data.User
import com.example.androidassignment.data.viewmodel.UserViewModel
import com.example.androidassignment.intents.ConnectionModeChangedReceiver
import com.example.androidassignment.model.UserInfo
import com.example.androidassignment.ui.fragments.EnteryFragment
import com.example.androidassignment.utils.ConvertLiveDataListToUserInfo
import com.example.androidassignment.utils.ConvertUserListToUserInfoList


class MainActivity : AppCompatActivity() {
    //lateinit var userListSavingState: List<UserInfo>
    private lateinit var userViewModel: UserViewModel
    lateinit var reciever: ConnectionModeChangedReceiver

    object Consts{
        const val TAG =  "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

       /* reciever = ConnectionModeChangedReceiver()

        //to keep it as long as thr application is running, we will define it in onCreate method
        //it's the observer
        //we specify to which intents we want to observe - in our case: CONNECTIVITY_ACTION
        IntentFilter(CONNECTIVITY_ACTION).also{ intentFilter->
            registerReceiver(reciever, intentFilter)
        }*/

        registerToConnectionModeReciever()
    }

    fun registerToConnectionModeReciever(){
        reciever = ConnectionModeChangedReceiver()

        //to keep it as long as the application is running, we will define it in onCreate method
        //it's the observer
        //we specify to which intents we want to observe - in our case: CONNECTIVITY_ACTION
        IntentFilter(CONNECTIVITY_ACTION).also{ intentFilter->
            registerReceiver(reciever, intentFilter)
        }
    }

    //when we stop our app, we don't want to hold the context to intent, cause it's lead to memory leak
    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciever)
    }

    override fun onBackPressed() {
        super.onBackPressed()



        /*//for identify the current displaying fragment
        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.navHostFragment)

        //TODO - i should save the list when i move to the next fragment and here get this list instead of reading again from db
        if (navHostFragment != null && navHostFragment.isVisible){
          // userViewModel.readAllData.observe(this, Observer {
          //         users -> setUserListToEnteryFragment(users)
          //         //The problem is when we are in entery fragment and press back we still enter to here
          //         //Log.d("MainActivity", "we are into onBackPressed...")
          // })

            //setUserListToEnteryFragment(userViewModel.readAllData)

        }else{
           // (enteryFragment as EnteryFragment).userList = userListSavingState
        }*/
    }

    private fun setUserListToEnteryFragment(users: List<User>){
        /*var firstName: String
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
            listOfUsers = listOfUsers.takeLast(10)
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
            val userInfo = UserInfo(name, email, picture,dob)

            listOfInfoUsers.add(userInfo)
        }*/

        //val listOfInfoUsers = ConvertUserListToUserInfoList.ConvertUserListToUserInfoList(users)


        //EnteryFragment.userList = listOfInfoUsers
        //EnteryFragment.userList = ConvertLiveDataListToUserInfo.convertLiveDataListToUserInfo(users)
    }
}
