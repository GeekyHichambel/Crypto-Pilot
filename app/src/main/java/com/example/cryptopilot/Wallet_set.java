package com.example.cryptopilot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Wallet_set extends AppCompatActivity {

    private ImageView back_button;
    private ImageView qr;
    private TextView address;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private ImageButton button;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_set);

        back_button = findViewById(R.id.back_but);
        address = findViewById(R.id.address);
        qr = findViewById(R.id.qr_img);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        button = findViewById(R.id.copybutton);
        progressBar = findViewById(R.id.qr_prog);

        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ethRef = db.getReference("users");
        DatabaseReference userEthRef = ethRef.child(user.getUid());

        userEthRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String address_txt = snapshot.getValue(String.class);
                address.setText(address_txt);
                gen_qr(address_txt);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //nothing
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData CD = ClipData.newPlainText("text",address.getText());
                cm.setPrimaryClip(CD);
                Toast.makeText(getApplicationContext(),"Address Copied Successfully!",Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_left, R.anim.out_right);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left, R.anim.out_right);
    }
    public void gen_qr(String add){
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(add, BarcodeFormat.QR_CODE,300,300);
            qr.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}