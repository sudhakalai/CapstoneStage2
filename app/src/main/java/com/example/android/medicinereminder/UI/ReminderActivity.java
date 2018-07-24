package com.example.android.medicinereminder.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class ReminderActivity extends AppCompatActivity {

    EditText medicineNameET;
    Spinner medicineType;
    Spinner medicineColor;
    Spinner medicineShape;
    EditText stockET;
    EditText dosageET;
    Spinner measure;
    Spinner timesADay;
    EditText notesET;
    Context context;
    private Uri mCurrentReminderUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Setting up the back button in the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        medicineNameET = findViewById(R.id.med_name_text);
        medicineType = findViewById(R.id.type_spinner);
        medicineColor = findViewById(R.id.color_spinner);
        medicineShape = findViewById(R.id.shape_spinner);
        stockET = findViewById(R.id.stock_text);
        dosageET = findViewById(R.id.dosage_text);
        measure = findViewById(R.id.spinner_dosage);
        timesADay = findViewById(R.id.spinner_times_a_day);
        notesET = findViewById(R.id.notes_et);
        context = this;

        Intent intent = getIntent();
        mCurrentReminderUri = intent.getData();

        if(mCurrentReminderUri == null){
            setTitle("Add Reminder");
        }else{
            setTitle("Edit Reminder");

        }

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
                    Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }

                return true;

            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

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

}
