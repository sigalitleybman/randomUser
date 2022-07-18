package com.example.androidassignment.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.HandlerThread
import android.os.IBinder
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.util.Log
import android.widget.Toast
import com.example.androidassignment.R

/**
 * Implemented background service
 */
class MusicPlayerService : Service() {
    lateinit var player: MediaPlayer

    /**
     * The system invokes this method to perform one-time setup procedures
     * when the service is initially created.
     */
    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.music)
        player.setLooping(true); // Set looping
        player.setVolume(100F, 100F)
    }

    /**
     * Executed every time we call the service by startService(). The service will run in the background indefinitely.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "music is playing :)", Toast.LENGTH_SHORT).show()

        /**
         * We created a thread because the service uses our application's main thread by default,
         * which can slow the performance of the activity that our application is running.
         */
        val thread = Thread {
            player.start()
        }
        thread.start()

        //player.start()
        //return super.onStartCommand(intent, flags, startId)

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * The system invokes this method when the service is no longer used and is being destroyed.
     * This is the last call that the service receives.
     */
    override fun onDestroy() {
        player.stop()
        player.release()
    }
}