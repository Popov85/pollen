package ua.edu.zsmu.mfi.biology.pollen.pollen;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import ua.edu.zsmu.mfi.biology.pollen.view.WidgetView;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherForecastDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherHistoryDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherWrapper;
import ua.edu.zsmu.mfi.biology.pollen.weather.YesterdayWeather;

/**
 * Created by Andrey on 19.05.2017.
 */

public class PollenForecastAsyncTask extends AsyncTask<String, Void, WeatherWrapper> {

    private WidgetView widgetView;

    private NormalConcentration normalConcentration;

    private WeatherForecastDataProvider weatherForecastDataProvider;

    private WeatherHistoryDataProvider weatherHistoryDataProvider;

    public PollenForecastAsyncTask(RemoteViews remoteViews, Context context,
                                   NormalConcentration normalConcentration) {
        this.normalConcentration = normalConcentration;
        this.widgetView = new WidgetView(context, remoteViews);
        this.weatherForecastDataProvider = new WeatherForecastDataProvider();
        this.weatherHistoryDataProvider = new WeatherHistoryDataProvider();
    }

    @Override
    protected WeatherWrapper doInBackground(String... params) {
        String forecast = null;
        String history = null;
        try {
            widgetView.setMessage("Downloading forecast...");
            forecast = weatherForecastDataProvider.downloadWeatherJSON();
            widgetView.setMessage("Downloading history...");
            history = weatherHistoryDataProvider.downloadWeatherJSON();
        } catch (Exception e) {
            widgetView.setError("Failed to download...");
            Log.e("ERROR", e.getMessage());
        }
        return new WeatherWrapper(forecast, history);
    }

    @Override
    protected void onPostExecute(WeatherWrapper wrapper) {
        widgetView.setMessage("Downloaded...");
        try {
            YesterdayWeather.getInstance().setWeather(weatherHistoryDataProvider.getWeatherYesterday(wrapper.getHistory()));
            PollenForecastEvaluator pollenForecastEvaluator =
                    new PollenForecastEvaluator(weatherForecastDataProvider
                            .getWeather5DaysForecast(wrapper.getForecast()), normalConcentration);
            Map<Integer, Pollen> forecast = pollenForecastEvaluator.getPollenForecast();
            widgetView.setForecast(forecast);
        } catch (Exception e) {
            widgetView.setError("Failure processing data...");
            Log.e("ERROR", e.getMessage());
        }
        widgetView.setMessage("Updated...");
        widgetView.setUpdated("Востаннє оновлено: "+getDate());
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        return format.format(new Date());
    }
}
