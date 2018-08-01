package com.example.android.medicinereminder.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Settings Fragment
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //switch preference(stock reminder)
        Preference preference = findPreference(getString(R.string.stock_key));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                if(preference.isEnabled()){
                    preference.setSummary(R.string.stock_summary_on);
                }else {
                    preference.setSummary(R.string.stock_summary_off);
                }

                return true;
            }
        });

        //delete all data preference
        Preference deletePreference = findPreference(getString(R.string.delete_all_data_key));
        deletePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().getContentResolver().delete(ReminderEntry.CONTENT_URI, null, null);
                getActivity().getContentResolver().delete(ReminderEntry.CONTENT_URI_TIME, null, null);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.removeValue();
                Toast.makeText(getActivity().getBaseContext(),getString(R.string.deleting), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
