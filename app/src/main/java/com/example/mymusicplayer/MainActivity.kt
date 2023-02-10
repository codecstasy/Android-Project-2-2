package com.example.mymusicplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymusicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyMusicPlayer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.shuffleBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Button Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity,PlayerActivity::class.java)
            startActivity(intent)
        }
        binding.favouriteBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Button Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity,Favourite_Activity::class.java)
            startActivity(intent)

        }
        binding.playlistBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Button Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity,Playlist_Activity::class.java)
            startActivity(intent)

        }
    }
}