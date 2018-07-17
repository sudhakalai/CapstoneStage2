package com.example.android.medicinereminder.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.android.medicinereminder.R;

public class ReminderActivity extends AppCompatActivity {

    EditText repeatTv;

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

        repeatTv = findViewById(R.id.times_a_day);

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
                Intent intent = new Intent(this, TimeActivity.class);
                intent.putExtra("TIMESADAY", repeatTv.getText().toString());
                startActivity(intent);
                return true;

            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
