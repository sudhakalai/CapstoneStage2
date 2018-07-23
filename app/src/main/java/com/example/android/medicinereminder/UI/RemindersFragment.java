package com.example.android.medicinereminder.UI;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.medicinereminder.Adapters.ReminderAdapter;
import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemindersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView mRecyclerView;
    FloatingActionButton fabReminder;
    ReminderAdapter adapter;
    private static final int REMINDER_LOADER = 0;


    public RemindersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reminders, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_reminders);

        fabReminder = getActivity().findViewById(R.id.fab_reminder);
        fabReminder.setVisibility(View.VISIBLE);

        fabReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new ReminderAdapter(getContext(), null);
        mRecyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(REMINDER_LOADER, null, this);

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                ReminderEntry._ID,
                ReminderEntry.MEDICINE_NAME,
                ReminderEntry.TYPE,
                ReminderEntry.COLOR,
                ReminderEntry.SHAPE,
                ReminderEntry.STOCK,
                ReminderEntry.DOSAGE,
                ReminderEntry.MEASURE,
                ReminderEntry.TIMESADAY,
                ReminderEntry.NOTES,
                ReminderEntry.FROM_DATE,
                ReminderEntry.TO_DATE};

        return new CursorLoader(getContext(),ReminderEntry.CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v("testtest",String.valueOf(data.getCount()));
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
