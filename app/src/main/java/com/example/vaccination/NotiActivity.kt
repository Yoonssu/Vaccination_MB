package com.example.vaccination

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.vaccination.databinding.ActivityNotiBinding
import java.util.Random
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class NotiActivity : AppCompatActivity() {

    lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //애니메이션
        binding.date.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
            binding.date.startAnimation(anim)
        }

        binding.date1.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
            binding.date1.startAnimation(anim)
        }

        binding.textView0.setOnClickListener{
            var anim = AnimationUtils.loadAnimation(this, R.anim.wave)
            binding.textView0.startAnimation(anim)
        }




        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val textcolor = sharedPreference.getString("color", "#000000") //id 불러오듯이 불러옴
        binding.textView0.setTextColor(Color.parseColor(textcolor))



        //youtube
        val random = Random()
        val num = random.nextInt(5)

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                val videoId :String
                if(num>3) videoId= "blbxNXCtKK0"
                else videoId = "11Hl_3yanK8"
                youTubePlayer.cueVideo(videoId, 0f) //딜레이 없이 바로 시작
            }
        })

    }
}