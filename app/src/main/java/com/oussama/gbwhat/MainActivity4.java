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

public class MainActivity4 extends AppCompatActivity {

    Button btnNext4;
    private ProgressDialog pd;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        btnNext4=(Button)findViewById(R.id.btnNext4);

        mAdView = findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(MainActivity4.this);
                pd.setTitle("Verification");
                pd.setMessage("Verification in progress...");
                pd.show();
                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        pd.cancel();
                        Intent i = new Intent(MainActivity4.this,MainActivity5.class);
                        startActivity(i);
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2000);
            }
        });


    }

    @Override
    public void onBackPressed() {

    }
}
