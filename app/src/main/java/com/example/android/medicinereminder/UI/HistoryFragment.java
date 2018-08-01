package com.example.android.medicinereminder.UI;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.medicinereminder.Adapters.HistoryReminderHolder;
import com.example.android.medicinereminder.Model.Reminder;
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
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    @BindView(R.id.date_button)
    ImageView dateView;
    static RecyclerView mRecyclerView;
    static TextView inputDate;
    private static DatabaseReference mRemindersDatabaseReference;
    static FirebaseRecyclerAdapter<Reminder, HistoryReminderHolder> firebaseAdapter;
    static Context context;
    private DatePickerFragment mDatePickerFragment;
    private static final String BUNDLE_RECYCLER_LAYOUT = "HistoryFragment.recycler.layout";
    Parcelable savedRecyclerLayoutState;
    private static final String DATE_STRING = "dateString";

    private static int NO_OF_COLUMNS = 2;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, rootview);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_reminder);
        mRecyclerView = rootview.findViewById(R.id.rv_history);
        inputDate = rootview.findViewById(R.id.input_date);
        context = getContext();
        mDatePickerFragment = new DatePickerFragment();

        fab.setVisibility(View.INVISIBLE);

        //getting data from firebase
        mRemindersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("reminders");
        mRemindersDatabaseReference.keepSynced(true);

        //getting today time
        SimpleDateFormat formatToday = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = formatToday.format(System.currentTimeMillis());
        inputDate.setText(todayDate);
        try {
            Date input = formatToday.parse(todayDate);

            Query query = mRemindersDatabaseReference
                    .orderByChild("reminderDate").equalTo(input.getTime());

            //child event listener
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

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(layoutManager);

            firebaseAdapter = new FirebaseRecyclerAdapter<Reminder, HistoryReminderHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull HistoryReminderHolder holder, int position, @NonNull Reminder model) {
                    holder.bind(model);
                }

                @NonNull
                @Override
                public HistoryReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.history_list_item, parent, false);

                    return new HistoryReminderHolder(view);
                }
            };

            mRecyclerView.setAdapter(firebaseAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerFragment.show(getFragmentManager(), "datePicker");

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

    //Date picker
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

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

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String selectedDate = format.format(calendar.getTime());
            inputDate.setText(selectedDate);
            populateUI(selectedDate);
        }
    }

    //Populates UI
    public static void populateUI(String date){
        firebaseAdapter.stopListening();
        Log.v("testdate", date);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date sDate = format.parse(date);
            Query query = mRemindersDatabaseReference
                    .orderByChild("reminderDate").equalTo(sDate.getTime());
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
            firebaseAdapter = new FirebaseRecyclerAdapter<Reminder, HistoryReminderHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull HistoryReminderHolder holder, int position, @NonNull Reminder model) {
                    holder.bind(model);
                }

                @NonNull
                @Override
                public HistoryReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.history_list_item, parent, false);

                    return new HistoryReminderHolder(view);
                }
            };
            firebaseAdapter.startListening();
            mRecyclerView.setAdapter(firebaseAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATE_STRING, inputDate.getText().toString());
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
        {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            inputDate.setText(savedInstanceState.getString(DATE_STRING));
        }
    }
}
