package com.oussama.gbwhat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity5 extends AppCompatActivity {

    Button btnNext5;
    private InterstitialAd mInterstitialAd;
    InterstitialAds interstitialAds = new InterstitialAds(mInterstitialAd);
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        btnNext5=(Button)findViewById(R.id.btnNext5);

        interstitialAds.LoadAds(this);
        mAdView = findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnNext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity5.this,MainActivity6.class);
                startActivity(i);
                interstitialAds.showAds(MainActivity5.this);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
