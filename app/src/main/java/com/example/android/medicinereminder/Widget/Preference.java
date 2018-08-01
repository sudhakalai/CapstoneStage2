package com.example.android.medicinereminder.Widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.medicinereminder.Util.ReminderUtils;

import java.util.ArrayList;

public class Preference {

    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, ArrayList<String> reminders) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString("Reminder key", ReminderUtils.toBaseString(reminders));
        prefs.apply();
    }

    public static ArrayList<String> loadReminder(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return ReminderUtils.fromBaseString(prefs.getString("Reminder key", ""));
    }
}
