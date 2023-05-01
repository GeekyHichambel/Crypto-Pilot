package com.example.cryptopilot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class Verificationscreen extends AppCompatActivity {

    public static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationscreen);

        Button refresh = findViewById(R.id.refresh);
        Button resend = findViewById(R.id.resend);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        assert user != null;
        if (user.isEmailVerified()){
            gotoHome();
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockspin);
                refresh.startAnimation(animation);
                auth.signInWithEmailAndPassword(globalvar.user_mail,globalvar.user_pass);
                if (user.isEmailVerified()){
                    Toast.makeText(Verificationscreen.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                    gotoHome();
                }
                else{
                    Toast.makeText(Verificationscreen.this, "Verification Incomplete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Verificationscreen.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                        resend.setEnabled(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "On_failure: Email Not Sent" + e.getMessage());
                    }
                });
            }
        });
    }
    public void gotoHome(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }
}