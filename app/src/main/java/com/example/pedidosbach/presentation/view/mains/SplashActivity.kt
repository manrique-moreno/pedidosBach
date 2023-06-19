package com.example.pedidosbach.presentation.view.mains

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.pedidosbach.databinding.ActivitySplashBinding
import com.example.pedidosbach.presentation.view.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : ActivityFather() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {

    }
}