package com.example.mymusicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusicplayer.databinding.ActivityPlaylistBinding

class Playlist_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var adapter: PlaylistViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.constraintlayout.widget.R.style.Theme_AppCompat_DayNight)
        binding= ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tempList = ArrayList<String>()
        tempList.add("Travel Songs")
        tempList.add("Lets Enjoy the Music")
        tempList.add("My Playlist")
        tempList.add("Travel Songs for Train")
        tempList.add("Travel Songs for Bus ")
        binding.playlistRV.setHasFixedSize(true)
        binding.playlistRV.setItemViewCacheSize(14)
        binding.playlistRV.layoutManager = GridLayoutManager(this@Playlist_Activity,2)
        adapter = PlaylistViewAdapter(this,tempList)
        binding.playlistRV.adapter = adapter
        binding.backBtnPLA.setOnClickListener { finish() }

    }
}