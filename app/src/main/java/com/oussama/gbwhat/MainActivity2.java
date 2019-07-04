package com.oussama.gbwhat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<String> countryCodes = new ArrayList<>();
    ArrayList<String> countryName = new ArrayList<>();
    String jsonCo;
    int i;
    private ProgressDialog pd;

    Spinner spinnerCountry;
    EditText editTextCode;
    EditText editTextPhone;
    Button btnNext;
    String textPhone;


    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        editTextCode = (EditText) findViewById(R.id.CodeCountry);
        btnNext=(Button)findViewById(R.id.btnNext);
        editTextPhone = (EditText)findViewById(R.id.numberPhone);

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        editTextPhone.requestFocus();

        getCountryCodes();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        i = spinnerCountry.getSelectedItemPosition();
        editTextCode.setText(countryCodes.get(i));

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                i = spinnerCountry.getSelectedItemPosition();
                editTextCode.setText(countryCodes.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                i = spinnerCountry.getSelectedItemPosition();
                editTextCode.setText(countryCodes.get(i));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textPhone = editTextPhone.getText().toString();

                if(valide()){
                    if(!isConnected(getApplicationContext())){
                       // buildDialog(getApplicationContext()).show();
                        Toast.makeText(MainActivity2.this, "You need to have Mobile Data or wifi to access this.", Toast.LENGTH_SHORT).show();
                     }else{
                         pd = new ProgressDialog(MainActivity2.this);
                         pd.setTitle("Send Message");
                         pd.setMessage("Please wait...");
                         pd.show();
                         Runnable progressRunnable = new Runnable() {

                             @Override
                             public void run() {

                                 try{
                                     Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                     Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),notification);
                                     r.play();

                                 }catch (Exception e){
                                     e.printStackTrace();
                                 }
                                 pd.cancel();

                                 Intent i = new Intent(MainActivity2.this,MainActivity3.class);
                                 startActivity(i);
                             }
                         };

                         Handler pdCanceller = new Handler();
                         pdCanceller.postDelayed(progressRunnable, 9000);
                     }
                }

            }
        });

    }


    public void getCountryCodes() {
        jsonCo = null;

        try {
            InputStream is = getApplicationContext().getAssets().open("CountryCodes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonCo = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonCo);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                countryName.add(obj.getString("name"));
                countryCodes.add(obj.getString("dial_code"));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public boolean valide(){

        boolean valid = true;
        if(textPhone.isEmpty()){
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
            valid =false;
        }else if(textPhone.length()<9 || textPhone.length()> 15){
            Toast.makeText(this, "The phone number is incorrect", Toast.LENGTH_SHORT).show();
            valid =false;
        }
        return valid;
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null &&  mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }

}
