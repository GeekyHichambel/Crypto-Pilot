package com.example.cryptopilot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private boolean Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(globalvar.PREFERENCES,MODE_PRIVATE);
                Switch = sharedPreferences.getBoolean(globalvar.SWITCH,false);
                if (Switch){
                    gotohome();
                    finish();
                }
                else {
                    gotowelcome();
                    finish();
                }
            }
        },2500);
    }
    public void gotowelcome(){
        Intent intent = new Intent(this,Welcomepage.class);
        startActivity(intent);
    }
    public void gotohome(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
}