package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.UI.HistoryFragment;
import com.example.android.medicinereminder.UI.RemindersFragment;
import com.example.android.medicinereminder.UI.TodayFragment;

/**
 * This the Adapter that displays the 3 different tabs in the MainActivity
 */

public class CategoryTabAdapter extends FragmentPagerAdapter {

    private static int PAGE_COUNT = 3;
    private Context mContext;

    public CategoryTabAdapter(FragmentManager fm) {
        super(fm);
    }
    public CategoryTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TodayFragment();
        } else if (position == 1) {
            return new HistoryFragment();
        } else {
            return new RemindersFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.today);
        } else if (position == 1) {
            return mContext.getString(R.string.history);
        } else {
            return mContext.getString(R.string.reminders);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
