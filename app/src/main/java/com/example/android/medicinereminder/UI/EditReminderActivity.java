package com.example.android.medicinereminder.UI;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.medicinereminder.Database.ReminderContract;
import com.example.android.medicinereminder.Model.ReminderEdit;
import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.ReminderLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditReminderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ReminderEdit>{

    Uri currentUri;
    Uri currentTimeUri;
    @BindView(R.id.med_name_edit)
    TextView medNameTv;
    @BindView(R.id.med_appearance)
    TextView medAppearancetv;
    @BindView(R.id.dates) TextView datesTv;
    @BindView(R.id.med_times) TextView timesTv;
    @BindView(R.id.med_stock)
    EditText stockET;
    private boolean mReminderHasChanged = false;
    int id;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mReminderHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_reminder);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        stockET.setOnTouchListener(mTouchListener);


        if(getIntent().hasExtra("reminderID")){
            id = getIntent().getIntExtra("reminderID", 1);
            currentUri = ContentUris.withAppendedId(ReminderContract.ReminderEntry.CONTENT_URI, id);
            currentTimeUri = ContentUris.withAppendedId(ReminderContract.ReminderEntry.CONTENT_URI_TIME, id);

            getLoaderManager().initLoader(0, null, this);


    }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_save:
                ContentValues values = new ContentValues();
                values.put(ReminderContract.ReminderEntry.STOCK, Integer.parseInt(stockET.getText().toString()));
                getContentResolver().update(currentUri,values, null, null);
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mReminderHasChanged) {
                    finish();
                    return true;
                } else {
                    // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                    // Create a click listener to handle the user confirming that
                    // changes should be discarded
                    DialogInterface.OnClickListener discardButtonClickListener =
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User clicked "Discard" button , navigate to parent activity
                                    finish();
                                }
                            };
                    // Show a dialog that notifies the user they have unsaved changes
                    showUnsavedChangesDialog(discardButtonClickListener);
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteReminder(){
        if (currentUri != null && currentTimeUri != null) {
            int rowsDeleted= getContentResolver().delete(currentUri, null, null);
            int timeRowsDeleted= getContentResolver().delete(currentTimeUri, null, null);
            if (rowsDeleted == 0 && timeRowsDeleted ==0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "Delete failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, "Delete successful",
                        Toast.LENGTH_SHORT).show();
            }
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("reminders").orderByChild("reminderId").equalTo(id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        finish();
    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete Reminder?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteReminder();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Unsaved changes. do you want to exit?");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public Loader<ReminderEdit> onCreateLoader(int i, Bundle bundle) {
        return new ReminderLoader(this, currentUri, currentTimeUri);
    }

    @Override
    public void onLoadFinished(Loader<ReminderEdit> loader, ReminderEdit reminderEdit) {

        medNameTv.setText(reminderEdit.getMedName());
        medAppearancetv.setText(reminderEdit.getAppearance());
        datesTv.setText(reminderEdit.getDates());
        timesTv.setText(reminderEdit.getTimes());
        stockET.setText(String.valueOf(reminderEdit.getStock()));

    }

    @Override
    public void onLoaderReset(Loader<ReminderEdit> loader) {
        medNameTv.setText("");
        medAppearancetv.setText("");
        datesTv.setText("");
        timesTv.setText("");
        stockET.setText("");

    }
}
