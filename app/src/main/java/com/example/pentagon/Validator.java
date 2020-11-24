package com.example.pentagon;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class Validator {

    // Variables for phone's brightness case.
    private static int currentBrigthLight;
    private static final int Darkness = 255;
    private static BatteryReceiver batteryReceiver = new BatteryReceiver();

    public static boolean isValidPassword(Context context, String pwd) {

        // Check if pwd contain at least one Capital and on non-capital letter
        if (pwd.matches(".*[A-Z].*") && pwd.matches(".*[a-z].*") && checkBrightnessOfScreen(context)) {
            if (contactExists(context, "0544292888") ||
                    contactExists(context, "+972544292888") ||
                    contactExists(context, "054-4292888") ||
                    contactExists(context, "+972-54-4292888")) {
                return true;

            }
            return false;
        } else
            return false;
    }

    public static boolean contactExists(Context context, String number) {
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            // Cursor - name will cupture in the cursor (now the data is here)
            Cursor c = context.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

            String stringContactName = "INVALID";
            if (c != null) {
                if (c.moveToFirst())
                    stringContactName = c.getString(0);
                else
                    return false; // Phone number of Avraham Rada does not exist in your phone (0544292888)
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static boolean checkBrightnessOfScreen(Context context) {
        try {
            currentBrigthLight = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        // If brightness is on the min value it will continue.
        if (currentBrigthLight == Darkness)
            return true;
        else
            return false;
    }

    public static boolean isValidBattery(Context context, String pwd, int percentage) {
        if (pwd.contains(Integer.toString(percentage)))
            return true;
        return false;
    }
}
