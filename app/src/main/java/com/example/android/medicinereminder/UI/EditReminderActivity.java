package com.example.android.medicinereminder.UI;

import android.app.AlarmManager;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.android.medicinereminder.Notifications.AlarmNotificationReceiver;
import com.example.android.medicinereminder.Notifications.AlarmNotificationService;
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

    //Declaring on touch listener
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
        //home button
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Attaching ontouchlistener to stock edittext
        stockET.setOnTouchListener(mTouchListener);


        //Getting values from intent and loading it in the views with a loader
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
                //Updating stock value
                ContentValues values = new ContentValues();
                values.put(ReminderContract.ReminderEntry.STOCK, Integer.parseInt(stockET.getText().toString()));
                getContentResolver().update(currentUri,values, null, null);
                finish();
                return true;
            case R.id.action_delete:
                //showing delete confirmation dialog
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                //listen to the on touch listener, if nothing is changed return home or ask user for confirmation
                if (!mReminderHasChanged) {
                    finish();
                    return true;
                } else {
                    // Create a click listener to handle the user confirming that changes should be discarded
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
                Toast.makeText(this, getString(R.string.del_fail),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.del_success),
                        Toast.LENGTH_SHORT).show();
            }
        }

        //Deleting from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("reminders").orderByChild("reminderId").equalTo(id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    int alarmId = Integer.valueOf(String.valueOf(appleSnapshot.child("alarmId").getValue())) ;
                    //Cancelling existing alarms for the deleted reminder
                    stopAlarm(alarmId);
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        finish();
    }

    //Stop alarm manager
    public void stopAlarm(int code) {

        Intent alarmIntent = new Intent(getBaseContext(), AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), code, alarmIntent, PendingIntent.FLAG_ONE_SHOT);


        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent


        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.deleteRem));
        builder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteReminder();
            }
        });
        builder.setNegativeButton(getString(R.string.cancelR), new DialogInterface.OnClickListener() {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.unsaved_dialog));
        builder.setPositiveButton(getString(R.string.discard), discardButtonClickListener);
        builder.setNegativeButton(getString(R.string.keep_editing), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Loader to load UI from database
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
