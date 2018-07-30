package com.example.android.medicinereminder.Util;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.Model.ReminderEdit;

public class ReminderLoader extends AsyncTaskLoader<ReminderEdit> {
    Uri mUri;
    Uri mtimeUri;
    Context mContext;
    ReminderEdit reminderEdit;

    public ReminderLoader(Context context, Uri uri, Uri timeUri) {
        super(context);
        mUri = uri;
        mtimeUri = timeUri;
        mContext = context;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ReminderEdit loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(mUri, null, null, null, null);
        Cursor timeCursor = getContext().getContentResolver().query(mtimeUri, null, null, null, null);
        if(cursor.getCount()!=0 && timeCursor.getCount()!=0) {
            cursor.moveToFirst();
            timeCursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME));
            String date = "From " + cursor.getLong(cursor.getColumnIndex(ReminderEntry.FROM_DATE)) + " to " + cursor.getLong(cursor.getColumnIndex(ReminderEntry.TO_DATE));
            int type = cursor.getInt(cursor.getColumnIndex(ReminderEntry.TYPE));
            int measure = cursor.getInt(cursor.getColumnIndex(ReminderEntry.MEASURE));
            int color = cursor.getInt(cursor.getColumnIndex(ReminderEntry.COLOR));
            int shape = cursor.getInt(cursor.getColumnIndex(ReminderEntry.SHAPE));
            String app= cursor.getInt(cursor.getColumnIndex(ReminderEntry.DOSAGE))+ "";
            if( measure != 6){
                app = app + FromintegerUtils.getMedicineMeasureString(measure, mContext);
            }
            if(color != 18){
                app = app +", "+ FromintegerUtils.getMedicineColorString(color, mContext);
            }
            if(shape != 7){
                app = app +", "+ FromintegerUtils.getMedicineShapeString(shape, mContext);
            }
            if(type != 10){
                app = app +", "+ FromintegerUtils.getMedicineTypeString(type, mContext);
            }

            String time = "";
            long time1 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_ONE));
            long time2 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_TWO));
            long time3 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_THREE));
            long time4 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_FOUR));
            long time5 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_FIVE));
            if(time1 != 0){
                time = time + time1;
            }
            if(time2 != 0){
                time = time +", "+ time2;
            }
            if(time3 != 0){
                time = time +", "+ time3;
            }
            if(time4 != 0){
                time = time+", "+ time4;
            }
            if(time5 != 0){
                time = time +", "+ time5 ;
            }

            reminderEdit = new ReminderEdit(name, app, date, time, cursor.getInt(cursor.getColumnIndex(ReminderEntry.STOCK)));
            return reminderEdit;
        }

        return null;
    }
}
