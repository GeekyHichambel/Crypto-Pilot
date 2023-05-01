package com.example.cryptopilot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Myinfo extends AppCompatActivity {

    private ImageView back_button;
    private ImageSwitcher pfp;
    private TextView quotes_txt;
    private String[] quotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        back_button = findViewById(R.id.back_but);
        pfp = (ImageSwitcher) findViewById(R.id.profilepic);
        quotes_txt = (TextView) findViewById(R.id.quote);

        quotes = getResources().getStringArray(R.array.Quote_List);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockspin);

        pfp.startAnimation(animation);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                String quote = quotes[random.nextInt(quotes.length)];
                SpannableString spannableString = new SpannableString(quote);
                ForegroundColorSpan cyan1 = new ForegroundColorSpan(Color.WHITE);
                ForegroundColorSpan cyan2 = new ForegroundColorSpan(Color.WHITE);

                spannableString.setSpan(cyan1,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(cyan2,spannableString.length()-1,spannableString.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        quotes_txt.setText(spannableString);
                    }
                });
            }
        },0,5000);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}