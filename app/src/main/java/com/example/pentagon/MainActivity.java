package com.example.pentagon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alon.customtoast.CustomToast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import static com.fullpagedeveloper.toastegg.ToastEggKt.toastOrEgg;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout main_TXT_password;
    private MaterialButton main_BTN_enter;
    private PackageManager myPackageManager;
    private final String packageNameToCheck = "com.netflix.mediaclient"; // ID of 'Netflix' - app u must have installed in your phone!
    private MyReceiver myReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Permissions of WRITE(not used for now) and READ
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);
        // Initialize variables
        init();
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
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(myReceiver);
    }

    /**
     * init() initialize all variables
     *
     */
    private void init() {
        main_TXT_password = (TextInputLayout) findViewById(R.id.main_TXT_password);
        main_BTN_enter = (MaterialButton) findViewById(R.id.main_BTN_enter);
        myPackageManager = getPackageManager();
        myReceiver = new MyReceiver(this.main_TXT_password);
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
                    AlonToast("Battery"); // Custom toast message of @alonlubinski
                }
            } else {
                AlonToast("'Netflix'"); // Custom toast message of @alonlubinski
            }
        } else {
            AlonToast("Letters/Brightness/AvrahamRada"); // Custom toast message of @alonlubinski
        }
    }

    private void AlonToast(String message) {
        CustomToast.init()
                .setContext(this)
                .setMessage(message)
                .setDuration(CustomToast.LENGTH_LONG)
                .setBackgroundColor(Color.RED)
                .setCornerRadius(50)
                .setTextColor(Color.WHITE)
                .setTextSize(16)
                .setLeftIcon(getResources().getDrawable(R.drawable.padlock))
                .setBackgroundBlink(Color.WHITE, 300)
                .buildToast()
                .show();
    }

    /**
     * Func that checks:
     * 1. User's password contains: Capital & non capital letters
     * 2. Brightness of the phone is 100%
     * 3. Avraham Rada's phone number is on your phone (0544292888)
     *
     * @param userPassword - password that the user insert.
     * @return - True - password & Brightness & Avraham Rada's phone number are OK / False - not
     */
    private boolean checkValidation(String userPassword) {
        return Validator.isValidPassword(this, userPassword);
    }
}
