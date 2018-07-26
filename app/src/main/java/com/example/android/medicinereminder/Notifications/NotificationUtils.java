package com.example.android.medicinereminder.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.android.medicinereminder.MainActivity;
import com.example.android.medicinereminder.R;

/**
 * Created by Sudha on 25-Jul-18.
 */

public class NotificationUtils {
    private static final String MEDICINE_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int MEDICINE_REMINDER_PENDING_INTENT_ID = 3417;
    private static final int MEDICINE_REMINDER_NOTIFICATION_ID = 1138;


    public static void reminderUser(Context context, String  notifyText){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    MEDICINE_REMINDER_NOTIFICATION_CHANNEL_ID,
                    "Medicine reminder",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MEDICINE_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Medicine Reminder")
                .setContentText(notifyText)
                .setContentIntent(contentIntent(context))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(MEDICINE_REMINDER_NOTIFICATION_ID, mBuilder.build());

    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                MEDICINE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
