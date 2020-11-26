package com.example.pentagon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout main_TXT_password;
    private MaterialButton main_BTN_enter;
    private PackageManager myPackageManager;
    private final String packageNameToCheck = "com.netflix.mediaclient"; // ID of 'Netflix' - app u must have installed in your phone!
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permissions of WRITE(not used for now) and READ
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);
        // Initialize variables
        init();
        myReceiver = new MyReceiver(this.main_TXT_password);
        // Click on "Enter"
        main_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

//        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
//        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(myReceiver);
    }

    private void init() {
        main_TXT_password = (TextInputLayout) findViewById(R.id.main_TXT_password);
        main_BTN_enter = (MaterialButton) findViewById(R.id.main_BTN_enter);
        myPackageManager = getPackageManager();
    }

    /**
     * onClick() listener that triggers checkValidation(), isAppInstalled() and getInsertedBatteryOK()
     *
     */
    private void start() {
        myReceiver.setMain_TXT_password(this.main_TXT_password);
        String userPassword = main_TXT_password.getEditText().getText().toString().trim();
        if (checkValidation(userPassword)) {
            if (Validator.isAppInstalled(myPackageManager, packageNameToCheck)) {
                if (myReceiver.getInsertedBatteryOK()) {
                    Intent intent = new Intent(MainActivity.this, Success.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Battery ?", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "snapchat ?", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Letters ? Brihtness ?", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Func that checks:
     * 1. User's password contains: Capital & non capital letters
     * 2. Brightness of the phone is 100%
     * 3. Avraham Rada's phone number is on your phone (0544292888)
     *
     * @param userPassword - password that the user insert.
     * @return - boolean type that check if password & Brightness & Avraham Rada's phone number are OK
     */
    private boolean checkValidation(String userPassword) {
        return Validator.isValidPassword(this, userPassword);
    }
}
