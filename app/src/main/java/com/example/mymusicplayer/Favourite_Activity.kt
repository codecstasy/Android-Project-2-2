package com.example.mymusicplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusicplayer.databinding.ActivityFavouriteBinding


class Favourite_Activity : AppCompatActivity() {
    private lateinit var adapter: FavouriteAdapter
    private lateinit var binding: ActivityFavouriteBinding

    companion object{
        var favouriteSongs: ArrayList<Music> = ArrayList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight)
        binding= ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtnFA.setOnClickListener { finish() }
        binding.favouriteRV.setHasFixedSize(true)
        binding.favouriteRV.setItemViewCacheSize(14)
        binding.favouriteRV.layoutManager = GridLayoutManager(this,4)
        adapter = FavouriteAdapter( this, favouriteSongs)
        binding.favouriteRV.adapter = adapter
        if(favouriteSongs.size <1) binding.shuffleBtnFA.visibility = View.INVISIBLE
        binding.shuffleBtnFA.setOnClickListener {

            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class","FavouriteShuffle")
            startActivity(intent)
        }
    }
}