package com.example.android.medicinereminder.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.medicinereminder.R;

import java.util.ArrayList;

public class ListRemoteViews implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<String> reminders;

    public ListRemoteViews(Context context) {
        this.mContext = context;
        reminders = Preference.loadReminder(mContext);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        reminders = Preference.loadReminder(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.reminder_widget_list_item);

        row.setTextViewText(R.id.widget_list_item, reminders.get(position));

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
