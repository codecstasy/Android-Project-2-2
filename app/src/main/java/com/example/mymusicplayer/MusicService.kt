package com.example.mymusicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class MusicService: Service() {
    private var myBinder = MyBinder()
    var mediaPlayer:MediaPlayer? = null
    private lateinit var runnable: Runnable
    override fun onBind(p0: Intent?): IBinder {
        return myBinder
    }
    inner class MyBinder: Binder(){

        fun currentService(): MusicService {
            return this@MusicService
        }
    }

    fun seekBarSetup(){
        runnable = Runnable {
            PlayerActivity.binding.tvSeekbarStart.text = formatDuration(mediaPlayer!!.currentPosition.toLong())
            PlayerActivity.binding.seekbarPA.progress = mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable,200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable,0)

    }
}