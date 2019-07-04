package com.oussama.gbwhat;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class InterstitialAds {

    InterstitialAd mInterstitialAd;
    AdView mAdView;
    String idAds = "ca-app-pub-3940256099942544~3347511713";
    String inter = "ca-app-pub-3940256099942544/1033173712";

    public InterstitialAds(InterstitialAd mInterstitialAd) {
        this.mInterstitialAd = mInterstitialAd;
    }

    public void LoadAds(Context context){

        MobileAds.initialize(context,idAds);

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(inter);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        final InterstitialAd finalMInterstitialAd = mInterstitialAd;
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                finalMInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    public void showAds(Context context){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
           // Toast.makeText(context, "\"TAG\", \"The interstitial wasn't loaded yet.", Toast.LENGTH_SHORT).show();

        }
    }

    public void showBanner(Context context){
        MobileAds.initialize(context,idAds);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
}
