package com.shankha.govtjobquest.subCatagory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.shankha.govtjobquest.QuizActivity
import com.shankha.govtjobquest.R
import com.shankha.govtjobquest.ads.AppOpenAdsManager
import com.shankha.govtjobquest.ads.interstitialAds
import com.shankha.govtjobquest.databinding.ActivityMathBinding

class MathActivity : AppCompatActivity() {
    private val binding: ActivityMathBinding by lazy {
        ActivityMathBinding.inflate(layoutInflater)
    }
    val parents: String = "1X22MiuCjX9FSJs3a9_PI-5bbwKQjfXrotKCF-8J4r2o"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = " Mathematics"

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        MobileAds.initialize(this) {}

        interstitialAds(this)


        binding.numberSystem.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_NumberSystem")
            intent.putExtra("title","Number System")
            startActivity(intent)
        }
        binding.simplification.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Simplification")
            intent.putExtra("title","Simplification")
            startActivity(intent)
        }
        binding.lcmHcf.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_LCM_HCF")
            intent.putExtra("title","LCM and HCF")
            startActivity(intent)
        }
        binding.percentage.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Percentage")
            intent.putExtra("title","Percentage")
            startActivity(intent)
        }
        binding.profitLoss.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Profit_Loss")
            intent.putExtra("title","Profit and Loss")
            startActivity(intent)
        }
        binding.ratioProportion.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Ratio_Proportion")
            intent.putExtra("title","Ratio Proportion")
            startActivity(intent)
        }
        binding.workTime.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Work_Time")
            intent.putExtra("title","Work and Time")
            startActivity(intent)
        }
        binding.SCInterest.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_S_C_Interest")
            intent.putExtra("title","Simple and Compound Interest")
            startActivity(intent)
        }
        binding.speedTime.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_S_T_Distance")
            intent.putExtra("title","Speed Time and Distance")
            startActivity(intent)
        }
        binding.mensuration.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Mensuration")
            intent.putExtra("title","Mensuration")
            startActivity(intent)
        }
        binding.algebra.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Algebra")
            intent.putExtra("title","Algebra")
            startActivity(intent)
        }
        binding.trigonometry.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Trigonometry")
            intent.putExtra("title","Trigonometry")
            startActivity(intent)
        }
        binding.geometry.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Geometry")
            intent.putExtra("title","Geometry")
            startActivity(intent)
        }
        binding.miscellaneous.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "M_Miscellaneous")
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