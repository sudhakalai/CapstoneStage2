package com.example.android.medicinereminder.UI;


import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemindersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, ReminderAdapter.ReminderAdapterOnClickListener {

    @BindView(R.id.rv_reminders) RecyclerView mRecyclerView;
    ReminderAdapter adapter;
    private static final int REMINDER_LOADER = 0;
    ReminderAdapter.ReminderAdapterOnClickListener onClickListener;
    private static final String BUNDLE_RECYCLER_LAYOUT = "ReminderFragment.recycler.layout";
    Parcelable savedRecyclerLayoutState;


    public RemindersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reminders, container, false);
        ButterKnife.bind(this, rootView);

        FloatingActionButton fabReminder = getActivity().findViewById(R.id.fab_reminder);


        fabReminder.setVisibility(View.VISIBLE);

        fabReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        onClickListener = this;
        adapter = new ReminderAdapter(getContext(), null,onClickListener);
        mRecyclerView.setAdapter(adapter);

        //loader
        getLoaderManager().initLoader(REMINDER_LOADER, null, this);

        return rootView;
    }

    //loader
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


    //implemented when clicked on recycler view item
    @Override
    public void onItemClick(View view, int position, int id) {
        Intent editIntent = new Intent(getContext(), EditReminderActivity.class);
        editIntent.putExtra("reminderID", id);
        startActivity(editIntent,ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
        {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }
}
