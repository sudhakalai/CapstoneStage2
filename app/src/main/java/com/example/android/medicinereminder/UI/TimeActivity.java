package com.example.android.medicinereminder.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.MainActivity;
import com.example.android.medicinereminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    String medName;
    int medType;
    int medColor;
    int medShape;
    int medStock;
    int medDosage;
    int medMeasure;
    int medTimes;
    String medNotes;
    static long medFromDate;
    static long medToDate;
    static String [] medReminders;
    ImageView fromPicker;
    static TextView fromDate;
    ImageView toPicker;
    static TextView toDate;
    ImageView firstReminder;
    static TextView firstTv;
    ImageView secondReminder;
    static TextView secondTv;
    ImageView thirdReminder;
    static TextView thirdTv;
    ImageView fourthReminder;
    static TextView fourthTv;
    ImageView fifthReminder;
    static TextView fifthTv;
    private DatePickerFragment mDatePickerFragment;
    private TimePickerFragment mTimePickerFragment;
    private int reminderTimesFlag = 5;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Setting up the back button in the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);

        RelativeLayout reminderOne = findViewById( R.id.reminder_one);
        RelativeLayout reminderTwo = findViewById( R.id.reminder_two);
        RelativeLayout reminderThree = findViewById( R.id.reminder_three);
        RelativeLayout reminderFour = findViewById( R.id.reminder_four);
        RelativeLayout reminderFive = findViewById( R.id.reminder_five);
        fromPicker = findViewById(R.id.from_date_picker);
        fromDate = findViewById(R.id.from_date);
        toPicker = findViewById(R.id.to_date_picker);
        toDate = findViewById(R.id.to_date);
        firstReminder = findViewById(R.id.first_button);
        firstTv = findViewById(R.id.first_reminder);
        secondReminder = findViewById(R.id.second_button);
        secondTv = findViewById(R.id.second_reminder);
        thirdReminder = findViewById(R.id.third_button);
        thirdTv = findViewById(R.id.third_reminder);
        fourthReminder = findViewById(R.id.fourth_button);
        fourthTv = findViewById(R.id.fourth_reminder);
        fifthReminder = findViewById(R.id.fifth_button);
        fifthTv = findViewById(R.id.fifth_reminder);
        mDatePickerFragment = new DatePickerFragment();
        mTimePickerFragment = new TimePickerFragment();
        medReminders = new String[5];
        context = this;


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            medName = extras.getString("medicineName");
            medType = extras.getInt("medicineType");
            medColor = extras.getInt("medicineColor");
            medShape = extras.getInt("medicineShape");
            medStock = extras.getInt("medicineStock");
            medDosage = extras.getInt("medicineDosage");
            medMeasure = extras.getInt("medicineMeasure");
            medTimes = extras.getInt("medicineTimes");
            medNotes = extras.getString("medicineNote");

            switch(medTimes){
                case 1:
                    reminderFive.setVisibility(View.GONE);
                    reminderFour.setVisibility(View.GONE);
                    reminderThree.setVisibility(View.GONE);
                    reminderTwo.setVisibility(View.GONE);
                    reminderTimesFlag = 1;
                    break;
                case 2:
                    reminderFive.setVisibility(View.GONE);
                    reminderFour.setVisibility(View.GONE);
                    reminderThree.setVisibility(View.GONE);
                    reminderTimesFlag = 2;
                    break;
                case 3:
                    reminderFive.setVisibility(View.GONE);
                    reminderFour.setVisibility(View.GONE);
                    reminderTimesFlag = 3;
                    break;
                case 4:
                    reminderFive.setVisibility(View.GONE);
                    reminderTimesFlag = 4;
                    break;
                default:
                    break;
            }
        }


        fromPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerFragment.setFlag(DatePickerFragment.FLAG_FROM_DATE);
                mDatePickerFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        toPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerFragment.setFlag(DatePickerFragment.FLAG_TO_DATE);
                mDatePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        firstReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerFragment.setFlag(TimePickerFragment.FLAG_FIRST_REMINDER);
                mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        secondReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerFragment.setFlag(TimePickerFragment.FLAG_SECOND_REMINDER);
                mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        thirdReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerFragment.setFlag(TimePickerFragment.FLAG_THIRD_REMINDER);
                mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        fourthReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerFragment.setFlag(TimePickerFragment.FLAG_FOURTH_REMINDER);
                mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        fifthReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickerFragment.setFlag(TimePickerFragment.FLAG_FIFTH_REMINDER);
                mTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete:
                return true;
            case R.id.action_close:
                return true;
            case R.id.action_save:
                if(validateInput()){
                    new InsertReminderAsyncTask().execute();
                    Toast.makeText(context, "Reminder saved!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, "One or more field are empty", Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public static final int FLAG_FROM_DATE = 0;
        public static final int FLAG_TO_DATE = 1;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar calendar = Calendar.getInstance();
            long today = calendar.getTimeInMillis();
            calendar.set(year, month, day);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            if (flag == FLAG_FROM_DATE) {
                if(calendar.getTimeInMillis()< today){
                    Toast.makeText(context, "From date should be not be less than today's date", Toast.LENGTH_LONG).show();
                }else {
                    fromDate.setText(format.format(calendar.getTime()));
                    medFromDate = calendar.getTimeInMillis();
                }
            } else if (flag == FLAG_TO_DATE) {
                if(calendar.getTimeInMillis()<= today){
                    Toast.makeText(context, "To date should be greater than today's date", Toast.LENGTH_LONG).show();
                }else if(calendar.getTimeInMillis()<medFromDate){
                    Toast.makeText(context, "To date should be greater than From date", Toast.LENGTH_LONG).show();
                }else {
                    toDate.setText(format.format(calendar.getTime()));
                    medToDate = calendar.getTimeInMillis();
                }
            }
        }
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        public static final int FLAG_FIRST_REMINDER = 0;
        public static final int FLAG_SECOND_REMINDER = 1;
        public static final int FLAG_THIRD_REMINDER = 2;
        public static final int FLAG_FOURTH_REMINDER = 3;
        public static final int FLAG_FIFTH_REMINDER = 4;

        long reminderOne, reminderTwo, reminderThree, reminderFour, reminderFive;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void setFlag(int i) {
            flag = i;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());

            if (flag == FLAG_FIRST_REMINDER) {
                reminderOne = calendar.getTimeInMillis();
                medReminders[0] = "";
                firstTv.setText(format.format(calendar.getTime()));
                medReminders[0] = String.valueOf(reminderOne);
            } else if (flag == FLAG_SECOND_REMINDER) {
                reminderTwo = calendar.getTimeInMillis();
                if(reminderTwo <= reminderOne){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[1] = "";
                    secondTv.setText(format.format(calendar.getTime()));
                    medReminders[1] = String.valueOf(reminderTwo);
                }
            }else if (flag == FLAG_THIRD_REMINDER) {
                reminderThree = calendar.getTimeInMillis();
                if(reminderThree <= reminderTwo){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[2] = "";
                    thirdTv.setText(format.format(calendar.getTime()));
                    medReminders[2] = String.valueOf(reminderThree);
                }
            }else if (flag == FLAG_FOURTH_REMINDER) {
                reminderFour = calendar.getTimeInMillis();
                if(reminderFour <= reminderThree){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[3] = "";
                    fourthTv.setText(format.format(calendar.getTime()));
                    medReminders[3] = String.valueOf(reminderFour);
                }
            }else if (flag == FLAG_FIFTH_REMINDER) {
                reminderFive = calendar.getTimeInMillis();
                if(reminderFive <= reminderFour){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[4] = "";
                    fifthTv.setText(format.format(calendar.getTime()));
                    medReminders[4] = String.valueOf(reminderFive);
                }
            }
        }
    }


    public class InsertReminderAsyncTask extends AsyncTask<Void, Void, Void>{

        Context context = getBaseContext();

        @Override
        protected Void doInBackground(Void... voids) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ReminderEntry.MEDICINE_NAME, medName);
            contentValues.put(ReminderEntry.TYPE, medType);
            contentValues.put(ReminderEntry.COLOR, medColor);
            contentValues.put(ReminderEntry.SHAPE, medShape);
            contentValues.put(ReminderEntry.STOCK, medStock);
            contentValues.put(ReminderEntry.DOSAGE, medDosage);
            contentValues.put(ReminderEntry.MEASURE, medMeasure);
            contentValues.put(ReminderEntry.TIMESADAY, medTimes);
            contentValues.put(ReminderEntry.NOTES, medNotes);
            contentValues.put(ReminderEntry.FROM_DATE, medFromDate);
            contentValues.put(ReminderEntry.TO_DATE, medToDate);
            contentValues.put(ReminderEntry.REMINDER_TIME, medReminders.toString());

            getContentResolver().insert(ReminderEntry.CONTENT_URI, contentValues);

            Cursor cursor = getContentResolver().query(ReminderEntry.CONTENT_URI,null, null, null,null);

            Log.v("testtest", String.valueOf(cursor.getCount()));

            return null;
        }
    }

    public boolean validateInput(){
        boolean flag = false;
        if(fromDate.getText().toString().isEmpty()){
            flag = false;
        }else if(toDate.getText().toString().isEmpty()){
            flag = false;
        }else if(reminderTimesFlag == 1){
            if(firstTv.getText().toString().isEmpty()){
                flag = false;
            }else {
                flag = true;
            }
        }else if(reminderTimesFlag == 2){
            if(firstTv.getText().toString().isEmpty() || secondTv.getText().toString().isEmpty()){
                flag = false;
            }else {
                flag = true;
            }
        }else if(reminderTimesFlag == 3){
            if(firstTv.getText().toString().isEmpty() || secondTv.getText().toString().isEmpty() || thirdTv.getText().toString().isEmpty()){
                flag = false;
            }else {
                flag = true;
            }
        }else if(reminderTimesFlag == 4){
            if(firstTv.getText().toString().isEmpty() || secondTv.getText().toString().isEmpty() || thirdTv.getText().toString().isEmpty() || fourthTv.getText().toString().isEmpty()){
                flag = false;
            }else {
                flag = true;
            }
        }else if(reminderTimesFlag == 5){
            if(firstTv.getText().toString().isEmpty() || secondTv.getText().toString().isEmpty() || thirdTv.getText().toString().isEmpty() || fourthTv.getText().toString().isEmpty()|| fifthTv.getText().toString().isEmpty()) {
                flag = false;
            }else {
                flag = true;
            }
        }else {
            flag = true;
        }
        return flag;
    }
}
