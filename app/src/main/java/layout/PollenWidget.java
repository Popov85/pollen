package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import ua.edu.zsmu.mfi.biology.pollen.pollen.NormalPollenConcentrationDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.pollen.NormalConcentration;
import ua.edu.zsmu.mfi.biology.pollen.R;
import ua.edu.zsmu.mfi.biology.pollen.pollen.PollenForecastAsyncTask;

public class PollenWidget extends AppWidgetProvider {

    private static final String UPD_CLICKED = "automaticWidgetSyncButtonClick";

    private NormalConcentration normalConcentrationStorage;

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        NormalPollenConcentrationDataProvider provider = new NormalPollenConcentrationDataProvider();
        //this.normalConcentrationStorage = provider.getDataFromGoogleDrive();
        this.normalConcentrationStorage = provider.getDataFromLocalFile(context);
        // TODO try to update right now programmatically (optionally)
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (UPD_CLICKED.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollen_widget);
            //new RandomDemo().randomlyUpdate(views);
            PollenForecastAsyncTask pollenForecastAsyncTask =
                    new PollenForecastAsyncTask(views, context, normalConcentrationStorage);
            pollenForecastAsyncTask.execute();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollen_widget);
        views.setOnClickPendingIntent(R.id.updateImageButton, getPendingSelfIntent(context, UPD_CLICKED));
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private  PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}

