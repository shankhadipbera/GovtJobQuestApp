package com.shankha.govtjobquest.subCatagory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.shankha.govtjobquest.QuizActivity
import com.shankha.govtjobquest.R
import com.shankha.govtjobquest.ads.AppOpenAdsManager
import com.shankha.govtjobquest.ads.interstitialAds
import com.shankha.govtjobquest.databinding.ActivityGscienceBinding

class GScienceActivity : AppCompatActivity() {
    private val binding: ActivityGscienceBinding by lazy {
        ActivityGscienceBinding.inflate(layoutInflater)
    }
    val parents: String = "1Uv2yXdL3YRHZBdQ2A6WuehHqgpvHOXeTINSqmD7DiZI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        title = " General Science"

        MobileAds.initialize(this) {}

        interstitialAds(this)

        binding.Physics.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GS_Physics")
            intent.putExtra("title","Physics")
            startActivity(intent)
        }
        binding.Chemistry.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GS_Chemistry")
            intent.putExtra("title","Chemistry")
            startActivity(intent)
        }
        binding.Computer.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GS_Computer")
            intent.putExtra("title","Computer")
            startActivity(intent)
        }
        binding.Ecology.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GS_Ecology")
            intent.putExtra("title","Ecology")
            startActivity(intent)
        }
        binding.Biology.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GS_Biology")
            intent.putExtra("title","Biology")
            startActivity(intent)
        }

        val appOpenAdsManager = AppOpenAdsManager.getInstance(this)
        appOpenAdsManager.fetchAd()

    }
    /*
    override fun onDestroy() {
        super.onDestroy()
        interstitialAds(this)
    }

     */

    override fun onResume() {
        super.onResume()
        val appOpenAdsManager = AppOpenAdsManager.getInstance(this)
        appOpenAdsManager.onResume(this)
    }

}