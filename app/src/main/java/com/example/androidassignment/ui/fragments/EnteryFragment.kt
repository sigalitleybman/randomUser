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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.R
import com.example.androidassignment.data.Name
import com.example.androidassignment.data.User
import com.example.androidassignment.data.viewmodel.UserViewModel
import com.example.androidassignment.model.UserInfo
import com.example.androidassignment.model.UserResponse
import com.example.androidassignment.network.ApiClient
import com.example.androidassignment.ui.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_entery.*
import retrofit2.Call
import retrofit2.Response
import kotlin.properties.Delegates


class EnteryFragment : Fragment(R.layout.fragment_entery), UserAdapter.OnUserClickListener {
    lateinit var fragmentEnteryRecyclerView: RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var progressBar: ProgressBar
    lateinit var navController: NavController
    val TAG = "EnteryFragment"
    private lateinit var userViewModel: UserViewModel

    //for access user list from MainActivity class - like static member in Java
    companion object{
        var userList: List<UserInfo> = listOf()
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_entery, container, false)

         userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

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

         if(userList.isEmpty()){
             getAllUsers()
         }else{
             setDataToEntryFragment()
         }

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

                    setDataToEntryFragment()
                }
            }

            //NOT successfully response
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("failed to get data", "" + t.message)
            }
        })
    }

    private fun setDataToEntryFragment(){
        userAdapter = UserAdapter(userList, this@EnteryFragment)
        fragmentEnteryRecyclerView.adapter = userAdapter
        setProgressBarAndRecycleViewVisability()
    }

    private fun setProgressBarAndRecycleViewVisability(){
        progressBar.visibility = View.GONE
        fragmentEnteryRecyclerView.visibility = View.VISIBLE
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

        if(checkIfThereIsExistingDataInDB()){
            deleteDataFromDatabase()
            Toast.makeText(requireContext(), "successfully removed all users from db", Toast.LENGTH_SHORT).show()
        }

        userList.forEach{user->
            val id = 0
            val firstName: String = user.name.first
            val lastName: String = user.name.last
            val name = Name(firstName, lastName)
            val email: String = user.email
            val picture: String = user.picture.large
            val age: Int = user.dob.age

            val user = User(id, name, email, picture, age)

            //Add each user to DB
            userViewModel.addUser(user)
            i++
        }
        Toast.makeText(requireContext(), "$i users added", Toast.LENGTH_SHORT).show()
    }

    private fun checkIfThereIsExistingDataInDB(): Boolean{
        var isExsist = true

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                users -> isExsist = users.size > 0
        })

        return isExsist
    }

    private fun deleteDataFromDatabase(){
        userViewModel.deleteAllUsers()
    }

 }


