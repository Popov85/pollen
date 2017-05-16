package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import java.util.Random;
import ua.edu.zsmy.biology.zsmu.pollen.R;

public class PollenWidget extends AppWidgetProvider {

    private static final String UPD_CLICKED    = "automaticWidgetSyncButtonClick";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (UPD_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollen_widget);
            /*Random rd=new Random();
            views.setTextViewText(R.id.appwidget_text, "Updated: "+rd.nextInt(10));*/
            randomlyUpdate(views);

            ComponentName watchWidget = new ComponentName(context, PollenWidget.class);
            appWidgetManager.updateAppWidget(watchWidget, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollen_widget);
        views.setTextViewText(R.id.appwidget_text, "Концентрація пилку у повітрі");
        views.setOnClickPendingIntent(R.id.updateImageButton, getPendingSelfIntent(context, UPD_CLICKED));
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    protected  PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private void randomlyUpdate(RemoteViews views) {

        Random rd=new Random();
        int level = rd.nextInt(5);
        views.setTextViewText(R.id.today_level, "Рівень "+level);
        try {
            views.setInt(R.id.today_container, "setBackgroundResource", getDrawable(level));
        } catch (Exception e) {
            e.printStackTrace();
            views.setInt(R.id.today_container, "setBackgroundResource", R.drawable.danger_1);
        }

        level = rd.nextInt(5);
        views.setTextViewText(R.id.tomorrow_level, "Рівень "+level);
        try {
            views.setInt(R.id.tomorrow_container, "setBackgroundResource", getDrawable(level));
        } catch (Exception e) {
            e.printStackTrace();
            views.setInt(R.id.tomorrow_container, "setBackgroundResource", R.drawable.danger_1);
        }

        level = rd.nextInt(5);
        views.setTextViewText(R.id.after_level, "Рівень "+level);
        try {
            views.setInt(R.id.after_container, "setBackgroundResource", getDrawable(level));
        } catch (Exception e) {
            e.printStackTrace();
            views.setInt(R.id.after_container, "setBackgroundResource", R.drawable.danger_1);
        }

    }

    private int getDrawable(int level) {
        switch (level) {
            case 1: return R.drawable.danger_1;
            case 2: return R.drawable.danger_2;
            case 3: return R.drawable.danger_3;
            case 4: return R.drawable.danger_4;
            case 5: return R.drawable.danger_5;
        }
        throw new RuntimeException();
    }

/*    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }*/
}

