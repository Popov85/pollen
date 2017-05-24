package ua.edu.zsmu.mfi.biology.pollen;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import layout.PollenWidget;
import ua.edu.zsmu.mfi.biology.pollen.weather.Weather;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherForecastDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherHistoryDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherWrapper;
import ua.edu.zsmu.mfi.biology.pollen.weather.YesterdayWeather;

/**
 * Created by Andrey on 19.05.2017.
 */

public class PollenForecastAsyncTask extends AsyncTask<String, Void, WeatherWrapper> {

    private RemoteViews remoteViews;

    private Context context;

    private NormalConcentration normalConcentration;

    private WeatherForecastDataProvider weatherForecastDataProvider;

    private WeatherHistoryDataProvider weatherHistoryDataProvider;

    public PollenForecastAsyncTask(RemoteViews remoteViews, Context context,
                                   NormalConcentration normalConcentration) {
        this.remoteViews = remoteViews;
        this.context = context;
        this.normalConcentration = normalConcentration;
        this.weatherForecastDataProvider = new WeatherForecastDataProvider();
        this.weatherHistoryDataProvider = new WeatherHistoryDataProvider();
    }

    @Override
    protected WeatherWrapper doInBackground(String... params) {
        String forecast = null;
        String history = null;
        try {
            setMessage("Downloading forecast...");
            forecast = weatherForecastDataProvider.downloadWeatherJSON();
            setMessage("Downloading history...");
            history = weatherHistoryDataProvider.downloadWeatherJSON();
        } catch (Exception e) {
            setMessage("Failed to download...");
            Log.e("ERROR", e.getMessage());
        }
        return new WeatherWrapper(forecast, history);
    }

    @Override
    protected void onPostExecute(WeatherWrapper wrapper) {
        setMessage("Downloaded...");
        try {
            // TODO set yesterday data
            //YesterdayWeather.getInstance().setWeather(weatherHistoryDataProvider.getWeatherYesterday(wrapper.getHistory()));
            Weather weather = new Weather(new Date(), 760d, 4d, 0.5d, 75d);
            YesterdayWeather.getInstance().setWeather(weather);

            PollenForecastEvaluator pollenForecastEvaluator =
                    new PollenForecastEvaluator(weatherForecastDataProvider
                            .getWeather5DaysForecast(wrapper.getForecast()), normalConcentration);
            Map<Integer, Pollen> forecast = pollenForecastEvaluator.getPollenForecast();
            setForecast(forecast);
        } catch (Exception e) {
            setMessage("Failure processing data...");
            Log.e("ERROR", e.getMessage());
        }
        setMessage("Updated...");
        setUpdated("Востаннє оновлено: "+getDate());
    }

    private void setForecast(Map<Integer, Pollen> forecast) {
        Pollen today = forecast.get(1);
        remoteViews.setTextViewText(R.id.today_level, "Рівень " + today.getLevel());
        remoteViews.setTextViewText(R.id.today_value, String.format("%.1f", today.getValue())+" од.");
        remoteViews.setInt(R.id.today_container, "setBackgroundResource", getDrawable(today.getLevel()));

        Pollen tomorrow = forecast.get(2);
        remoteViews.setTextViewText(R.id.tomorrow_level, "Рівень " + tomorrow.getLevel());
        remoteViews.setTextViewText(R.id.tomorrow_value, String.format("%.1f", tomorrow.getValue())+" од.");
        remoteViews.setInt(R.id.tomorrow_container, "setBackgroundResource", getDrawable(tomorrow.getLevel()));

        Pollen after = forecast.get(3);
        remoteViews.setTextViewText(R.id.after_level, "Рівень " + after.getLevel());
        remoteViews.setTextViewText(R.id.after_value, String.format("%.1f", after.getValue())+" од.");
        remoteViews.setInt(R.id.after_container, "setBackgroundResource", getDrawable(after.getLevel()));

        updateView();
    }

    private void setMessage(String message) {
        remoteViews.setTextViewText(R.id.message, message);
        updateView();
    }

    private void setUpdated(String updated) {
        remoteViews.setTextViewText(R.id.updated, updated);
        updateView();
    }

    private void updateView() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName watchWidget = new ComponentName(context, PollenWidget.class);
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        return format.format(new Date());
    }

    private int getDrawable(int level) {
        switch (level) {
            case 1:
                return R.drawable.danger_1;
            case 2:
                return R.drawable.danger_2;
            case 3:
                return R.drawable.danger_3;
            case 4:
                return R.drawable.danger_4;
            case 5:
                return R.drawable.danger_5;
        }
        throw new RuntimeException();
    }
}
