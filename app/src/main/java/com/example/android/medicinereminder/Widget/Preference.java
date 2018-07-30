package com.example.android.medicinereminder.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.android.medicinereminder.Util.ReminderUtils;

import java.util.ArrayList;

public class Preference {

    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, ArrayList<String> reminders) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString("Reminder key", ReminderUtils.toBaseString(reminders));
        Log.v("recipeString", ReminderUtils.toBaseString(reminders));
        prefs.apply();
    }

    public static ArrayList<String> loadReminder(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Log.v("recipeStringload", ReminderUtils.toBaseString(ReminderUtils.fromBaseString(prefs.getString("Reminder key", ""))));
        return ReminderUtils.fromBaseString(prefs.getString("Reminder key", ""));
    }
}
