package com.example.androidassignment.ui.activity

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.androidassignment.R
import com.example.androidassignment.data.User
import com.example.androidassignment.data.viewmodel.UserViewModel
import com.example.androidassignment.intents.ConnectionModeChangedReceiver
import com.example.androidassignment.services.MusicPlayerService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    lateinit var reciever: ConnectionModeChangedReceiver

    lateinit var serviceIntent: Intent //
    /**
     * The serviceIntent has the all data for MusicPlayerService service.
     */

    lateinit var musicPlayerButton: ToggleButton

    object Consts{
        const val TAG =  "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

       serviceIntent = Intent(this, MusicPlayerService::class.java)

        musicPlayerButton = musicButton
        musicPlayerButton.setOnClickListener(View.OnClickListener(){
            if(musicPlayerButton.isChecked){
                startService(serviceIntent)//then it calls onStartCommand()
            }else{
                stopService(serviceIntent)
            }
        })

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //registerToConnectionModeReciever()
    }

    fun registerToConnectionModeReciever(){
        reciever = ConnectionModeChangedReceiver()

        /**
         * To keep it as long as the application is running, we will define it in onCreate method.
         * It's the observer.
         * We specify to which intents we want to observe - in our case: CONNECTIVITY_ACTION.
         */
        IntentFilter(CONNECTIVITY_ACTION).also{ intentFilter->
            registerReceiver(reciever, intentFilter)
        }
    }

    /**
     * When we stop our app, we don't want to hold the context to intent, cause it's lead to memory leak
     */
    override fun onStop() {
        super.onStop()
        //TODO - add try catch on unregisterReceiver(reciever)
        unregisterReceiver(reciever)
        stopService(serviceIntent)
    }

    override fun onStart() {
        super.onStart()
        registerToConnectionModeReciever()
        if(musicPlayerButton.isChecked){
            startService(serviceIntent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
