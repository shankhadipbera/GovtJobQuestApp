package com.shankha.govtjobquest.subCatagory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.shankha.govtjobquest.QuizActivity
import com.shankha.govtjobquest.R
import com.shankha.govtjobquest.ads.AppOpenAdsManager
import com.shankha.govtjobquest.ads.interstitialAds
import com.shankha.govtjobquest.databinding.ActivityGawarenessBinding

class GAwarenessActivity : AppCompatActivity() {
    private val binding: ActivityGawarenessBinding by lazy {
        ActivityGawarenessBinding.inflate(layoutInflater)
    }
    val parents: String = "1TV9KSUKnmbau158sgjEZTEW2ZZ6YBtrVFeGZ2a63uHU"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        MobileAds.initialize(this) {}

        interstitialAds(this)

        title= " General Awareness"

        binding.history.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GA_History")
            intent.putExtra("title","History")
            startActivity(intent)
        }
        binding.polityConstituion.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GA_PolityConstitution")
            intent.putExtra("title","Polity and Constitution")
            startActivity(intent)
        }
        binding.geography.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GA_Geography")
            intent.putExtra("title","Geography")
            startActivity(intent)
        }
        binding.Economy.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GA_Economy")
            intent.putExtra("title","Economy")
            startActivity(intent)
        }
        binding.miscellaneous.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "GA_Miscellaneous")
            intent.putExtra("title","Miscellaneous")
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