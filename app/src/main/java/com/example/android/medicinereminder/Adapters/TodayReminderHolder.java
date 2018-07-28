package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
        tvTime.setText(String.valueOf(reminder.getReminderTime()));
        tvMedicineName.setText(reminder.getMedicineName());
        tvNotes.setText(reminder.getNotes());
        tvDosage.setText(reminder.getDosage() + reminder.getMeasure() + ", " + reminder.getMedicineColor() + ", "+ reminder.getShape()+ " "+ reminder.getType());
        if(reminder.getState() == 1){
            todayStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
        }else if(reminder.getState() == 2){
            todayStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_black_24dp));
        }else if(reminder.getState() == 3){
            todayStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_close_black_24dp));
        }
    }

}
