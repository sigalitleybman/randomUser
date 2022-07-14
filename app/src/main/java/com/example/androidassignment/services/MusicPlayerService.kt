package com.example.androidassignment.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.androidassignment.R

/**
 * implemented background service
 */
class MusicPlayerService: Service() {
    lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.music)
        player.setLooping(true); // Set looping
        player.setVolume(100F, 100F)
    }

    /**
     * executed avery time we call the service
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       Toast.makeText(this, "music is playing :)", Toast.LENGTH_SHORT).show()
        player.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
       return null
    }

    override fun onDestroy() {
        player.stop()
        player.release()
    }
}