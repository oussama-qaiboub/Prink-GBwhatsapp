package com.oussama.gbwhat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MainActivity6 extends AppCompatActivity {

    ImageView ImgUserPhoto, emojPhoto;
    static int PReqCode = 1 ;
    View rootView;
    Uri pickedImgUri ;
    Button btnNext6;

    private InterstitialAd mInterstitialAd;
    InterstitialAds interstitialAds = new InterstitialAds(mInterstitialAd);
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        ImgUserPhoto = findViewById(R.id.regUserPhoto) ;
        btnNext6 = (Button)findViewById(R.id.btnNext6);

        interstitialAds.LoadAds(this);
        mAdView = findViewById(R.id.adView6);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btnNext6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                    finish();
                }else {
                    Toast.makeText(MainActivity6.this, "Error! Check out your internet", Toast.LENGTH_SHORT).show();
                    interstitialAds.showAds(MainActivity6.this);
                    finish();
                }
            }
        });

       /* emojPhoto = findViewById(R.id.emojPhoto) ;
        rootView = findViewById(R.id.rootView);
       @SuppressLint("WrongViewCast") EmojiconEditText emojiconEditText = (EmojiconEditText) findViewById(R.id.txtName);
        EmojIconActions emojIcon = new EmojIconActions(MainActivity6.this, rootView , emojiconEditText, emojPhoto);

        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard","open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard","close");
            }
        });*/

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Build.VERSION_CODES.M = 23 is marshmallow version
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                    interstitialAds.showAds(MainActivity6.this);

                }

            }
        });

    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(MainActivity6.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity6.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(MainActivity6.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(MainActivity6.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,PReqCode);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PReqCode && data != null) {
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }
    }
}
