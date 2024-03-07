package com.verianggoro.news.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.verianggoro.news.R
import com.verianggoro.news.databinding.ActivitySplashScreenBinding
import java.util.Timer
import java.util.TimerTask

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                goMain()
            }
        }, 2000)
    }

    private fun goMain(){
        val intent = Intent(this, HomeScreen::class.java)
        intent.putExtra(HomeScreen.EVENT_ID, 0)
        startActivity(intent)
    }
}