package com.example.androidassignment.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.data.User
import com.example.androidassignment.data.viewmodel.UserViewModel
import com.example.androidassignment.model.UserInfo
import com.example.androidassignment.model.UserResponse
import com.example.androidassignment.network.ApiClient
import com.example.androidassignment.ui.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_entery.*
import retrofit2.Call
import retrofit2.Response


class EnteryFragment : Fragment(R.layout.fragment_entery), UserAdapter.OnUserClickListener {
    lateinit var fragmentEnteryRecyclerView: RecyclerView
    //lateinit var userList: List<UserInfo>
    lateinit var userAdapter: UserAdapter
    lateinit var progressBar: ProgressBar
    lateinit var navController: NavController
    val TAG = "EnteryFragment"
    private lateinit var userViewModel: UserViewModel

    //for access user list from MainActivity class
    companion object{
        var userList: List<UserInfo> = listOf()
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_entery, container, false)

         userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Return the fragment view/layout
        return view
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         //whenever fragment_entery will initiate, the navController will have reference to the nav graph
         navController = Navigation.findNavController(view)

         fragmentEnteryRecyclerView = recyclerView
         fragmentEnteryRecyclerView.layoutManager = LinearLayoutManager(this.context)

         progressBar = progressBarFragmentEntery

         fragmentEnteryRecyclerView.visibility = View.GONE
         progressBar.visibility = View.VISIBLE

         if(userList.isNotEmpty()){
             userList = listOf()
         }

         getAllUsers()

         swipeRefreshLayout.setOnRefreshListener{
             getAllUsers()
             insertDataToDatabase()
             swipeRefreshLayout.isRefreshing = false
         }
     }

     private fun getAllUsers(){
        // here we get all the users from ApiService
        val client = ApiClient.apiService.getUsers(10)

        // here we are calling method to enqueue and calling
        // all the data from userList.
        client.enqueue(object : retrofit2.Callback<UserResponse>{
            //successfully responses
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.d("users", "" + response.body())

                    //here we are adding our data from api to our array list ????
                    userList = response.body()?.result!!

                    userAdapter = UserAdapter(userList, this@EnteryFragment)
                    fragmentEnteryRecyclerView.adapter = userAdapter
                    progressBar.visibility = View.GONE
                    fragmentEnteryRecyclerView.visibility = View.VISIBLE
                }
            }

            //NOT successfully response
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("failed to get data", "" + t.message)
            }
        })

    }

    //it's when i am choosing a user
    override fun onUserClickListener(results: UserInfo, sharedImageView: ImageView) {
        //userList = userListSavingState
        when(sharedImageView.id){
            R.id.userImageView -> {
                //bundle -> it's key+value:
                //key - currentUser
                //value- results(the chosen user)
                val bundle = bundleOf("currentUser" to results)
                //navController.navigate(R.id.action_enteryFragment_to_userDetailsFragment, bundle)
                insertDataToDatabase()
                findNavController().navigate(R.id.action_enteryFragment_to_userDetailsFragment, bundle)
            }
        }
    }


    private fun insertDataToDatabase(){
        var i = 0
        userList.forEach{
            val id: Int = 0
            val firstName: String = it.name.first
            val lastName: String = it.name.last
            val email: String = it.email
            val picture: String = it.picture.large
            val age: Int = it.dob.age

            val user = User(id, firstName, lastName, email, picture, age)

            //Add each user to DB
            userViewModel.addUser(user)
            i++
        }
        Toast.makeText(requireContext(), "$i users added", Toast.LENGTH_SHORT).show()
    }
 }
