package com.example.android.medicinereminder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.medicinereminder.Adapters.CategoryTabAdapter;
import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.Database.ReminderDbHelper;
import com.example.android.medicinereminder.UI.SettingsActivity;
import com.example.android.medicinereminder.Util.ReminderUtils;
import com.example.android.medicinereminder.Widget.ReminderWidgetService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.fab_reminder)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static ArrayList<String> reminderList = new ArrayList<>();
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        setSupportActionBar(toolbar);

        mContext = this;

        ReminderDbHelper dbHelper = new ReminderDbHelper(this);
        dbHelper.getWritableDatabase();

        CategoryTabAdapter adapter = new CategoryTabAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        new TodayMedicineListAsyncTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public class TodayMedicineListAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = getContentResolver().query(ReminderEntry.CONTENT_URI, null, null, null, null);
            Cursor timeCursor = getContentResolver().query(ReminderEntry.CONTENT_URI_TIME, null, null, null, null);
            cursor.moveToFirst();
            timeCursor.moveToFirst();
            SimpleDateFormat formatToday = new SimpleDateFormat("dd-MM-yyyy");
            String todayDate = formatToday.format(System.currentTimeMillis());
            try {
                Date today = formatToday.parse(todayDate);
                for (int i = 0; i < cursor.getCount(); i++) {
                    long fromDate = cursor.getLong(cursor.getColumnIndex(ReminderEntry.FROM_DATE));
                    long toDate = cursor.getLong(cursor.getColumnIndex(ReminderEntry.TO_DATE));
                    long reminder1 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_ONE));
                    long reminder2 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_TWO));
                    long reminder3 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_THREE));
                    long reminder4 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_FOUR));
                    long reminder5 = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_FIVE));

                    if (today.getTime() >= fromDate || today.getTime() <= toDate) {
                        if(reminder1 != 0){
                            reminderList.add(cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME)) + " " + ReminderUtils.getTimeString(reminder1));
                        }
                        if(reminder2 != 0){
                            reminderList.add(cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME)) + " " + ReminderUtils.getTimeString(reminder2));
                        }
                        if(reminder3 != 0){
                            reminderList.add(cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME)) + " " + ReminderUtils.getTimeString(reminder3));
                        }
                        if(reminder4 != 0){
                            reminderList.add(cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME)) + " " + ReminderUtils.getTimeString(reminder4));
                        }
                        if(reminder5 != 0){
                            reminderList.add(cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME)) + " " + ReminderUtils.getTimeString(reminder5));
                        }

                    }
                    cursor.moveToNext();
                    timeCursor.moveToNext();
                }
                cursor.close();
                timeCursor.close();
                ReminderWidgetService.updateWidget(mContext, reminderList);
                Log.v("testString", reminderList.size()+"");
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


}
