package com.example.cryptopilot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Registerpage extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText email,password;
    TextView status;
    Button register;

    FirebaseFirestore store;
    String userid;
    FirebaseAuth auth;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        email = (EditText) findViewById(R.id.emailbox);
        password = (EditText) findViewById(R.id.passbox);
        status = (TextView) findViewById(R.id.pass_status);
        register = (Button) findViewById(R.id.registernow);
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        if (user!=null){
            if (user.isEmailVerified()){
                Toast.makeText(this, "You are already registered, Please sign in.", Toast.LENGTH_SHORT).show();
                gotosignin();
            }
        }

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = email.getText().toString();
                globalvar.emailok = temp.length()>0;
                checkset();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = password.getText().toString();
                globalvar.passok = temp.length()>0;
                int counter = statuscheck(temp);
                if (counter == 1){
                    status.setTextColor(getResources().getColor(R.color.orange));
                    status.setText("Weak");
                } else if (counter == 2) {
                    status.setTextColor(getResources().getColor(R.color.yellow));
                    status.setText("Good");
                } else if (counter == 3) {
                    status.setTextColor(getResources().getColor(R.color.green));
                    status.setText("Excellent");
                }
                checkset();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (checkconditions(pass)) {
                    auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fuser = auth.getCurrentUser();
                                assert fuser != null;
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Registerpage.this, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "On_failure: Email Not Sent" + e.getMessage());
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                                userid = auth.getCurrentUser().getUid();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference eth_reference = firebaseDatabase.getReference().child("ethereumAddresses");
                                DatabaseReference user_reference = firebaseDatabase.getReference().child("users");
                                Query query = eth_reference.limitToFirst(1);

                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                                        if (iterator.hasNext()) {
                                            String ethereumAddress = iterator.next().getKey();
                                            Map<String,Object> updates = new HashMap<>();
                                            updates.put(userid,ethereumAddress);
                                            user_reference.updateChildren(updates);
                                            assert ethereumAddress != null;
                                            DatabaseReference databaseReference = eth_reference.child(ethereumAddress);
                                            databaseReference.removeValue();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Null element",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w(TAG, "onCancelled", error.toException());
                                    }
                                });

                                DocumentReference documentReference = store.collection("user").document(userid);
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", mail);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "On_success: Profile creation successful" + userid);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "On_failure: " + e.toString());
                                    }
                                });
                                globalvar.user_mail = mail;
                                globalvar.user_pass = pass;
                                gotoverification();
                                finish();
                            } else {
                                Toast.makeText(Registerpage.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void gotoverification(){
        Intent intent = new Intent(this,Verificationscreen.class);
        startActivity(intent);
        finish();
    }
    public void checkset(){
        register.setEnabled(globalvar.passok && globalvar.emailok);
    }
    public int statuscheck(String temp){
        int count = 0;
        if (temp.length()>=8){
            count++;
        }
        if (passcasecheck(temp)){
            count++;
        }
        if (passdigcheck(temp)){
            count++;
        }
        return count;
    }
    public boolean checkconditions(String pass){
         if (pass.length()<8){
             password.setError("Password should be of at least 8 characters");
             return false;
         }
         if (!passcasecheck(pass)){
            password.setError("Password must contain uppercase and lowercase letters");
            return false;
         }
         if (!passdigcheck(pass)){
             password.setError("Password must contain at least one digit");
             return false;
         }
         return true;
    }
    public boolean passcasecheck(String pass){
        boolean flag_up = false;
        boolean flag_low = false;
        for (int i=0;i<pass.length();i++){
            if (pass.charAt(i)>96 && pass.charAt(i)<123){
                flag_low = true;
            } else if (pass.charAt(i)>64 && pass.charAt(i)<91) {
                flag_up = true;
            }
            if (flag_low && flag_up){
                break;
            }
        }
        return flag_up && flag_low;
    }
    public boolean passdigcheck(String pass){
        boolean flag = false;
        for (int i=0;i<pass.length();i++){
            if (pass.charAt(i)>47 && pass.charAt(i)<58){
                flag = true;
                break;
            }
        }
        return flag;
    }
    public void backclick(View view){
        Intent intent = new Intent(this,Welcomepage.class);
        startActivity(intent);
        finish();
    }

    public void gotosignin(){
        Intent intent = new Intent(this,Loginpage.class);
        startActivity(intent);
        finish();
    }
}