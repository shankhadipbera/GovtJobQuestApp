package com.shankha.govtjobquest.ads

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.shankha.govtjobquest.R

fun interstitialAds( activity : Activity) {
    val adId = activity.resources.getString(R.string.interstitialAd_id_test)

    InterstitialAd.load(
        activity,
        adId,
        AdRequest.Builder().build(),
        object :  InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                interstitialAd.show(activity)

            }
        }
    )
}