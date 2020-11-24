package com.example.pentagon;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
    private static final int Darkness = 0;

    public static boolean isValidPassword(Context context, String pwd) {

        // Check if pwd contain at least one Capital and on non-capital letter
        if (pwd.matches(".*[A-Z].*") && pwd.matches(".*[a-z].*") && checkBrightnessOfScreen(context)) {
            if (contactExists(context, "0544292888")) {
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
            if (c != null){
                if(c.moveToFirst()){
                    stringContactName = c.getString(0);
                }
            }
            Log.d("pttt","your name is:"+stringContactName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;


//        /// number is the phone number
//        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
//        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
//        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
//        try {
//            if (cur.moveToFirst()) {
//                return true;
//            }
//        } finally {
//            if (cur != null)
//                cur.close();
//        }
//        return false;

        // -------------------------------------------------------------------------------------------

//        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
//        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
//        Cursor mycursor=resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, mPhoneNumberProjection, null, null, null);
//        if (mycursor!=null && mycursor.moveToFirst()) {
//            // record exists
//        }
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

}
