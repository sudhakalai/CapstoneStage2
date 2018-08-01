package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.medicinereminder.Model.Reminder;
import com.example.android.medicinereminder.R;
import com.example.android.medicinereminder.Util.ReminderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

//This is the adapter that gets medicine history from firebase into the recyclerview


public class HistoryReminderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_med_name)
    TextView medicineName;

    @BindView(R.id.tv_finished_dosage)
    TextView medcinedosage;

    @BindView(R.id.tv_finished_time)
    TextView medicineTime;

    @BindView(R.id.iv_state)
    ImageView state;

    Context context;

    public HistoryReminderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    public void bind(Reminder reminder){
        medicineName.setText(reminder.getMedicineName()+" ");
        String dosage = reminder.getDosage()+" ";
        if(!reminder.getMeasure().equalsIgnoreCase("None")){
            dosage = dosage + reminder.getMeasure();
        }
        medcinedosage.setText(dosage);
        medicineTime.setText(ReminderUtils.getTimeString(reminder.getReminderTime()));
        if(reminder.getState() == 1){
            state.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
        }else if(reminder.getState() == 2){
            state.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_black_24dp));
        }else if(reminder.getState() == 3){
            state.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_close_black_24dp));
        }
    }
}
