package com.example.mymusicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymusicplayer.databinding.ActivityPlaylistBinding

class Playlist_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.constraintlayout.widget.R.style.Theme_AppCompat_DayNight)
        binding= ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}