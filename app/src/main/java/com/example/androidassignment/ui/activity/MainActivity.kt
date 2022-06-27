package com.example.androidassignment.ui.activity

//import androidx.fragment.app.Fragment
//import android.R
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidassignment.R
import com.example.androidassignment.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //lateinit var binding: ActivityMainBinding
    val TAG =  "MainActivity"
    lateinit var userListSavingState: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*findViewById<LinearLayout>(R.id.linearlayoutOfUsers).setOnClickListener(View.OnClickListener(){

        })*/

      /* binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)

     //it's for later switch between two of them
       val enteryFragment = EnteryFragment()
       val userDetailsFragment = UserDetailsFragment()*/

        /*supportFragmentManager.beginTransaction().apply{
            addToBackStack(null)
            commit()
        }*/

       /* .setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                addToBackStack(null)
                commit()
            }
        }*/

    }



    override fun onBackPressed() {
        super.onBackPressed()

        val enteryFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.enteryFragment)

        Log.i(TAG, "entered onBackPressed")
        //test
        if (enteryFragment != null && enteryFragment.isVisible){
            Log.i(TAG, "entered if in onBackPressed")
            //userListSavingState = enteryFragment.userList
        }else{
           // (enteryFragment as EnteryFragment).userList = userListSavingState
        }
    }
}



//---------the change
/*class MainActivity : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.activity_main, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //final View view =
        //setContentView(R.layout.activity_main)

        //it's for later switch between two of them
//        val enteryFragment = EnteryFragment()
//        val userDetailsFragment = UserDetailsFragment()

    }
}*/
