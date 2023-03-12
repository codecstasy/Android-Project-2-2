package com.example.mymusicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mymusicplayer.databinding.FavouriteViewBinding
import com.example.mymusicplayer.databinding.PlaylistViewBinding

class PlaylistViewAdapter (private val context: Context, private val playlistList: ArrayList<String>): RecyclerView.Adapter<PlaylistViewAdapter.MyHolder>() {
    class MyHolder(binding: PlaylistViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.playlistimg
        val name = binding.playlistName
        val root = binding.root

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(PlaylistViewBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = playlistList[position]
        holder.name.isSelected = true


    }
    override fun getItemCount(): Int {

        return playlistList.size

    }



}