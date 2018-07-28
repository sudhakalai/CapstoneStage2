package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.medicinereminder.Database.ReminderContract.ReminderEntry;
import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.FromintegerUtils;
import com.example.android.medicinereminder.Util.ReminderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This adapter populates the list items in the reminder fragment.
 */

public class ReminderAdapter extends ReminderCursorAdapter<ReminderAdapter.ReminderViewholder>{

    Context mContext;

    public ReminderAdapter(Context context,Cursor cursor){
        super(context,cursor);
        mContext = context;
    }

    @Override
    public ReminderViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reminder_list_item,parent, false);
        return new ReminderViewholder(view);
    }

    @Override
    public void onBindViewHolder(ReminderViewholder viewHolder, Cursor cursor) {
        String medName = cursor.getString(cursor.getColumnIndex(ReminderEntry.MEDICINE_NAME));
        Log.v("testtest", medName);
        long fromDate = cursor.getLong(cursor.getColumnIndex(ReminderEntry.FROM_DATE));
        long toDate = cursor.getLong(cursor.getColumnIndex(ReminderEntry.TO_DATE));
        String date = ReminderUtils.getDateString(fromDate) + " to " + ReminderUtils.getDateString(toDate);
        String stock = cursor.getInt(cursor.getColumnIndex(ReminderEntry.STOCK)) + " " + FromintegerUtils.getMedicineTypeString(cursor.getInt(cursor.getColumnIndex(ReminderEntry.TYPE)), mContext) + " left";

        viewHolder.bind(medName, date, stock);
    }

    public static class ReminderViewholder extends RecyclerView.ViewHolder{

        Context context;
        @BindView(R.id.reminder_med) TextView medTv;
        @BindView(R.id.date_tv) TextView dateTv;
        @BindView(R.id.remaining_stock) TextView stockTv;
        Reminder reminder;

        public ReminderViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            context = itemView.getContext();

        }

        public void bind(String name, String date, String stock){

            medTv.setText(name);
            dateTv.setText(date);
            stockTv.setText(stock);
        }

    }
}