package com.majestadev.rickandmortyguide.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.ActivitySplashBinding
import com.majestadev.rickandmortyguide.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding?.root

        binding?.rickAnimationImageView?.let {
            Glide
                .with(this)
                .load(R.drawable.orig)
                .into(it)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

        setContentView(view)
    }
}