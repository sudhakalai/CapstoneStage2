package com.example.android.medicinereminder.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryReminderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_med_name)
    TextView medicineName;

    @BindView(R.id.tv_finished_dosage)
    TextView medcinedosage;

    @BindView(R.id.tv_finished_time)
    TextView medicineTime;

    public HistoryReminderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Reminder reminder){
        medicineName.setText(reminder.getMedicineName());
        medcinedosage.setText(" "+reminder.getDosage() + reminder.getMeasure());
        medicineTime.setText(String.valueOf(reminder.getReminderTime()));
    }
}
