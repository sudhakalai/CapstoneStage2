package com.example.android.medicinereminder.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.FromintegerUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    static long [] medReminders;
    @BindView(R.id.reminder_one) RelativeLayout reminderOne;
    @BindView(R.id.reminder_two) RelativeLayout reminderTwo;
    @BindView(R.id.reminder_three) RelativeLayout reminderThree;
    @BindView(R.id.reminder_four) RelativeLayout reminderFour;
    @BindView(R.id.reminder_five) RelativeLayout reminderFive;
    @BindView(R.id.from_date_picker) ImageView fromPicker;
    static TextView fromDate;
    @BindView(R.id.to_date_picker) ImageView toPicker;
    static TextView toDate;
    @BindView(R.id.first_button) ImageView firstReminder;
    static TextView firstTv;
    @BindView(R.id.second_button) ImageView secondReminder;
    static TextView secondTv;
    @BindView(R.id.third_button) ImageView thirdReminder;
    static TextView thirdTv;
    @BindView(R.id.fourth_button) ImageView fourthReminder;
    static  TextView fourthTv;
    @BindView(R.id.fifth_button) ImageView fifthReminder;
    static TextView fifthTv;
    private DatePickerFragment mDatePickerFragment;
    private TimePickerFragment mTimePickerFragment;
    private int reminderTimesFlag = 5;
    private static Context context;
    private boolean insertionFlag;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRemindersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Setting up the back button in the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);


        fromPicker = findViewById(R.id.from_date_picker);
        fromDate = findViewById(R.id.from_date);
        toDate = findViewById(R.id.to_date);
        firstTv = findViewById(R.id.first_reminder);
        secondTv = findViewById(R.id.second_reminder);
        thirdTv = findViewById(R.id.third_reminder);
        fourthTv = findViewById(R.id.fourth_reminder);
        fifthTv = findViewById(R.id.fifth_reminder);
        mDatePickerFragment = new DatePickerFragment();
        mTimePickerFragment = new TimePickerFragment();
        medReminders = new long[5];
        context = this;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRemindersDatabaseReference = mFirebaseDatabase.getReference().child("reminders");



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
                Log.v("testreminder", String.valueOf(reminderOne));
                medReminders[0] = 0;
                firstTv.setText(format.format(calendar.getTime()));
                medReminders[0] = reminderOne;
            } else if (flag == FLAG_SECOND_REMINDER) {
                reminderTwo = calendar.getTimeInMillis();
                if(reminderTwo <= reminderOne){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[1] = 0;
                    secondTv.setText(format.format(calendar.getTime()));
                    medReminders[1] = reminderTwo;
                }
            }else if (flag == FLAG_THIRD_REMINDER) {
                reminderThree = calendar.getTimeInMillis();
                if(reminderThree <= reminderTwo){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[2] = 0;
                    thirdTv.setText(format.format(calendar.getTime()));
                    medReminders[2] = reminderThree;
                }
            }else if (flag == FLAG_FOURTH_REMINDER) {
                reminderFour = calendar.getTimeInMillis();
                if(reminderFour <= reminderThree){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[3] = 0;
                    fourthTv.setText(format.format(calendar.getTime()));
                    medReminders[3] = reminderFour;
                }
            }else if (flag == FLAG_FIFTH_REMINDER) {
                reminderFive = calendar.getTimeInMillis();
                if(reminderFive <= reminderFour){
                    Toast.makeText(context, "Reminder time should be greater that previous reminders", Toast.LENGTH_SHORT).show();
                }else {
                    medReminders[4] = 0;
                    fifthTv.setText(format.format(calendar.getTime()));
                    medReminders[4] = reminderFive;
                }
            }
        }
    }


    public class InsertReminderAsyncTask extends AsyncTask<Void, Void, Void>{

        Context context = getBaseContext();

       @Override
        protected Void doInBackground(Void... voids) {

            Cursor cursor = getContentResolver().query(ReminderEntry.CONTENT_URI,null, null, null,null);

            int reminderId = cursor.getCount() + 1;

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
            contentValues.put(ReminderEntry.REMINDER_ID, reminderId);

            getContentResolver().insert(ReminderEntry.CONTENT_URI, contentValues);

            Cursor reminderCursor = getContentResolver().query(ReminderEntry.CONTENT_URI, null, null, null, null);

            ContentValues values = new ContentValues();
            values.put(ReminderEntry.REMINDER_ONE, medReminders[0]);
            Log.v("testtest", String.valueOf(medReminders[0]));
            if(reminderTimesFlag == 2){
                values.put(ReminderEntry.REMINDER_TWO, medReminders[1]);
            }else if(reminderTimesFlag == 3){
                values.put(ReminderEntry.REMINDER_TWO, medReminders[1]);
                values.put(ReminderEntry.REMINDER_THREE,medReminders[2]);
            }else if(reminderTimesFlag == 4){
                values.put(ReminderEntry.REMINDER_TWO, medReminders[1]);
                values.put(ReminderEntry.REMINDER_THREE,medReminders[2]);
                values.put(ReminderEntry.REMINDER_FOUR, medReminders[3]);
            }else if(reminderTimesFlag == 5){
                values.put(ReminderEntry.REMINDER_TWO, medReminders[1]);
                values.put(ReminderEntry.REMINDER_THREE,medReminders[2]);
                values.put(ReminderEntry.REMINDER_FOUR, medReminders[3]);
                values.put(ReminderEntry.REMINDER_FIVE, medReminders[4]);
            }
            values.put(ReminderEntry.REMINDER_TIME_ID, reminderId);

            getContentResolver().insert(ReminderEntry.CONTENT_URI_TIME, values);

            Cursor timeCursor = getContentResolver().query(ReminderEntry.CONTENT_URI_TIME,null, null, null, null);

            if(reminderCursor.getCount() == timeCursor.getCount()){
                insertionFlag = true;
            }else if(reminderCursor.getCount() > timeCursor.getCount()){
                Uri uri = ContentUris.withAppendedId(ReminderEntry.CONTENT_URI, reminderCursor.getCount() - 1);
                getContentResolver().delete(uri, null, null );
                insertionFlag = false;
            }else if(reminderCursor.getCount() < timeCursor.getCount()){
                Uri uri = ContentUris.withAppendedId(ReminderEntry.CONTENT_URI_TIME, timeCursor.getCount() - 1);
                getContentResolver().delete(uri, null, null);
                insertionFlag = false;
            }

            if(insertionFlag == true){
                createReminder(medName,medType, medDosage,medMeasure,medColor,medShape,medFromDate,medToDate, medNotes, medReminders);
            }

           Log.v("testcursor", reminderCursor.getCount() + "=" + timeCursor.getCount());


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(insertionFlag){
                Toast.makeText(context, "Reminder added!!!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Failed to add Reminder!!!", Toast.LENGTH_SHORT).show();
            }
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

    public void createReminder(String medName, int medType, int dosage, int measure, int color, int shape, long fromDate,long toDate, String notes, long[] reminders){
        ArrayList<Reminder> reminderList = new ArrayList<>();
        long from = getTrueDate(fromDate);
        long to = getTrueDate(toDate);
        for(long k= from; k<= to; k=k+86400000){
            for(int i=0; i<5; i++){
                if(reminders[i] != 0){
                    reminderList.add(new Reminder(
                            medName,
                            FromintegerUtils.getMedicineTypeString(medType, context),
                            dosage,
                            FromintegerUtils.getMedicineMeasureString(measure, context),
                            FromintegerUtils.getMedicineColorString(color, context),
                            FromintegerUtils.getMedicineShapeString(shape, context),
                            k,
                            notes,
                            k+getTrueTime(reminders[i]),
                            1
                    ));
                }
            }

        }

        for(int j=0; j<reminderList.size(); j++){
            mRemindersDatabaseReference.push().setValue(reminderList.get(j));
        }
    }

    public long getTrueDate(long date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String trueDateString = format.format(date);
        try {
            Date trueDate = format.parse(trueDateString);
            return trueDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getTrueTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String trueTimeString = format.format(time);
        try {
            Date trueTime = format.parse(trueTimeString);
            return trueTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
