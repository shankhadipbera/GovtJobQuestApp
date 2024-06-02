package com.shankha.govtjobquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.animation.AnimationUtils
import com.shankha.govtjobquest.databinding.ActivitySplashBinding
import java.util.logging.Handler

class SplashActivity : AppCompatActivity() {
    private  val binding: ActivitySplashBinding by lazy{
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        android.os.Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        },3000)

    }
}