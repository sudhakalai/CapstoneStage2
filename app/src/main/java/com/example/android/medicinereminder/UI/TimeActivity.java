package com.example.android.medicinereminder.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.android.medicinereminder.R;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Setting up the back button in the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);

        RelativeLayout reminderOne = findViewById( R.id.reminder_one);
        RelativeLayout reminderTwo = findViewById( R.id.reminder_two);
        RelativeLayout reminderThree = findViewById( R.id.reminder_three);
        RelativeLayout reminderFour = findViewById( R.id.reminder_four);
        RelativeLayout reminderFive = findViewById( R.id.reminder_five);


        String timesADay;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("TIMESADAY");

            switch(Integer.valueOf(value)){
                case 1:
                    reminderFive.setVisibility(View.GONE);
                    reminderFour.setVisibility(View.GONE);
                    reminderThree.setVisibility(View.GONE);
                    reminderTwo.setVisibility(View.GONE);
                    break;
                case 2:
                    reminderFive.setVisibility(View.GONE);
                    reminderFour.setVisibility(View.GONE);
                    reminderThree.setVisibility(View.GONE);
                    break;
                case 3:
                    reminderFive.setVisibility(View.GONE);
                    reminderFour.setVisibility(View.GONE);
                    break;
                case 4:
                    reminderFive.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete:
                return true;
            case R.id.action_close:
                return true;
            case R.id.action_save:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
