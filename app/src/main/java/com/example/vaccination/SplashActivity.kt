package com.example.vaccination

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)

        //특정시간에 스레드를 실행시켜주는 역할
        val backgroundExe : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

        //스레드를 만들어주는 역할
        val mainExe:Executor = ContextCompat.getMainExecutor(this)

        backgroundExe.schedule({
            //스레드 형태로 실행
            mainExe.execute({
                //mainactivity부르기 -> splsh 화면에서 바로 main으로 넘어감 -> //특정 시간보이고 main으로 넘어가도록
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            })},
            3, //3초 동안 보여줌
            TimeUnit.SECONDS
        )


    }
}