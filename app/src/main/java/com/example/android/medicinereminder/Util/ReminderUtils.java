package com.example.android.medicinereminder.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */

public class ReminderUtils {
    public static String getDateString(long date){
        Date date1 = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date1);
    }
}
