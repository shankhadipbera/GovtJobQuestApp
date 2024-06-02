package com.shankha.govtjobquest.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.concurrent.TimeUnit


class AppOpenAdsManager private constructor(private val context: Context) {
    private var appOpenAd: AppOpenAd? = null
    private var isLoading = false
    private var isShowing = false
    private val adUnitId = "ca-app-pub-3940256099942544/9257395921" // Replace with your ad unit ID
    private val adRequest: AdRequest = AdRequest.Builder().build()
    private val adRefreshIntervalMillis = TimeUnit.MINUTES.toMillis(1) // Ads every 3 minutes
    private var appStartTimeMillis: Long = 0

    companion object {
        private var instance: AppOpenAdsManager? = null

        fun getInstance(context: Context): AppOpenAdsManager {
            return instance ?: synchronized(this) {
                val newInstance = AppOpenAdsManager(context)
                instance = newInstance
                newInstance
            }
        }
    }

    init {
        appStartTimeMillis = System.currentTimeMillis() // Record the start time of the app
    }

    fun fetchAd() {
        if (isLoading || isAdAvailable() || !shouldLoadAd()) {
            return
        }
        isLoading = true
        AppOpenAd.load(context, adUnitId, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                super.onAdLoaded(ad)
                appOpenAd = ad
                isLoading = false
                appStartTimeMillis = System.currentTimeMillis()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                isLoading = false
                Log.e("AppOpenAdsManager", "Ad failed to load: ${loadAdError.message}")
            }
        })
    }

    private fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }

    private fun shouldLoadAd(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val timeSinceAppStart = currentTimeMillis - appStartTimeMillis
        return timeSinceAppStart >= adRefreshIntervalMillis
    }

    fun showAdIfAvailable(activity: Activity) {
        if (isShowing || !isAdAvailable()) {
            return
        }

        val fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                isShowing = false
                fetchAd() // Load a new ad after dismissing the current one
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                super.onAdFailedToShowFullScreenContent(adError)
                Log.e("AppOpenAdsManager", "Ad failed to show fullscreen content: ${adError.message}")
                fetchAd() // Load a new ad after failing to show the current one
            }
        }

        appOpenAd?.fullScreenContentCallback = fullScreenContentCallback
        appOpenAd?.show(activity)
        isShowing = true
    }

    fun onResume(activity: Activity) {
        fetchAd() // Fetch ad when activity is resumed
        showAdIfAvailable(activity) // Show ad if available when activity is resumed
    }
}



/*


class AppOpenAdsManager private constructor(private val context: Context) {
    private var appOpenAd: AppOpenAd? = null
    private var isLoading = false
    private var isShowing = false
    private val adUnitId = "ca-app-pub-3940256099942544/3419835294" // Replace with your ad unit ID
    private val adRequest: AdRequest = AdRequest.Builder().build()
    private var lastAdShowTimeMillis: Long = 0
    private val adRefreshIntervalMillis = TimeUnit.MINUTES.toMillis(1) // Ads every 5 minutes

    companion object {
        private var instance: AppOpenAdsManager? = null

        fun getInstance(context: Context): AppOpenAdsManager {
            return instance ?: synchronized(this) {
                val newInstance = AppOpenAdsManager(context)
                instance = newInstance
                newInstance
            }
        }
    }

    fun showAdIfAvailable(activity: Activity) {
        val currentTimeMillis = System.currentTimeMillis()
        val timeSinceLastAd = currentTimeMillis - lastAdShowTimeMillis

        if (!isShowing && (appOpenAd != null || timeSinceLastAd >= adRefreshIntervalMillis)) {
            appOpenAd?.let {
                val fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        isShowing = false
                        fetchAd() // Load a new ad after dismissing the current one
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)
                        Log.e("AppOpenAdsManager", "Ad failed to show fullscreen content: ${adError.message}")
                        fetchAd() // Load a new ad after failing to show the current one
                    }
                }

                it.fullScreenContentCallback = fullScreenContentCallback
                it.show(activity)
                isShowing = true
            } ?: run {
                fetchAd()
            }
        }
    }

    private fun fetchAd() {
        if (isLoading) {
            return
        }
        isLoading = true

        AppOpenAd.load(context, adUnitId, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                super.onAdLoaded(ad)
                appOpenAd = ad
                isLoading = false
                lastAdShowTimeMillis = System.currentTimeMillis() // Record the time when the ad was loaded
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                isLoading = false
                Log.e("AppOpenAdsManager", "Ad failed to load: ${loadAdError.message}")
            }
        })
    }
}


 */


