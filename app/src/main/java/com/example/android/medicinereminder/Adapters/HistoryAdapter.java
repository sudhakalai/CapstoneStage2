package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;

import java.util.ArrayList;

/**
 * This is the adapter that populates the history fragment.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewholder>{

    private ArrayList<Reminder> mReminders;
    private Context mContext;

    public HistoryAdapter(Context context, ArrayList<Reminder> reminders){
        mContext = context;
        mReminders = reminders;
    }

    @Override
    public HistoryViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.history_list_item,parent, false);
        return new HistoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewholder holder, int position) {
        holder.reminder = mReminders.get(position);
        String time = String.valueOf(holder.reminder.getReminderTime());
        String name = holder.reminder.getMedicineName();
        String dosage = ", "+  holder.reminder.getDosage() + "mg";

        holder.bind(name, time, dosage);
    }

    @Override
    public int getItemCount() {
        if(mReminders != null){
            return mReminders.size();
        }else {
            return 0;
        }
    }

    public static class HistoryViewholder extends RecyclerView.ViewHolder{

        Reminder reminder;
        Context context;
        TextView tvMedName;
        TextView tvFinishedTime;
        TextView tvFinishedDosage;
        ImageView ivState;

        public HistoryViewholder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            tvMedName = itemView.findViewById(R.id.tv_med_name);
            tvFinishedTime = itemView.findViewById(R.id.tv_finished_time);
            tvFinishedDosage = itemView.findViewById(R.id.finished_dosage);
            ivState = itemView.findViewById(R.id.iv_state);

        }

        public void bind(String medName, String time, String dosage){

            tvMedName.setText(medName);
            tvFinishedTime.setText(time);
            tvFinishedDosage.setText(dosage);
        }
    }
}