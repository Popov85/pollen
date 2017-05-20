package ua.edu.zsmu.mfi.biology.pollen.weather;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import layout.PollenWidget;
import ua.edu.zsmu.mfi.biology.pollen.R;

/**
 * Created by Andrey on 19.05.2017.
 */

public class DownloadWeatherAsyncTask extends AsyncTask<String, Void, String> {

    private static final String APP_ID = "887cd404c38497947eb969593d0aae87";

    private static final String CITY= "Zaporizhzhya,ua";

    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q="+CITY+"&lang=en&&appid="+APP_ID;

    private RemoteViews remoteViews;

    private Context context;

    public DownloadWeatherAsyncTask(RemoteViews remoteViews, Context context) {
        this.remoteViews = remoteViews;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String s = null;
        try {
            remoteViews.setTextViewText(R.id.message, "Downloading weather data...");
            s = getContent();
            Log.i("DOWNLOADED", s);
            remoteViews.setTextViewText(R.id.message, "Downloaded...");
            remoteViews.setTextViewText(R.id.updated, "Востаннє оновлено: "+getDate());
        } catch (Exception e) {
            remoteViews.setTextViewText(R.id.message, "Failed to download...");
            Log.e("ERROR", e.getMessage());
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName watchWidget = new ComponentName(context, PollenWidget.class);
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        // TODO: calculate quotients here


    }

    private String getContent() throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(FORECAST_URL);
            HttpURLConnection c =(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(5000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return buf.toString();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        return format.format(new Date());
    }


}
