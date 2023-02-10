package com.example.mymusicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymusicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

      private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.constraintlayout.widget.R.style.Theme_AppCompat_DayNight)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}