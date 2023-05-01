package com.example.cryptopilot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Home extends AppCompatActivity {
    private AppCompatSpinner usermenu;
    private ImageView asset1;
    private ImageView asset2;
    private ImageView asset3;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private BlurView blurView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usermenu = (AppCompatSpinner) findViewById(R.id.user_menu);
        asset1 = (ImageView) findViewById(R.id.assests1);
        asset2 = (ImageView) findViewById(R.id.assests2);
        asset3 = (ImageView) findViewById(R.id.assests3);
        t1 = (TextView) findViewById(R.id.txt1);
        t2 = (TextView) findViewById(R.id.txt2);
        t3 = (TextView) findViewById(R.id.txt3);
        usermenu.setDropDownVerticalOffset(160);
        String[] empty = {""};


        ArrayAdapter<String> empty_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, empty);

        usermenu.setAdapter(empty_adapter);

        usermenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (usermenu.getSelectedItem().toString().equals("   Log Out")) {
                    logout();
                } else if (usermenu.getSelectedItem().toString().equals("   My Info")) {
                    Intent intent = new Intent(getApplicationContext(),Myinfo.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_down_anim);
        Animation anim_3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        asset1.setActivated(true);
        t1.setTextColor(ContextCompat.getColor(this,R.color.cyan));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view,new Assets());
        fragmentTransaction.replace(R.id.frame2,new Bal());
        fragmentTransaction.replace(R.id.frame1,new Asset_Frame());
        fragmentTransaction.commit();

        asset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if (asset1.isActivated()){
                    return;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view,new Assets());
                fragmentTransaction.replace(R.id.frame1,new Asset_Frame());
                if (!asset2.isActivated()){
                    fragmentTransaction.replace(R.id.frame2,new Bal());
                    flag = true;
                }
                activate(1);
                fragmentTransaction.commit();
                if (flag){
                    requireViewById(R.id.frame2).startAnimation(anim_3);
                }
                requireViewById(R.id.frame1).startAnimation(anim_3);
                requireViewById(R.id.fragment_view).startAnimation(anim);
            }
        });
        asset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if (asset2.isActivated()){
                    return;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view,new Trade());
                fragmentTransaction.replace(R.id.frame1,new Trade_Frame());
                if (!asset1.isActivated()){
                    fragmentTransaction.replace(R.id.frame2,new Bal());
                    flag = true;
                }
                    activate(2);
                fragmentTransaction.commit();
                    if (flag){
                        requireViewById(R.id.frame2).startAnimation(anim_3);
                    }
                    requireViewById(R.id.frame1).startAnimation(anim_3);
                    requireViewById(R.id.fragment_view).startAnimation(anim);
            }
        });
        asset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asset3.isActivated()){
                    return;
                }
                activate(3);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_view,new Settings());
                fragmentTransaction.replace(R.id.frame1,new Settings_Frame());
                fragmentTransaction.replace(R.id.frame2,new empty());
                fragmentTransaction.commit();
                requireViewById(R.id.frame1).startAnimation(anim_3);
                requireViewById(R.id.frame2).startAnimation(anim_3);
                requireViewById(R.id.fragment_view).startAnimation(anim);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        String[] empty = {""};
        String[] options = {"     Options","   My Info","   Log Out"};
        usermenu = (AppCompatSpinner) findViewById(R.id.user_menu);
        ArrayAdapter<String> empty_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, empty);
        ArrayAdapter<String> option_adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_dropdown_item, R.id.custom_text, options);

        super.onWindowFocusChanged(hasFocus);
        blurView = (BlurView) findViewById(R.id.blur_view);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        float radius = 1f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable win_back = decorView.getBackground();
        blurView.setupWith(rootView,new RenderScriptBlur(this)).setFrameClearDrawable(win_back).setBlurRadius(radius);
        if(hasFocus){
            blurView.setBlurEnabled(false);
            usermenu.setAdapter(empty_adapter);
        }
        else{
            blurView.setBlurEnabled(true);
            requireViewById(R.id.blur_view).startAnimation(animation);
            usermenu.setAdapter(option_adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkReceiver();
    }
    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }
    public void logout(){
            SharedPreferences sharedPreferences = getSharedPreferences(globalvar.PREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(globalvar.SWITCH,false);
            editor.apply();

            Intent intent = new Intent(this,Welcomepage.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

    private void unregisterNetworkReceiver() {
        unregisterReceiver(networkReceiver);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // There is an internet connection
                if (!globalvar.logged_in) {
                    Toast.makeText(Home.this, "Welcome User", Toast.LENGTH_SHORT).show();
                    globalvar.logged_in = true;
                }
            } else {
                // No internet connection
                netError();
            }
        }
    };

    private void netError() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.network_toast, (ViewGroup) findViewById(R.id.toast_root));

        Toast note = new Toast(getApplicationContext());
        note.setDuration(Toast.LENGTH_SHORT);
        note.setView(layout);
        note.show();
    }
    public void activate(int i) {
        if (i == 1){
            asset1.setActivated(true);
            t1.setTextColor(ContextCompat.getColor(this,R.color.cyan));
            asset2.setActivated(false);
            t2.setTextColor(ContextCompat.getColor(this,R.color.white));
            asset3.setActivated(false);
            t3.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
        else if (i == 2){
            asset1.setActivated(false);
            t1.setTextColor(ContextCompat.getColor(this,R.color.white));
            asset2.setActivated(true);
            t2.setTextColor(ContextCompat.getColor(this,R.color.cyan));
            asset3.setActivated(false);
            t3.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
        else{
            asset1.setActivated(false);
            t1.setTextColor(ContextCompat.getColor(this,R.color.white));
            asset2.setActivated(false);
            t2.setTextColor(ContextCompat.getColor(this,R.color.white));
            asset3.setActivated(true);
            t3.setTextColor(ContextCompat.getColor(this,R.color.cyan));
        }
    }
}