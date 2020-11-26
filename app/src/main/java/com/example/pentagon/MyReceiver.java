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

    public TextInputLayout getMain_TXT_password() {
        return main_TXT_password;
    }

    public void setMain_TXT_password(TextInputLayout main_TXT_password) {
        this.main_TXT_password = main_TXT_password;
    }

    public boolean getInsertedBatteryOK() {
        this.insertedBatteryOK = (this.main_TXT_password.getEditText().getText().toString().trim()).contains(percentage + "");
        return this.insertedBatteryOK;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String actionString = intent.getAction();
        Toast.makeText(context, "actionString = " + actionString, Toast.LENGTH_LONG).show();
//        String timeZone = intent.getStringExtra("time-zone");
//        Log.d(TAG, "onReceive: "+timeZone);
        //throw new UnsupportedOperationException("Not yet implemented");
//        boolean isOn = intent.getBooleanExtra("state", false);
//        Log.d(TAG, "onReceive: Airplane mode is on:" + isOn);
//        PendingResult pendingResult = goAsync();
//        Log.d(TAG, "onReceive: BOOT Action");
//        new Task(pendingResult, intent).execute();

        this.percentage = intent.getIntExtra("level", 0);
        this.insertedBatteryOK = (this.main_TXT_password.getEditText().getText().toString().trim()).contains(percentage + "");

    }
}