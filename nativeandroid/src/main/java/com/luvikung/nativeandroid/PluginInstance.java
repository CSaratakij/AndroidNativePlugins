package com.luvikung.nativeandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

public class PluginInstance {
    private static final String LOG_TAG = "UnityPlugins";
    private static final int VERSION = 7;

    public int getPluginVersion() {
        return VERSION;
    }

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private PluginCallback pluginCallback;

    public void setPluginCallback(PluginCallback callback) {
        pluginCallback = callback;
    }

    private PowerManager.WakeLock currentWakeLock;

    public void wakeLock(boolean isLock, int millisecond) {
        if (isLock) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (currentWakeLock == null) {
                    String tag = LOG_TAG + ":WakeLock";
                    PowerManager pm = activity.getSystemService(PowerManager.class);
                    currentWakeLock = pm.newWakeLock(PowerManager.ON_AFTER_RELEASE, tag);
                }
                currentWakeLock.acquire(millisecond > 0 ? millisecond : 10 * 60 * 1000L);
            } else {
                Log.e(LOG_TAG, "Wake Lock are supported in Android API level 23 or greater.");
            }
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            if (currentWakeLock != null)
                currentWakeLock.release();
        }
    }

    public void openApplicationSettings() {
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }

    public void openGooglePlayAccount() {
        Uri uri = Uri.parse("https://play.google.com/store/account");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }

    public void openGooglePlayAccountSubscription() {
        Uri uri = Uri.parse("https://play.google.com/store/account/subscriptions");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(intent);
    }

    public void alertDialog(String title, String message, boolean cancelable, String positive, String negative, String neutral) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme);
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(positive, (dialogInterface, i) ->
        {
            pluginCallback.OnAlertDialog(0);
            dialogInterface.cancel();
        });
        if (negative != null && !negative.isEmpty()) {
            builder.setNegativeButton(negative, (dialogInterface, i) ->
            {
                pluginCallback.OnAlertDialog(1);
                dialogInterface.cancel();
            });
        }
        if (neutral != null && !neutral.isEmpty()) {
            builder.setNeutralButton(neutral, (dialogInterface, i) ->
            {
                pluginCallback.OnAlertDialog(2);
                dialogInterface.cancel();
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void pickDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        pickDate(year, month, day);
    }

    public void pickDate(int year, int month, int day) {
        Log.i(LOG_TAG, "Pick Date");
        DatePickerDialog dialog = new DatePickerDialog(activity, R.style.Theme, (datePicker, selectedYear, selectedMonth, selectedDay) ->
                pluginCallback.OnPickDate(selectedYear, selectedMonth, selectedDay), year, month, day);
        dialog.show();
    }

    public void pickTime(boolean is24HourView) {
        Log.i(LOG_TAG, "Pick Time");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        pickTime(is24HourView, hour, minute);
    }

    public void pickTime(boolean is24HourView, int hour, int minute) {
        Log.i(LOG_TAG, "Pick Time");
        TimePickerDialog dialog = new TimePickerDialog(activity, R.style.Theme, (timePicker, selectedHour, selectedMinute) ->
                pluginCallback.OnPickTime(selectedHour, selectedMinute), hour, minute, is24HourView);
        dialog.show();
    }

    public void toast(String message) {
        Log.i(LOG_TAG, "Toast");
        Toast dialog = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        dialog.show();
    }
}
