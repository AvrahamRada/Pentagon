package com.example.pentagon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout main_TXT_password;
    private MaterialButton main_BTN_enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);

        init();
        main_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chekcValidation())
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "NOT OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean chekcValidation() {
        return Validator.isValidPassword(this, main_TXT_password.getEditText().getText().toString().trim());
    }

    /**
     * @param
     */
    private void init() {
        main_TXT_password = (TextInputLayout) findViewById(R.id.main_TXT_password);
        main_BTN_enter = (MaterialButton) findViewById(R.id.main_BTN_enter);
    }
}