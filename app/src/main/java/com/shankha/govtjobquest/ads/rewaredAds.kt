package com.shankha.govtjobquest.ads

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.shankha.govtjobquest.R
/*
class RewardedAdsManager(private val activity: Activity) {

    fun loadRewardedAd() {
        val adId = activity.resources.getString(R.string.rewards_ads_id_test)
        RewardedAd.load(activity, adId,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    // Handle ad load failure if needed
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    super.onAdLoaded(rewardedAd)
                    rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            // Ad dismissed callback
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            // Ad failed to show callback
                        }
                    }
                    rewardedAd.show(activity, object : OnUserEarnedRewardListener {
                        override fun onUserEarnedReward(p0: RewardItem) {
                            Toast.makeText(activity, "Let's Start The Quiz", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        )
    }
}

 */

class RewardedAdsManager(private val activity: Activity) {

    private var rewardedAd: RewardedAd? = null
    private var isAdLoaded = false
    init {
        loadRewardedAd()
    }

    // Function to load the rewarded ad
    private fun loadRewardedAd() {
        RewardedAd.load(activity, "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    // Handle ad load failure if needed
                    isAdLoaded = false
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    super.onAdLoaded(ad)
                    rewardedAd = ad
                    isAdLoaded = true
                }
            }
        )
    }

    // Function to show the rewarded ad
    fun showRewardedAd() {
        if (isAdLoaded) {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    // Ad dismissed callback
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    // Ad failed to show callback
                }
            }
            rewardedAd?.show(activity, object : OnUserEarnedRewardListener {
                override fun onUserEarnedReward(p0: RewardItem) {
                    Toast.makeText(activity, "Let's Start The Quiz", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            loadRewardedAd()
        }
    }
}

