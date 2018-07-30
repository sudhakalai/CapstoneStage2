package com.example.android.medicinereminder.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class ReminderWidgetService extends RemoteViewsService {


    public static void updateWidget(Context context, ArrayList<String> reminders) {
        Preference.saveRecipe(context, reminders);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ReminderWidgetProvider.class));
        ReminderWidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViews(getApplicationContext());
    }
}
