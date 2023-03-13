package com.example.mymusicplayer

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

data class Music (val id:String, val title:String, val album:String,val artist:String,val duration: Long = 0,val path:String,
  val artUri:String)

class Playlist{
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createdBy: String
    lateinit var createdOn: String
}
class MusicPlaylist{
    var ref: ArrayList<Playlist> = ArrayList()
}

  @SuppressLint("SuspiciousIndentation")
  fun formatDuration(duration: Long):String{
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)

    val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)-
            minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))

         return String.format("%02d:%02d",minutes,seconds)
  }

  fun setSongPosition(increment: Boolean){
      if(!PlayerActivity.repeat) {

          if(increment){
              if(PlayerActivity.musicListPA.size - 1 == PlayerActivity.songPosition)
                  PlayerActivity.songPosition = 0
              else ++PlayerActivity.songPosition
            }else{

                if(0 == PlayerActivity.songPosition)
                    PlayerActivity.songPosition = PlayerActivity.musicListPA.size -1
              else --PlayerActivity.songPosition

          }
      }



  }
fun exitApplication(){
    if(PlayerActivity.musicService != null){
        PlayerActivity.musicService!!.mediaPlayer!!.release()
        PlayerActivity.musicService = null }
    exitProcess(1)
}
 fun favouriteChecker (id: String): Int{
     PlayerActivity.isFavourite = false
      Favourite_Activity.favouriteSongs.forEachIndexed { index, music ->
          if(id == music.id){
              PlayerActivity.isFavourite = true
              return index
          }
      }
     return -1
 }