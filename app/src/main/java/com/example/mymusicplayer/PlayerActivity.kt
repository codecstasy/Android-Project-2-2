package com.example.mymusicplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymusicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

      companion object {
         lateinit var musicListPA : ArrayList<Music>
         var songPosition: Int = 0
          var mediaPlayer:MediaPlayer? = null
      }


      private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.constraintlayout.widget.R.style.Theme_AppCompat_DayNight)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
       songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")){

            "MusicAdapter" ->{

                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)

                if(mediaPlayer == null ) mediaPlayer = MediaPlayer()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()



            }
        }

    }
}