package com.example.androidassignment.ui.fragments

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
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
import com.example.androidassignment.intents.ConnectionModeChangedReceiver
import com.example.androidassignment.model.UserInfo
import com.example.androidassignment.model.UserResponse
import com.example.androidassignment.network.ApiClient
import com.example.androidassignment.ui.adapter.UserAdapter
import com.example.androidassignment.utils.ConvertUserInfoListToUserList
import com.example.androidassignment.utils.ConvertUserListToUserInfoList
import kotlinx.android.synthetic.main.fragment_entery.*
import retrofit2.Call
import retrofit2.Response


class EnteryFragment : Fragment(R.layout.fragment_entery), UserAdapter.OnUserClickListener {
    lateinit var fragmentEnteryRecyclerView: RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var progressBar: ProgressBar
    lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel
    lateinit var reciever: ConnectionModeChangedReceiver
    var userList: MutableList<UserInfo> = mutableListOf()

    object Constants {
        const val NUMBER_OF_USERS_TO_GET = 10
        const val TAG = "EnteryFragment"
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_entery, container, false)

         userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Return the fragment view/layout
        return view
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         /**
          * Whenever fragment_entery will initiate, the navController will have reference to the nav graph
          */
         navController = Navigation.findNavController(view)

         fragmentEnteryRecyclerView = recyclerView
         fragmentEnteryRecyclerView.layoutManager = LinearLayoutManager(this.context)

         initializeDataInEntryFragment()

         /**
          * Observer connected to LivaData.
          * Whenever we change the data in recycleView, LiveData NOTIFY to each of the OBSERVERs.
          * So here this code will executed after we open the app and after each refresh.
          */
         userViewModel.readAllData.observe(viewLifecycleOwner, Observer { listOfUsersFromDB ->
             listOfUsersFromDB?.let {
                    listOfUsers -> run {
                        if (listOfUsers.isEmpty()) {
                            getAllUsersFromApiService()
                        } else {
                            userList = ConvertUserListToUserInfoList.ConvertUserListToUserInfoList(listOfUsers) as MutableList<UserInfo>
                            userAdapter.setItems(userList)
                        }
                    }
             }
         })

         reciever = ConnectionModeChangedReceiver()
     }

    /**
     * onResume() active while the fragment is visible in the running activity.
     */
    override fun onResume() {
        super.onResume()

        swipeRefreshLayout.setOnRefreshListener{
            getAllUsersFromApiService()
            //TODO - better put insertDataToDatabase() when successfully we get all users. because there is option there is nothing to get
            //insertDataToDatabase(userList)
            registerToConnectionModeReciever()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    /**
     * onPause() active when we want to stop the registration to ConnectionModeChangedReceiver
     * because the user might not come back.
     */
    override fun onPause() {
        super.onPause()
        unRegisterToConnectionModeReciever()
    }

    fun registerToConnectionModeReciever(){

        /**
         * To keep it as long as the application is running, we will define it in onCreate method.
         * It's the observer.
         * We specify to which intents we want to observe - in our case: CONNECTIVITY_ACTION
         */
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also{ intentFilter->
            requireActivity().registerReceiver(reciever, intentFilter)
        }
    }

    //I added try catch because i received IllegalArgumentException exception while unregisterReceiver.
    // the problem could be - If the receiver was already unregistered
    // or was not registered, then call to unregisterReceiver throws IllegalArgumentException
    fun unRegisterToConnectionModeReciever(){
       try {
           requireActivity().unregisterReceiver(reciever)
       }catch (e: IllegalArgumentException){
               e.printStackTrace()
       }
    }


     private fun getAllUsersFromApiService(){
        /**
         * Here we get all the users from ApiService.
         */
        val client = ApiClient.apiService.getUsers(Constants.NUMBER_OF_USERS_TO_GET)


         /**
          * Here we are calling method to enqueue and calling all the data from userList.
          */
        client.enqueue(object : retrofit2.Callback<UserResponse>{
            //successfully responses
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.d(Constants.TAG, "we are in onResponse method" + response.body())

                    /**
                     * Here we are adding our data from api to our array list
                     */
                    userList = (response.body()?.result as MutableList<UserInfo>?)!!

                    insertDataToDatabase(userList)

                  /*  //instead of do all the logc of create an adapter i just update the list.
                    //because it is not the first call so no need for all of this...
                    //setDataToEntryFragment()

                   // insertDataToDatabase(userList)*/

                }else{
                    Log.d(Constants.TAG, "Problem with get usere from the api")
                }
            }

            //NOT successfully response
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("failed to get data", "" + t.message)
            }
        })
    }

    fun initializeDataInEntryFragment(){
        userAdapter = UserAdapter(mutableListOf(), this@EnteryFragment)
        fragmentEnteryRecyclerView.adapter = userAdapter
    }

    fun updateUserListInUserAdapter(listOfUsers: MutableList<UserInfo>){
        userAdapter.userList = listOfUsers
    }

    /**
     * This function executed when you are choosing a user.
     */
    override fun onUserClickListener(results: UserInfo, sharedImageView: ImageView) {
        when(sharedImageView.id){
            R.id.userImageView -> {
                //bundle -> it's key+value: key - currentUser ,value - results(the chosen user)
                val bundle = bundleOf("currentUser" to results)
                //navController.navigate(R.id.action_enteryFragment_to_userDetailsFragment, bundle)
                //insertDataToDatabase()
                findNavController().navigate(R.id.action_enteryFragment_to_userDetailsFragment, bundle)
            }
        }
    }

    /**
     * After we will insert new users to the db, the LiveData in ViewModel will notify
     * to the observer in onViewCreated() and then: userAdapter.setItems(userList) line
     * will update the view with new loaded users.
     */
    private fun insertDataToDatabase(userList: List<UserInfo>){
       //TODO - put this block in comment because if there is data in db we will delete + add and it
       // will lead to invoke twice the observer and it is not efficient
        /* if(checkIfThereIsExistingDataInDB()){
            deleteDataFromDatabase()
        }

        userViewModel.addAllUsers(ConvertUserInfoListToUserList.convertUserInfoListToUserList(userList))*/

        if(checkIfThereIsExistingDataInDB()){
            userViewModel.deleteAllAndInsertUsers(ConvertUserInfoListToUserList.convertUserInfoListToUserList(userList))
        } else {
            userViewModel.addAllUsers(ConvertUserInfoListToUserList.convertUserInfoListToUserList(userList))
        }
    }

    /**
     * This method called just for one check and not for the all run of the program.
     * Thus, no need for observe because observe it's when you want the db will observe
     * for updates while all running.
     */
    private fun checkIfThereIsExistingDataInDB(): Boolean{
        return userViewModel.readAllData.value?.isNotEmpty() ?: false
    }

    private fun deleteDataFromDatabase(){
        userViewModel.deleteAllUsers()
    }
 }


