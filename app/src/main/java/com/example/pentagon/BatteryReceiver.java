package com.example.pentagon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    private int percentage;

    @Override
    public void onReceive(Context context, Intent intent) {
        percentage = intent.getIntExtra("level", 0);
        if (percentage != 0) {
            //Toast.makeText(context, percentage+"%", Toast.LENGTH_SHORT).show();
        }
    }

    public int getPercentage() {
        return percentage;
    }
}
