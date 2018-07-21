package com.example.android.medicinereminder.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;

/**
 * This class creates a database.
 */

public class ReminderDbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "reminder.db";
    public static int DATABASE_VERSION = 1;

    public ReminderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_REMINDER_TABLE = "CREATE TABLE " + ReminderEntry.TABLE_NAME + "("
                + ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReminderEntry.MEDICINE_NAME + " TEXT NOT NULL, "
                + ReminderEntry.TYPE + " INTEGER NOT NULL, "
                + ReminderEntry.COLOR + " INTEGER NOT NULL, "
                + ReminderEntry.SHAPE + " INTEGER NOT NULL, "
                + ReminderEntry.STOCK + " INTEGER NOT NULL, "
                + ReminderEntry.DOSAGE + " INTEGER NOT NULL, "
                + ReminderEntry.MEASURE + " INTEGER NOT NULL, "
                + ReminderEntry.TIMESADAY + " INTEGER NOT NULL, "
                + ReminderEntry.NOTES + " TEXT, "
                + ReminderEntry.FROM_DATE + " INTEGER NOT NULL, "
                + ReminderEntry.TO_DATE + " INTEGER NOT NULL, "
                + ReminderEntry.REMINDER_TIME + " TEXT NOT NULL );";


        db.execSQL(SQL_CREATE_REMINDER_TABLE);

        Log.v("databasequery", SQL_CREATE_REMINDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
