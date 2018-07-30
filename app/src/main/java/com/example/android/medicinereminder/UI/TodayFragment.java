package com.example.android.medicinereminder.UI;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.ArrayList;
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
    public  ArrayList<Reminder> reminders = new ArrayList<>();


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
                    reminders.add(new Reminder(model));
                    holder.setOnClickListener(new TodayReminderHolder.ClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), PopActivity.class);
                            String key = getRef(position).getKey();
                            intent.putExtra("reminderKey", key);
                            startActivity(intent);

                        }
                    });
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
        Log.v("testlist", reminders.size()+"");
        firebaseAdapter.stopListening();
    }




}
