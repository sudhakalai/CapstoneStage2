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

/**
 * This is the adapter which populates the todays reminders from firebase
 */

public class TodayReminderHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.medicine_name) TextView tvMedicineName;
    @BindView(R.id.tv_time) TextView tvTime;
    @BindView(R.id.dosage) TextView tvDosage;
    @BindView(R.id.notes) TextView tvNotes;
    @BindView(R.id.iv_today_status) ImageView todayStatus;
    Reminder todayReminder;
    Context context;
    private TodayReminderHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(TodayReminderHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }


    public TodayReminderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }

    public void bind(Reminder reminder ){
        tvTime.setText(ReminderUtils.getTimeString(reminder.getReminderTime()));
        tvMedicineName.setText(reminder.getMedicineName());
        tvNotes.setText(reminder.getNotes());

        String appearance = reminder.getDosage()+ " ";
        if(!reminder.getMeasure().equalsIgnoreCase("None")){
            appearance = appearance+ reminder.getMeasure()+", ";
        }
        if(!reminder.getMedicineColor().equalsIgnoreCase("None")){
            appearance = appearance+ reminder.getMedicineColor()+", ";
        }
        if(!reminder.getShape().equalsIgnoreCase("None")){
            appearance = appearance+ reminder.getShape()+" ";
        }
        if(!reminder.getType().equalsIgnoreCase("None")){
            appearance = appearance+ reminder.getType()+" ";
        }

        tvDosage.setText(appearance);
        if(reminder.getState() == 1){
            todayStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
        }else if(reminder.getState() == 2){
            todayStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_black_24dp));
        }else if(reminder.getState() == 3){
            todayStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_close_black_24dp));
        }
    }

}
