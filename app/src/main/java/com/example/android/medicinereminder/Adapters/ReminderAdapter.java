package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;

import java.util.ArrayList;

/**
 * This adapter populates the list items in the reminder fragment.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewholder>{

    private ArrayList<Reminder> mReminders;
    private Context mContext;

    public ReminderAdapter(Context context, ArrayList<Reminder> reminders){
        mContext = context;
        mReminders = reminders;
    }

    @Override
    public ReminderViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.reminder_list_item,parent, false);
        return new ReminderViewholder(view);
    }

    @Override
    public void onBindViewHolder(ReminderViewholder holder, int position) {

        holder.reminder = mReminders.get(position);
        String name = holder.reminder.getMedicineName();
        String date = holder.reminder.getFromDate()+ " - " + holder.reminder.getToDate();
        String stock = String.valueOf(holder.reminder.getStock()) + " " + holder.reminder.getType() + " left";
        holder.bind(name, date, stock);
    }

    @Override
    public int getItemCount() {
        if(mReminders != null){
            return mReminders.size();
        }else {
            return 0;
        }
    }

    public static class ReminderViewholder extends RecyclerView.ViewHolder{

        Context context;
        TextView medTv;
        TextView dateTv;
        TextView stockTv;
        Reminder reminder;

        public ReminderViewholder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            medTv = itemView.findViewById(R.id.reminder_med);
            dateTv = itemView.findViewById(R.id.date_tv);
            stockTv= itemView.findViewById(R.id.remaining_stock);
        }

        public void bind(String name, String date, String stock){

            medTv.setText(name);
            dateTv.setText(date);
            stockTv.setText(stock);
        }
    }
}
