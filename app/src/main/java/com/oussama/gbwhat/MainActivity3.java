package com.oussama.gbwhat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class MainActivity3 extends AppCompatActivity {

    EditText numberVerify;
    Button btnNext3;
    private ProgressDialog pd;

    private InterstitialAd mInterstitialAd;
    InterstitialAds interstitialAds = new InterstitialAds(mInterstitialAd);
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        numberVerify=(EditText)findViewById(R.id.numberVerify);
        btnNext3=(Button)findViewById(R.id.btnNext3);

        numberVerify.setEnabled(false);

        interstitialAds.LoadAds(this);
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Random rand = new Random();
        int value = rand.nextInt(500 - 100);
        int value2 = rand.nextInt(900 - 500);

        if(value<=100){
              value = value*10+1;

        }if (value2<=100){
            value2 = value2*10+2;
        }
        numberVerify.setText(value + "-" +value2);

        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(MainActivity3.this);
                pd.setTitle("Continuing");
                pd.setMessage("Please wait...");
                pd.show();
                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        pd.cancel();
                        Intent i = new Intent(MainActivity3.this,MainActivity4.class);
                        startActivity(i);
                        interstitialAds.showAds(MainActivity3.this);
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 5000);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
