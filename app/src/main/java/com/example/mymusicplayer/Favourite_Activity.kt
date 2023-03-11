package com.example.mymusicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymusicplayer.databinding.ActivityFavouriteBinding


class Favourite_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight)
        binding= ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtnFA.setOnClickListener { finish() }
    }
}