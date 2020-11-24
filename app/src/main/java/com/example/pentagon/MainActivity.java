package com.example.pentagon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout main_TXT_password;
    private MaterialButton main_BTN_enter;
    private BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permissions of WRITE(not used for now) and READ
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);
        // Initialize variables
        init();
        // Click on "Enter"
        main_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chekcValidation()) {
                    batteryReceiver = new BatteryReceiver();
                    registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

                    if (checkBatteryValidator(batteryReceiver.getPercentage())) {
                        Intent intent = new Intent(MainActivity.this, Success.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Battery ?", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Letters ? Brihtness ?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkBatteryValidator(int percentage) {
        return Validator.isValidBattery(this, main_TXT_password.getEditText().getText().toString().trim(), percentage);
    }

    private boolean chekcValidation() {
        return Validator.isValidPassword(this, main_TXT_password.getEditText().getText().toString().trim());
    }

    private void init() {
        main_TXT_password = (TextInputLayout) findViewById(R.id.main_TXT_password);
        main_BTN_enter = (MaterialButton) findViewById(R.id.main_BTN_enter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryReceiver);
    }
}