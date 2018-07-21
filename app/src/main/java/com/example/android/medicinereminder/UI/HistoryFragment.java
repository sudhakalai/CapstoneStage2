package com.example.android.medicinereminder.UI;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.medicinereminder.Adapters.HistoryAdapter;
import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static int NO_OF_COLUMNS = 2;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_history, container, false);

        mRecyclerView = rootview.findViewById(R.id.rv_history);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_reminder);
        fab.setVisibility(View.INVISIBLE);

        ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("Ibuprofene", "Capsule", 100, "mg", 1, "White and Red", "oblong", new int[]{1230, 230}, 1021987, 2021987,10,"to be taken after food",0));
        reminders.add(new Reminder("Ibuprofene", "Capsule", 100, "mg", 1, "White and Red", "oblong", new int[]{1230, 230}, 1021987, 2021987,10,"to be taken after food",0));
        reminders.add(new Reminder("Ibuprofene", "Capsule", 100, "mg", 1, "White and Red", "oblong", new int[]{1230, 230}, 1021987, 2021987,10,"to be taken after food",0));
        reminders.add(new Reminder("Ibuprofene", "Capsule", 100, "mg", 1, "White and Red", "oblong", new int[]{1230, 230}, 1021987, 2021987,10,"to be taken after food",0));
        reminders.add(new Reminder("Ibuprofene", "Capsule", 100, "mg", 1, "White and Red", "oblong", new int[]{1230, 230}, 1021987, 2021987,10,"to be taken after food",0));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        HistoryAdapter adapter = new HistoryAdapter(getContext(), reminders);
        mRecyclerView.setAdapter(adapter);

        return rootview;
    }

}
