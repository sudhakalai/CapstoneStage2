package com.example.android.medicinereminder.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;

/**
 * This is the content provider class
 */

public class ReminderProvider extends ContentProvider{

    private ReminderDbHelper mDbHelper;
    private static final int REMINDER_ID = 100;
    private static final int REMINDERS = 101;
    private static final int TIMES = 102;
    private static final int TIME_ID = 103;
    private static final String LOG_TAG = ReminderProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ReminderContract.CONTENT_AUTHORITY, ReminderContract.PATH_REMINDER, REMINDERS);
        sUriMatcher.addURI(ReminderContract.CONTENT_AUTHORITY, ReminderContract.PATH_REMINDER+ "/#", REMINDER_ID);
        sUriMatcher.addURI(ReminderContract.CONTENT_AUTHORITY, ReminderContract.PATH_TIME, TIMES);
        sUriMatcher.addURI(ReminderContract.CONTENT_AUTHORITY, ReminderContract.PATH_TIME+ "/#", TIME_ID);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new ReminderDbHelper(getContext());
        return true;
    }

    //Query the database
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){
            case REMINDERS:
                cursor = db.query(ReminderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case REMINDER_ID:
                selection = ReminderEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ReminderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TIMES:
                cursor = db.query(ReminderEntry.TIME_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            case TIME_ID:
                selection = ReminderEntry.TIME_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ReminderEntry.TIME_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    //insert reminder into database
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id;
        switch (match){
            case REMINDERS:
                id = db.insert(ReminderEntry.TABLE_NAME, null, values);

                if (id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, id);
            case TIMES:
                id = db.insert(ReminderEntry.TIME_TABLE_NAME, null, values);

                if (id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    //delete from database
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int rowsDeleted;

        switch (match){

            case REMINDERS:
                rowsDeleted = db.delete(ReminderEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDER_ID:
                selection = ReminderEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ReminderEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TIMES:
                rowsDeleted = db.delete(ReminderEntry.TIME_TABLE_NAME, selection, selectionArgs);
                break;
            case TIME_ID:
                selection = ReminderEntry.TIME_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ReminderEntry.TIME_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    //update database
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);

        switch (match){
            case REMINDERS:
                return updateReminder(uri, values, selection, selectionArgs);
            case REMINDER_ID:
                selection = ReminderEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateReminder(uri, values, selection, selectionArgs);
            case TIMES:
                return updateReminderTime(uri, values, selection, selectionArgs);
            case TIME_ID:
                selection = ReminderEntry.TIME_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateReminderTime(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);

        }
    }

    private int updateReminder(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        int rowsUpdated;

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        rowsUpdated = db.update(ReminderEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }

    private int updateReminderTime(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        int rowsUpdated;

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        rowsUpdated = db.update(ReminderEntry.TIME_TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }
}
