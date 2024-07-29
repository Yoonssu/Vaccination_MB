package com.example.vaccination

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vaccination.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            Toast.makeText(this, "인적사항이 저장되었습니다.", Toast.LENGTH_LONG).show()
        }

        binding.button2.setOnClickListener {

            //바로 전화앱과 연결
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-911"))
            startActivity(intent)
        }

        var rocketAnimation : AnimationDrawable
        val rocketImage = binding.rocketImage.apply {
            setBackgroundResource(R.drawable.rocket)
            rocketAnimation = background as AnimationDrawable
        }

        binding.rocketImage.setOnClickListener{ //이미지뷰 클릭했을때만 실행됨.
            rocketAnimation.start()
        }


    }
}