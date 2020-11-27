package com.example.pentagon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;

public class Validator {

    // Variables for phone's brightness case.
    private static int currentBrightLight;
    private static final int MAX_BRIGHTNESS = 255;

    /**
     * Checks that user's password contains:
     * 1. Capital & non capital letters
     * 2. checkBrightnessOfScreen()
     * 3. contactExists()
     * @param context
     * @param pwd
     * @return - True - password & Brightness & Avraham Rada's phone number are OK / False - not
     */
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

    /**
     * Checks if number: 0544292888 is saved in the phone
     * @param context
     * @param number - 0544292888 (Avraham Rada's phone number)
     * @return True - avraham rada's phone is saved in your phone / False - Avraham Rada's phone is not saved
     */
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

    /**
     * Checks if the phone is on MAX brightness 100%
     * @param context
     * @return True - MAX brightness / False - not on MAX brightness
     */
    private static boolean checkBrightnessOfScreen(Context context) {
        try {
            currentBrightLight = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        // If brightness is on the min value it will continue.
        if (currentBrightLight == MAX_BRIGHTNESS)
            return true;
        else
            return false;
    }

    /**
     * Checks if 'Netflix' app is installed in the phone
     * @param myPackageManager
     * @param appName
     * @return True - Netflix is installed / False - not installed.
     */
    public static boolean isAppInstalled(PackageManager myPackageManager, String appName) {
        try {
            myPackageManager.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
