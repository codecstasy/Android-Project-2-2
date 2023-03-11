package com.example.mymusicplayer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.media.MediaPlayer
import android.media.audiofx.AudioEffect
import android.os.Bundle
import android.os.IBinder
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mymusicplayer.databinding.ActivityPlayerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.system.exitProcess

class PlayerActivity : AppCompatActivity(), ServiceConnection,MediaPlayer.OnCompletionListener {

      @SuppressLint("StaticFieldLeak")
      companion object {
         lateinit var musicListPA : ArrayList<Music>
         var songPosition: Int = 0

          var isPlaying:Boolean = false
          var musicService: MusicService? = null
           lateinit var binding: ActivityPlayerBinding

           var repeat : Boolean = false
           var min15: Boolean = false
           var min30: Boolean = false
           var min60: Boolean = false

      }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.constraintlayout.widget.R.style.Theme_AppCompat_DayNight)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // for starting service
        val intent = Intent(this,MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)
        initializeLayout()
        binding.backBtnPA.setOnClickListener { finish() }
        binding.playPauseBtnPA.setOnClickListener{

            if (isPlaying) pauseMusic()
            else playMusic()
        }
        binding.previousBtnPA.setOnClickListener {
            prevNextSong(increment = false)

        }
        binding.nextBtnPA.setOnClickListener{
            prevNextSong(increment = true)
        }
        binding.seekbarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) musicService!!.mediaPlayer!!.seekTo(progress)

            }

            override fun onStartTrackingTouch(p0: SeekBar?)= Unit
            override fun onStopTrackingTouch(p0: SeekBar?)=Unit

        })
        binding.repeatBtnPA.setOnClickListener {
            if(!repeat){
                repeat = true
                binding.repeatBtnPA.setColorFilter(ContextCompat.getColor(this,R.color.purple_500))
            }else{
                repeat = false
                binding.repeatBtnPA.setColorFilter(ContextCompat.getColor(this,R.color.cool_pink))
            }
        }
        binding.equalizerBtnPA.setOnClickListener {
            try{
                val eqIntent = Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL)
                eqIntent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, musicService!!.mediaPlayer!!.audioSessionId)
                eqIntent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, baseContext.packageName)
                eqIntent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE,AudioEffect.CONTENT_TYPE_MUSIC)
                startActivityForResult(eqIntent,13)
            }catch (e:Exception){Toast.makeText(this,"Equalizer Feature not not supported!!",Toast.LENGTH_SHORT).show()}
        }
        binding.timerBtnPA.setOnClickListener {
            val timer = min15 || min30 || min60
             if(!timer) showBottomSheetDialog()
            else{
                 val builder = MaterialAlertDialogBuilder(this)
                 builder.setTitle("Stop Timer")
                     .setMessage("Do you want to stop timer?")
                     .setPositiveButton("Yes"){_,_ ->
                         min15 = false
                         min30 = false
                         min60 = false
                         binding.timerBtnPA.setColorFilter((ContextCompat.getColor(this, R.color.purple_200)))
                     }
                     .setNegativeButton("No"){dialog,_ ->
                         dialog.dismiss()
                     }
                 val customDialog = builder.create()
                 customDialog.show()
                 customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                 customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)

             }
        }


    }
    private fun setLayout (){
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_slash_screen).centerCrop())
            .into(binding.songimgPA)
         binding.songNamePA.text = musicListPA[songPosition].title
        if(repeat) binding.repeatBtnPA.setColorFilter(ContextCompat.getColor(this,R.color.purple_500))
        if(min15|| min30|| min60) binding.timerBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.purple_500))

    }
    private fun createMediaPlayer(){
         try {
             if(musicService!!.mediaPlayer == null ) musicService!!.mediaPlayer = MediaPlayer()
             musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
             isPlaying = true
             binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
             binding.tvSeekbarStart.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
             binding.tvSeekbarEnd.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
             binding.seekbarPA.progress = 0
             binding.seekbarPA.max = musicService!!.mediaPlayer!!.duration
             musicService!!.mediaPlayer!!.setOnCompletionListener(this)
         }
         catch (e:Exception){return}

    }
    private fun initializeLayout(){
        songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")){

            "MusicAdapter" ->{

                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()


            }
            "MainActivity" ->{
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                musicListPA.shuffle()
                setLayout()


            }
        }
    }
    private fun playMusic(){
        binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        isPlaying = true
       musicService!!.mediaPlayer!!.start()
    }
    private fun pauseMusic(){
        binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
        isPlaying = false
       musicService!!.mediaPlayer!!.pause()
    }
    private fun prevNextSong(increment: Boolean){
        if(increment)
        {
            setSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        }
        else{
            setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()

        }

    }
    private fun setSongPosition(increment: Boolean){
        if(increment)
        {
            if(musicListPA.size - 1 == songPosition)
                songPosition = 0

            else ++songPosition


        }
        else
        {
            if(0 == songPosition)
                songPosition = musicListPA.size-1

            else --songPosition

        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.seekBarSetup()


    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }

    override fun onCompletion(mp: MediaPlayer?) {
         setSongPosition(increment = true)
         createMediaPlayer()
         try{setLayout()} catch (e:Exception){return}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 13|| resultCode == RESULT_OK)
            return
    }
    private fun showBottomSheetDialog(){
        val dialog = BottomSheetDialog(this@PlayerActivity)
        dialog.setContentView(R.layout.bottom_sheet_dialog)
        dialog.show()
        dialog.findViewById<LinearLayout>(R.id.min_15)?.setOnClickListener {
            Toast.makeText(baseContext, "Music will stop after 15 minutes ",Toast.LENGTH_SHORT).show()
            binding.timerBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.purple_500))
            min15 = true
            Thread{Thread.sleep(15 * 60000)
            if (min15) exitApplication()}.start()
            dialog.dismiss()
        }
        dialog.findViewById<LinearLayout>(R.id.min_30)?.setOnClickListener {
            Toast.makeText(baseContext, "Music will stop after 30 minutes ",Toast.LENGTH_SHORT).show()
            binding.timerBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.purple_500))
            min30 = true
            Thread{Thread.sleep(30 * 60000)
                if (min30) exitApplication()}.start()
            dialog.dismiss()
        }
        dialog.findViewById<LinearLayout>(R.id.min_60)?.setOnClickListener {
            Toast.makeText(baseContext, "Music will stop after 60 minutes ",Toast.LENGTH_SHORT).show()
            binding.timerBtnPA.setColorFilter(ContextCompat.getColor(this, R.color.purple_500))
            min60 = true
            Thread{Thread.sleep(60 * 60000)
                if (min60) exitApplication()}.start()
            dialog.dismiss()
        }
    }
}