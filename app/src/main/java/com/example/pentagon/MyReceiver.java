package com.example.pentagon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

public class MyReceiver extends BroadcastReceiver {
    private TextInputLayout main_TXT_password;
    private boolean insertedBatteryOK = true;
    private int percentage;

    public MyReceiver() {
    }

    public MyReceiver(TextInputLayout main_TXT_password) {
        this.main_TXT_password = main_TXT_password;
    }

    public void setMain_TXT_password(TextInputLayout main_TXT_password) {
        this.main_TXT_password = main_TXT_password;
    }

    /**
     * return True/False if the percentage of teh battery is as inserted in the password by user
     * @return True - password includes the battery percentage, False - if not
     */
    public boolean getInsertedBatteryOK() {
        this.insertedBatteryOK = (this.main_TXT_password.getEditText().getText().toString().trim()).contains(percentage + "");
        return this.insertedBatteryOK;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        String actionString = intent.getAction();
//        Toast.makeText(context, "actionString = " + actionString, Toast.LENGTH_LONG).show();
        this.percentage = intent.getIntExtra("level", 0);
        this.insertedBatteryOK = (this.main_TXT_password.getEditText().getText().toString().trim()).contains(percentage + "");

    }
}