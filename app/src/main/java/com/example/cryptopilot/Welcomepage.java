package com.example.cryptopilot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class Welcomepage extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static Activity state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = this;
        setContentView(R.layout.activity_welcomepage);

        TextView signin = findViewById(R.id.signintext);
        Button register = findViewById(R.id.buttonregister);
        VideoView videoView = findViewById(R.id.videoView);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.introvid;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0,0);
                videoView.start();

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        videoView.start();
                    }
                });
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkconnection()){
                    gotologin();
                }
                else {
                    neterror();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  (checkconnection()){
                    gotoregister();
                }
                else {
                    neterror();
                }
            }
        });
    }
    public void neterror(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.network_toast,(ViewGroup) findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public boolean checkconnection(){
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;

            if (manager != null){
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e){
            return false;
        }
    }
    public void gotologin(){
        Intent intent = new Intent(this,Loginpage.class);
        startActivity(intent);
    }

    public void gotoregister(){
        Intent intent = new Intent(this,Registerpage.class);
        startActivity(intent);
    }
}