package com.example.android.medicinereminder.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */

public class ReminderUtils {
    //returns date as string
    public static String getDateString(long date){
        Date date1 = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date1);
    }

    //returns time as string
    public static String getTimeString(long date){
        Date date1 = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(date1);
    }

    //converts arraylist to string
    public static String toBaseString(ArrayList<String> reminders) {
        Log.v("testgson", reminders.size()+"");
        Gson gson = new Gson();
        return gson.toJson(reminders);
    }

    //converts string to arraylist
    public static ArrayList<String> fromBaseString(String encoded) {
        if (!"".equals(encoded)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> reminders= gson.fromJson(encoded, type);
            Log.v("testgson", reminders.size()+"");
            return gson.fromJson(encoded, type);

        }
        return null;
    }
}
