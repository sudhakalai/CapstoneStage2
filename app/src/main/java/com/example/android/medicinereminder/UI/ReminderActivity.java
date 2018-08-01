package com.example.android.medicinereminder.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.ToIntegerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderActivity extends AppCompatActivity {

    @BindView(R.id.med_name_text) EditText medicineNameET;
    @BindView(R.id.type_spinner) Spinner medicineType;
    @BindView(R.id.color_spinner) Spinner medicineColor;
    @BindView(R.id.shape_spinner) Spinner medicineShape;
    @BindView(R.id.stock_text) EditText stockET;
    @BindView(R.id.dosage_text) EditText dosageET;
    @BindView(R.id.spinner_dosage) Spinner measure;
    @BindView(R.id.spinner_times_a_day) Spinner timesADay;
    @BindView(R.id.notes_et) EditText notesET;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Setting up the back button in the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        context = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:

                if(validateInput()){
                    //sending information in an intent to TimeActivity
                    Intent intent = new Intent(this, TimeActivity.class);
                    Bundle b = new Bundle();
                    b.putString("medicineName", medicineNameET.getText().toString());
                    b.putInt("medicineType", ToIntegerUtils.getMedicineType(medicineType, context ));
                    b.putInt("medicineColor", ToIntegerUtils.getMedicineColor(medicineColor,context));
                    b.putInt("medicineShape", ToIntegerUtils.getMedicineShape(medicineShape,context));
                    b.putInt("medicineStock", Integer.parseInt(stockET.getText().toString()));
                    b.putInt("medicineDosage", Integer.parseInt(dosageET.getText().toString()));
                    b.putInt("medicineMeasure", ToIntegerUtils.getMedicineMeasure(measure, context));
                    b.putInt("medicineTimes", Integer.parseInt(timesADay.getSelectedItem().toString()));
                    b.putString("medicineNote", notesET.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, getString(R.string.one_or_more), Toast.LENGTH_SHORT).show();
                }

                return true;

            case android.R.id.home:

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

        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.unsaved));
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

    //validate input
    public boolean validateInput(){
        boolean flag;
        if(medicineNameET.getText().toString().isEmpty()){
            flag = false;
        }else if(stockET.getText().toString().isEmpty()){
            flag = false;
        }else if(dosageET.getText().toString().isEmpty()){
            flag = false;
        }else if(notesET.getText().toString().isEmpty()){
            flag = false;
        }else {
            flag = true;
        }

        return flag;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("MedName", medicineNameET.getText().toString());
        outState.putInt("medType", medicineType.getSelectedItemPosition());
        outState.putInt("medColor", medicineColor.getSelectedItemPosition());
        outState.putInt("medShape", medicineShape.getSelectedItemPosition());
        outState.putString("medStock", stockET.getText().toString());
        outState.putString("medDosage", dosageET.getText().toString());
        outState.putInt("medMeasure", measure.getSelectedItemPosition());
        outState.putInt("medTimes", timesADay.getSelectedItemPosition());
        outState.putString("medNotes", notesET.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
        {
            medicineNameET.setText(savedInstanceState.getString("MedName"));
            medicineType.setSelection(savedInstanceState.getInt("medType"));
            medicineColor.setSelection(savedInstanceState.getInt("medColor"));
            medicineShape.setSelection(savedInstanceState.getInt("medShape"));
            stockET.setText(savedInstanceState.getString("medStock"));
            dosageET.setText(savedInstanceState.getString("medDosage"));
            measure.setSelection(savedInstanceState.getInt("medMeasure"));
            timesADay.setSelection(savedInstanceState.getInt("medTimes"));
            notesET.setText(savedInstanceState.getString("medNotes"));

        }
    }
}
