package com.example.android.medicinereminder.UI;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.medicinereminder.Adapters.TodayAdapter;
import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.FromintegerUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Context mContext;
    TodayAdapter adapter;
    int REMINDER_LIST_LOADER = 200;


    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_today, container, false);

        mRecyclerView = rootview.findViewById(R.id.rv_today);
        mContext = getContext();

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_reminder);
        fab.setVisibility(View.INVISIBLE);

       /* ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("Crocin", "Tablet", 100, "mg", "Blue", "Round",86400,10, "Take 1", 86400 ));
*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new TodayAdapter(getContext(), getTodayReminders(getReminderArrayList()));
        mRecyclerView.setAdapter(adapter);



        return rootview;
    }

    public ArrayList<Reminder> getReminderArrayList(){

        Cursor reminderCursor = getActivity().getContentResolver().query(ReminderEntry.CONTENT_URI,null, null, null,null);
        Cursor timeCursor = getActivity().getContentResolver().query(ReminderEntry.CONTENT_URI_TIME, null,null,null, null);

        ArrayList<Reminder> reminders = new ArrayList<>();

        int reminderCount = reminderCursor.getCount();
        int timeCursorCount = timeCursor.getCount();

        reminderCursor.moveToFirst();
        timeCursor.moveToFirst();

        for(int i=0; i< reminderCount; i++){
            String medName = reminderCursor.getString(reminderCursor.getColumnIndex(ReminderEntry.MEDICINE_NAME));
            String medType = FromintegerUtils.getMedicineTypeString(reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.TYPE)), mContext);
            String medColor = FromintegerUtils.getMedicineColorString(reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.COLOR)), mContext);
            String medShape = FromintegerUtils.getMedicineShapeString(reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.SHAPE)), mContext);
            int stock = reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.STOCK));
            int dosage = reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.DOSAGE));
            String measure = FromintegerUtils.getMedicineMeasureString(reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.MEASURE)),mContext);
            int timesADay = reminderCursor.getInt(reminderCursor.getColumnIndex(ReminderEntry.TIMESADAY));
            String notes = reminderCursor.getString(reminderCursor.getColumnIndex(ReminderEntry.NOTES));
            long fromDate = reminderCursor.getLong(reminderCursor.getColumnIndex(ReminderEntry.FROM_DATE));
            long toDate = reminderCursor.getLong(reminderCursor.getColumnIndex(ReminderEntry.TO_DATE));
            long reminderOne = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_ONE));
            long reminderTwo = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_TWO));
            long reminderThree = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_THREE));
            long reminderFour = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_FOUR));
            long reminderFive = timeCursor.getLong(timeCursor.getColumnIndex(ReminderEntry.REMINDER_FIVE));

            Log.v("testreminder", medName + medType + medColor + medShape + stock + " " + dosage + measure+notes+ fromDate+toDate);
            Log.v("testreminder",reminderOne + " " + reminderTwo + " "+ reminderThree + " " + reminderFour + " "+reminderFive);

            for(long j = fromDate; j<= toDate; j = j+86400000){
                long date = j;
                Log.v("testreminder", String.valueOf(date));
                if(timesADay == 1){
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderOne ));
                }else if(timesADay == 2){
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderOne ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderTwo ));
                }else if(timesADay == 3){
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderOne ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderTwo ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderThree ));
                }else if(timesADay == 4){
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderOne ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderTwo ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderThree ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderFour ));
                }else if(timesADay == 5){
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderOne ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderTwo ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderThree ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderFour ));
                    reminders.add(new Reminder(medName, medType, dosage, measure, medColor, medShape, date, stock, notes,reminderFive ));
                }

            }

            reminderCursor.moveToNext();
            timeCursor.moveToNext();
        }

        reminderCursor.close();
        timeCursor.close();
        return reminders;
    }

    public ArrayList<Reminder> getTodayReminders(ArrayList<Reminder> reminders){
        ArrayList<Reminder> todayReminders = new ArrayList<>();
        Collections.sort(reminders, (o1, o2) -> Long.compare(o1.getReminderDate(), o2.getReminderDate()));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(System.currentTimeMillis());
        try {
            Date today = format.parse(date);
            long todayMilliseconds = today.getTime();
            for(int i=0; i<reminders.size(); i++){
                if(todayMilliseconds <= reminders.get(i).getReminderDate() && reminders.get(i).getReminderDate()< todayMilliseconds+86400000){
                    todayReminders.add(reminders.get(i));
                }
            }
            return todayReminders;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }


}
