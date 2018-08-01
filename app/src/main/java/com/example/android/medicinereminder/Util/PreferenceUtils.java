package com.example.android.medicinereminder.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.medicinereminder.R;

public class PreferenceUtils {

    public static final String KEY_ALARM_ID = "alarm-id";
    private static final int DEFAULT_COUNT = 0;
    private static final Boolean DEFAULT_STOCK_STATE = true;

    //sets alarm id
    synchronized private static void setAlarmId(Context context, int alarmId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_ALARM_ID, alarmId);
        editor.apply();
    }

    //gets alarm id
    public static int getAlarmId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int alarmId = prefs.getInt(KEY_ALARM_ID, DEFAULT_COUNT);
        return alarmId;
    }

    //increments alarm id
    synchronized public static void incrementAlarmId(Context context) {
        int alarmId = PreferenceUtils.getAlarmId(context);
        PreferenceUtils.setAlarmId(context, ++alarmId);
    }

    //gets stock reminder state
    public static Boolean getStockReminderState(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean stockState = prefs.getBoolean(context.getString(R.string.stock_key),false);
        return stockState;
    }




}
