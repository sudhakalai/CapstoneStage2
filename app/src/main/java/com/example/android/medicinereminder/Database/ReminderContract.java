package com.example.android.medicinereminder.Database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This is the contract class
 */

public class ReminderContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.medicinereminder";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REMINDER = "reminder";

    public static final class ReminderEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_REMINDER)
                .build();

        public static final String TABLE_NAME = "reminders";
        public static final String MEDICINE_NAME = "medicine";
        public static final String TYPE = "type";
        public static final String COLOR = "color";
        public static final String SHAPE = "shape";
        public static final String STOCK = "stock";
        public static final String DOSAGE = "dosage";
        public static final String MEASURE = "measure";
        public static final String TIMESADAY = "timesaday";
        public static final String NOTES = "notes";
        public static final String FROM_DATE = "fromdate";
        public static final String TO_DATE = "todate";
        public static final String REMINDER_TIME = "remindertime";
        public static final String STATE = "state";
    }
}
