package com.example.android.medicinereminder.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.medicinereminder.Adapters.TodayReminderHolder;
import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.Notifications.NotificationUtils;
import com.example.android.medicinereminder.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    private static final String CHANNEL_ID ="Medicine Notification" ;
    @BindView(R.id.rv_today) RecyclerView mRecyclerView;
    private Context mContext;
    private DatabaseReference mRemindersDatabaseReference;
    FirebaseRecyclerAdapter<Reminder, TodayReminderHolder> firebaseAdapter;


    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, rootview);

        mContext = getContext();
        mRemindersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("reminders");
        mRemindersDatabaseReference.keepSynced(true);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_reminder);
        fab.setVisibility(View.INVISIBLE);

        SimpleDateFormat formatToday = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = formatToday.format(System.currentTimeMillis());
        try {
            Date today = formatToday.parse(todayDate);

            Query query = mRemindersDatabaseReference
                    .orderByChild("reminderDate").equalTo(today.getTime());

            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            query.addChildEventListener(childEventListener);

            FirebaseRecyclerOptions<Reminder> options =
                    new FirebaseRecyclerOptions.Builder<Reminder>()
                            .setQuery(query, Reminder.class)
                            .build();

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);

            firebaseAdapter = new FirebaseRecyclerAdapter<Reminder, TodayReminderHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull TodayReminderHolder holder, int position, @NonNull Reminder model) {
                    holder.bind(model);
                }

                @NonNull
                @Override
                public TodayReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.today_list_item, parent, false);

                    return new TodayReminderHolder(view);
                }
            };

            mRecyclerView.setAdapter(firebaseAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        Button button = rootview.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils.reminderUser(getContext(),"Take "+ "test notification");
            }
        });

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        firebaseAdapter.stopListening();
    }

    /* public ArrayList<Reminder> getReminderArrayList(){

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
            Log.v("testRe", String.valueOf(todayReminders.size()));
          return todayReminders;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<Reminder> getRemindersSorted(ArrayList<Reminder> reminders){
        for(int i = 0; i<reminders.size(); i++){
           long time = reminders.get(i).getReminderTime();
           SimpleDateFormat format = new SimpleDateFormat("HH::mm");
           String stringTime = format.format(time);
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String date = format1.format(System.currentTimeMillis());
            try {
                Date today = format1.parse(date);
                long todayMilliseconds = today.getTime();
                Date reminderTime = format.parse(stringTime);
                long todayTime = todayMilliseconds + reminderTime.getTime();
                reminders.get(i).setReminderTime(todayTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(reminders, (o1, o2) -> Long.compare(o1.getReminderTime(), o2.getReminderTime()));
        Log.v("testRe", String.valueOf(reminders.size()));
        return reminders;
    }
*/

}
