package com.example.androidassignment.intents

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import com.example.androidassignment.R
import com.example.androidassignment.databinding.ActivityMainBinding.inflate
import com.example.androidassignment.databinding.FragmentEnteryBinding.inflate
import com.example.androidassignment.databinding.FragmentUserDetailsBinding.inflate
import com.example.androidassignment.databinding.ToastForConnectionModeBinding.inflate
import com.example.androidassignment.databinding.UserItemBinding.inflate

//Implicit approach because it's not act directly but just subscribe to this event

//BroadcastReceiver - it's like an Notify class
class ConnectionModeChangedReceiver: BroadcastReceiver() {

    //This code will executed when user will press on connection mode.
    //intent has the information if connection mode is set to on or to off.
    //The system will see which app want to be notified to the intent.
    override fun onReceive(context: Context?, intent: Intent?) {
      /*  val isConnectionModeEnabled = intent?.getBooleanExtra("state", true) ?: return

        if(isConnectionModeEnabled){
            Toast.makeText(context, "Connection mode enabaled", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Please turn on your connection mode", Toast.LENGTH_SHORT).show()
        }*/

        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent?.action)){
            val isConnectionModeDisabled = intent?.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false) ?: return

            if(isConnectionModeDisabled){
                Toast.makeText(context, "Please turn on your connection mode", Toast.LENGTH_LONG).show()
            }else{
                //Toast.makeText(context, "Connection mode enabaled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*private fun showToast(text: String){
        //val inflater: LayoutInflater = getLayout
        val layout: View = LayoutInflater.inflate(R.layout.toast_for_connection_mode, (ViewGroup)findViewById(R.id.toast_toot))
        val toast: Toast = Toast(ApplicationContext)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration(Toast.LENGTH_SHORT)
        toast.view(layout)
        toast.show()
    }*/

}