package com.example.androidassignment.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidassignment.R
import com.example.androidassignment.databinding.ActivityMainBinding
import com.example.androidassignment.model.User
import com.example.androidassignment.model.UserResponse
import com.example.androidassignment.network.ApiClient
import com.example.androidassignment.network.ApiService
import com.example.androidassignment.ui.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_entery.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create


 class EnteryFragment : Fragment(R.layout.fragment_entery), UserAdapter.OnUserClickListener {
    lateinit var fragmentEnteryRecyclerView: RecyclerView
    lateinit var userList: List<User>
    //lateinit var userListSavingState: List<User>
    lateinit var userAdapter: UserAdapter
    lateinit var progressBar: ProgressBar

    lateinit var navController: NavController

    val TAG = "EnteryFragment"

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_entery, container, false)

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

         userList = listOf()
         //userListSavingState = listOf()

         getAllUsers()

         swipeRefreshLayout.setOnRefreshListener{
             getAllUsers()
             swipeRefreshLayout.isRefreshing = false
         }

     }

    /* override fun onAttach(context: Context) {
         Log.d(TAG, "entered onAttach method")
         super.onAttach(context)
         val callback: OnBackPressedCallback =
             object : OnBackPressedCallback(true)
             {
                 override fun handleOnBackPressed() {
                     Log.i(TAG, "handleOnBackPressed: here")
                     // Leave empty do disable back press or
                     // write your code which you want
                 }
             }
         requireActivity().onBackPressedDispatcher.addCallback(
             this,
             callback
         )
     }*/



/*//for saving the last shown users
     override fun onSaveInstanceState(outState: Bundle) {
         Log.d("EnteryFragment", "saving the last users")
         super.onSaveInstanceState(outState)
         userListSavingState = userList
         activity?.supportFragmentManager?.putFragment(outState, "EnteryFragment", this)
     }

     //for restoring the last shown users
     override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         Log.d("EnteryFragment", "restoring the last users")
         if (savedInstanceState != null) {
             userList = userListSavingState
             userAdapter = UserAdapter(userList, this@EnteryFragment)
             fragmentEnteryRecyclerView.adapter = userAdapter
             progressBar.visibility = View.GONE
             fragmentEnteryRecyclerView.visibility = View.VISIBLE
         }
         if (savedInstanceState != null) {
             activity?.supportFragmentManager?.getFragment(savedInstanceState, "EnteryFragment")
         }
     }*/

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
                    //val result = response.body()?.result

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
    override fun onUserClickListener(results: User, sharedImageView: ImageView) {
        //userList = userListSavingState
        when(sharedImageView.id){
            R.id.userImageView -> {
                //bundle -> it's key+value:
                //key - currentUser
                //value- results(the chosen user)
                val bundle = bundleOf("currentUser" to results)
                navController.navigate(R.id.action_enteryFragment_to_userDetailsFragment, bundle)
            }
        }
    }
 }