package com.adsdemoby_ahsan.demo_admob_ads.nativetemplates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adsdemoby_ahsan.demo_admob_ads.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class LoadAdsActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    public static final String TAG = LoadAdsActivity.class.getName();
    String nextClassNameInString;
    String interstitialId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prepair_loading_ads);

        nextClassNameInString=getIntent().getStringExtra(ConstantAds.nextClassName);
        interstitialId=getIntent().getStringExtra(ConstantAds.interstitialid);


        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, interstitialId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        showInterstitialAd();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                        try {
                            nextActivity(nextClassNameInString);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                });
    }


    void showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(TAG, "Ad was clicked.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad dismissed fullscreen content.");
                    mInterstitialAd = null;
                    try {
                        nextActivity(nextClassNameInString);
                        finish();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    overridePendingTransition(0, 0);

                    // Finish the activity
//                    finishAffinity();

                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    // Called when ad fails to show.
                    Log.e(TAG, "Ad failed to show fullscreen content.");
                    mInterstitialAd = null;
                    try {
                        nextActivity(nextClassNameInString);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    finish();
                }

                @Override
                public void onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.");
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.");
                }
            });
            mInterstitialAd.show(LoadAdsActivity.this);

        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
    private void nextActivity(String nextactivity) throws ClassNotFoundException {

        Intent mIntent=new Intent(LoadAdsActivity.this, Class.forName(nextactivity));
        mIntent.putExtra("url",getIntent().getStringExtra("url"));
        mIntent.putExtra(ConstantAds.KeyTwo, getIntent().getIntExtra(ConstantAds.KeyTwo, 0));
        mIntent.putExtra(ConstantAds.KeyOne, getIntent().getStringExtra(ConstantAds.KeyOne));
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}