package com.shankha.govtjobquest.subCatagory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.shankha.govtjobquest.QuizActivity
import com.shankha.govtjobquest.R
import com.shankha.govtjobquest.ads.AppOpenAdsManager
import com.shankha.govtjobquest.ads.interstitialAds
import com.shankha.govtjobquest.databinding.ActivityReasoningBinding

class ReasoningActivity : AppCompatActivity() {
    private val binding: ActivityReasoningBinding by lazy {
        ActivityReasoningBinding.inflate(layoutInflater)
    }
    val parents: String = "1b0gWnNsJSX2PMiDctoAnzP_-5L5qheBox0_8wKgZr80"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = " Reasoning"

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        MobileAds.initialize(this) {}

        interstitialAds(this)

        binding.analogy.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Analogy")
            intent.putExtra("title","Analogy")
            startActivity(intent)

        }
        binding.classification.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Classification")
            intent.putExtra("title","Classification")
            startActivity(intent)

        }
        binding.series.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Series")
            intent.putExtra("title","Series")
            startActivity(intent)

        }
        binding.bloodRelation.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_BloodRelation")
            intent.putExtra("title","Blood Relation")
            startActivity(intent)

        }
        binding.arithmeticalOperetion.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Arithemetical")
            intent.putExtra("title","Arithmetical Operation")
            startActivity(intent)

        }
        binding.syllogism.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Syllogism")
            intent.putExtra("title","Syllogism")
            startActivity(intent)

        }
        binding.dataSufficiency.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_DataSufficiency")
            intent.putExtra("title","Data Sufficiency")
            startActivity(intent)

        }
        binding.sequence.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Sequence")
            intent.putExtra("title","Sequence")
            startActivity(intent)

        }
        binding.clockCalander.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Clock_Calander")
            intent.putExtra("title","Clock and Calender")
            startActivity(intent)

        }
        binding.miscellaneous.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("parent", parents)
            intent.putExtra("children", "R_Miscellaneous")
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