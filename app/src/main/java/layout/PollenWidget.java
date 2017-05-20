package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ua.edu.zsmu.mfi.biology.pollen.NormalPollenConcentrationDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.NormalConcentration;
import ua.edu.zsmu.mfi.biology.pollen.R;
import ua.edu.zsmu.mfi.biology.pollen.weather.DownloadWeatherAsyncTask;

public class PollenWidget extends AppWidgetProvider {

    private static final String UPD_CLICKED = "automaticWidgetSyncButtonClick";

    private NormalConcentration normalConcentrationStorage;

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        NormalPollenConcentrationDataProvider provider = new NormalPollenConcentrationDataProvider();
        //this.normalConcentrationStorage = provider.getDataFromGoogleDrive();
        this.normalConcentrationStorage = provider.getDataFromLocalFile(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (UPD_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollen_widget);

            //new RandomDemo().randomlyUpdate(views);
            // TODO work here to receive updated weather data

            DownloadWeatherAsyncTask downloadWeatherAsyncTask = new DownloadWeatherAsyncTask(views, context);
            downloadWeatherAsyncTask.execute();

            /*ComponentName watchWidget = new ComponentName(context, PollenWidget.class);
            appWidgetManager.updateAppWidget(watchWidget, views);*/
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollen_widget);
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

}

