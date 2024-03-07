package com.shankha.govtjobquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.shankha.govtjobquest.ads.AppOpenAdsManager
import com.shankha.govtjobquest.databinding.ActivityMainBinding
import com.shankha.govtjobquest.subCatagory.GAwarenessActivity
import com.shankha.govtjobquest.subCatagory.GScienceActivity
import com.shankha.govtjobquest.subCatagory.MathActivity
import com.shankha.govtjobquest.subCatagory.ReasoningActivity

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_bar))

        val onBackPressedCallback =object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.bannerAd1.loadAd(adRequest)
        binding.bannerAd2.loadAd(adRequest)

        binding.GeneralScience.setOnClickListener {
            startActivity(Intent(this,GScienceActivity::class.java))
        }
        binding.GeneralAwareness.setOnClickListener {
            startActivity(Intent(this,GAwarenessActivity::class.java))
        }
        binding.Mathematics.setOnClickListener {
            startActivity(Intent(this,MathActivity::class.java))
        }
        binding.Reasoning.setOnClickListener {
            startActivity(Intent(this,ReasoningActivity::class.java))
        }

        val appOpenAdsManager = AppOpenAdsManager.getInstance(this)
        appOpenAdsManager.fetchAd()

    }

    override fun onPause() {
        binding.bannerAd1.pause()
        binding.bannerAd2.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.bannerAd1.resume()
        binding.bannerAd2.resume()
        val appOpenAdsManager = AppOpenAdsManager.getInstance(this)
        appOpenAdsManager.onResume(this)
    }

    override fun onDestroy() {
        binding.bannerAd1.destroy()
        binding.bannerAd2.destroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                FirebaseAuth.getInstance().signOut()

                val gso =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail().build()
                val googleSignInClient = GoogleSignIn.getClient(this,gso)
                googleSignInClient.signOut().addOnCompleteListener { task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Log Out Successfully",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Failed to LogOut!!  \n Please try again",Toast.LENGTH_SHORT).show()
                    }
                }

                startActivity(Intent(this,LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    }