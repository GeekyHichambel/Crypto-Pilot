package com.example.cryptopilot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView text,button;
    EditText email;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        text = (TextView) findViewById(R.id.guidance);
        button = (TextView) findViewById(R.id.sendbutton);
        email = (EditText) findViewById(R.id.editemailbox);

        SpannableString str = new SpannableString(getString(R.string.guidance));
        ForegroundColorSpan cyan = new ForegroundColorSpan(Color.CYAN);

        str.setSpan(cyan,53,65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(str);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();

                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Forgot.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent");
                            button.setEnabled(false);
                        }
                    }
                });
            }
        });
    }
    public void backclick(View view){
        Intent intent = new Intent(this,Loginpage.class);
        startActivity(intent);
        finish();
    }
}