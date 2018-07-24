package com.example.android.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.medicinereminder.Adapters.CategoryTabAdapter;
import com.example.android.medicinereminder.Database.ReminderDbHelper;
import com.example.android.medicinereminder.UI.SettingsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ReminderDbHelper dbHelper = new ReminderDbHelper(this);
        dbHelper.getWritableDatabase();

        floatingActionButton = findViewById(R.id.fab_reminder);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        CategoryTabAdapter adapter = new CategoryTabAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
