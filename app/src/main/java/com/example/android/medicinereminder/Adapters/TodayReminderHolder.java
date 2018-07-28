package com.example.android.medicinereminder.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sudha on 27-Jul-18.
 */

public class TodayReminderHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.medicine_name) TextView tvMedicineName;
    @BindView(R.id.tv_time) TextView tvTime;
    @BindView(R.id.dosage) TextView tvDosage;
    @BindView(R.id.notes) TextView tvNotes;
    Reminder todayReminder;

    public TodayReminderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(Reminder reminder ){
        tvTime.setText(String.valueOf(reminder.getReminderTime()));
        tvMedicineName.setText(reminder.getMedicineName());
        tvNotes.setText(reminder.getNotes());
        tvDosage.setText(reminder.getDosage() + reminder.getMeasure() + ", " + reminder.getMedicineColor() + ", "+ reminder.getShape()+ " "+ reminder.getType());
    }

}
