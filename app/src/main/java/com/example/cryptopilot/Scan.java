package com.example.cryptopilot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.Manifest;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class Scan extends AppCompatActivity {

    private ImageView torch;
    private DecoratedBarcodeView dbv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ImageView back = (ImageView) findViewById(R.id.back_but);
        dbv = findViewById(R.id.scan_view);
        torch = (ImageView) findViewById(R.id.torch);

        final boolean[] torch_on = {false};

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 123);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
        final String status_txt = "Place the QR in the center of the View Finder to scan it.";
        dbv.setStatusText(status_txt);
        dbv.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

            }
        });
        torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (!torch_on[0]){
                 torch_on[0] = true;
                 torch.setBackground(getDrawable(R.drawable.torch_glow));
                 dbv.setTorchOn();
             }
             else{
                 torch_on[0] = false;
                 torch.setBackground(getDrawable(R.drawable.torch));
                 dbv.setTorchOff();
             }
            }
        });
    }
    @Override
    protected void onResume() {
        dbv = findViewById(R.id.scan_view);
        super.onResume();
        dbv.resume();
    }

    @Override
    protected void onPause() {
        dbv = findViewById(R.id.scan_view);
        super.onPause();
        dbv.pause();
    }
}