package com.example.cryptopilot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class Loginpage extends AppCompatActivity{
    private GoogleSignInClient gsc;
    private FirebaseAuth auth;

    private CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        EditText email = findViewById(R.id.editemailbox);
        EditText password = findViewById(R.id.editpasswordbox);
        TextView forgot = findViewById(R.id.forgot_text);
        TextView create = findViewById(R.id.create_text);
        Button signin = findViewById(R.id.editsigninbutton);
        remember = findViewById(R.id.emailcheckbox);
        ImageView google = findViewById(R.id.googlesign);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions goptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,goptions);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = email.getText().toString();
                globalvar.emailchange = temp.length() > 0;
                checkif(signin);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = password.getText().toString();
                globalvar.passchange = temp.length()>0;
                checkif(signin);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoforgot();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregister();
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent,69);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = auth.getCurrentUser();
                        globalvar.user_mail = mail;
                        globalvar.user_pass = pass;
                        if (task.isSuccessful()){
                            assert user != null;
                            if (!user.isEmailVerified()){
                                Toast.makeText(Loginpage.this,"You are not verified",Toast.LENGTH_SHORT).show();
                                plsverify();
                            }
                            else{
                                if (remember.isChecked()){
                                    savestate(true);
                                }
                                Welcomepage.state.finish();
                                Toast.makeText(Loginpage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                gotoHome();
                                finish();
                            }
                        }
                        else{
                                Toast.makeText(Loginpage.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 69) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            if (!user.isEmailVerified()){
                                Toast.makeText(Loginpage.this,"You are not verified",Toast.LENGTH_SHORT).show();
                                plsverify();
                            }
                            else{
                                if (remember.isChecked()){
                                    savestate(true);
                                }
                                Welcomepage.state.finish();
                                Toast.makeText(Loginpage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                gotoHome();
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(Loginpage.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void gotoforgot(){
        Intent intent = new Intent(this,Forgot.class);
        startActivity(intent);
    }
    public void savestate(boolean state){
        SharedPreferences sharedPreferences = getSharedPreferences(globalvar.PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(globalvar.SWITCH,state);
        editor.apply();
    }
    public void plsverify(){
        Intent intent = new Intent(this,Verificationscreen.class);
        startActivity(intent);
        finish();
    }
    public void backclick(View view){
        Intent intent = new Intent(this,Welcomepage.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("ResourceAsColor")
    public void checkif(Button signin){

        if (globalvar.emailchange && globalvar.passchange){
            signin.setBackgroundColor(getResources().getColor(R.color.cyan));
            signin.setEnabled(true);
        }
        else{
            signin.setBackgroundColor(getResources().getColor(R.color.fadecyan));
            signin.setEnabled(false);
        }
    }
    public void gotoHome(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
    public void gotoregister(){
        Intent intent = new Intent(this,Registerpage.class);
        startActivity(intent);
    }
}
