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
 * This is the adapter which populates the recycle view items in the today fragment.
 */

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewholder> {

    private ArrayList<Reminder> mReminders;
    private Context mContext;

    public TodayAdapter(Context context, ArrayList<Reminder> reminders){
        mContext = context;
        mReminders = reminders;
    }

    @Override
    public TodayViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.today_list_item,parent, false);
        return new TodayViewholder(view);
    }

    @Override
    public void onBindViewHolder(TodayViewholder holder, int position) {

        holder.reminder = mReminders.get(position);
        String time = String.valueOf(holder.reminder.getTimes()[0]);
        String name = holder.reminder.getMedicineName();
        String dosage = String.valueOf(holder.reminder.getDosage());
        String quantity = String.valueOf(holder.reminder.getQuantity());
        String color = holder.reminder.getMedicineColor();
        String shape = holder.reminder.getShape();
        String medicineDosage = quantity + " * " + dosage + "mg, "+ shape + ", " + color + " tablet";
        String notes = holder.reminder.getNotes();

        holder.bind(name, time, medicineDosage, notes);
    }

    @Override
    public int getItemCount() {
        if(mReminders != null){
            return mReminders.size();
        }else {
            return 0;
        }
    }

    public static class TodayViewholder extends RecyclerView.ViewHolder{

        Context context;
        TextView tvMedicineName;
        TextView tvTime;
        TextView tvDosage;
        TextView tvNotes;
        Reminder reminder;

        public TodayViewholder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            tvMedicineName = itemView.findViewById(R.id.medicine_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvDosage = itemView.findViewById(R.id.dosage);
            tvNotes = itemView.findViewById(R.id.notes);

        }

        public void bind(String medicineName, String time, String dosage, String notes){

            tvMedicineName.setText(medicineName);
            tvTime.setText(time);
            tvDosage.setText(dosage);
            tvNotes.setText(notes);

        }
    }
}
