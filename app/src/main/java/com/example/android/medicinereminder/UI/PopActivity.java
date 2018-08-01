package com.example.android.medicinereminder.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.Notifications.AlarmNotificationReceiver;
import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.PreferenceUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PopActivity extends Activity {
    static int stock;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Fade());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);



        context = this;

        //resizing activity
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.7), (int)(height * 0.4));

        Button saveButton = findViewById(R.id.save);
        Button cancelButton = findViewById(R.id.cancel);
        RadioButton done = findViewById(R.id.medicine_taken);
        RadioButton notDone = findViewById(R.id.medicine_no);
        RadioButton none = findViewById(R.id.none);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        if(getIntent() != null){
            if(getIntent().hasExtra("reminderKey") && getIntent().hasExtra("reminderId") && getIntent().hasExtra("reminderMed")){
                String key = getIntent().getStringExtra("reminderKey");
                int reminderId = getIntent().getIntExtra("reminderId", 0);
                String reminderMed = getIntent().getStringExtra("reminderMed");

                //getting reminder from database
                Cursor reminderCursor = getContentResolver().query(ReminderEntry.CONTENT_URI, null, ReminderEntry._ID + "=" +reminderId, null, null);

                if(reminderCursor!=null){
                    reminderCursor.moveToFirst();
                    stock = reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.STOCK));
                    reminderCursor.close();
                }
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(done.isChecked()){
                            databaseReference.child("reminders").child(key).child("state").setValue(2);
                            stock = stock-1;
                            //Checking if stock reminder preference is on
                            if(PreferenceUtils.getStockReminderState(context)){
                                if(stock == 0){
                                    setStockReminderNotification(reminderMed);

                                }else {
                                    updateStock(stock);
                                }

                            }else {
                                if(stock!=0){
                                    updateStock(stock);
                                }
                            }

                        }else if(notDone.isChecked()){
                            databaseReference.child("reminders").child(key).child("state").setValue(3);
                        }else if (none.isChecked()){
                            databaseReference.child("reminders").child(key).child("state").setValue(1);
                        }
                        finish();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }


    }

    //Update stock
    public void updateStock(int stockValue){
        ContentValues values = new ContentValues();
        values.put(ReminderEntry.STOCK, stockValue);
        getContentResolver().update(ReminderEntry.CONTENT_URI, values, null, null);
    }

    //Stock reminder
    public void setStockReminderNotification(String medName){
        Intent alarmIntent = new Intent(context, AlarmNotificationReceiver.class);
        alarmIntent.putExtra("message", medName+ " stock over");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1500, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 2);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds

    }
}
