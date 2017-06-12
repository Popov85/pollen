package ua.edu.zsmu.mfi.biology.pollen.pollen;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    // private Exception exception;

    // https://stackoverflow.com/questions/14211755/how-to-gracefully-handle-exception-inside-asynctask-in-android

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
            String err = (e.getMessage()==null)?"Failed to download":e.getMessage();
            Log.e("ERROR", err);
            return null;
        }
        return new WeatherWrapper(forecast, history);
    }

    @Override
    protected void onPostExecute(WeatherWrapper wrapper) {
        if (wrapper==null) {
            Log.e("ERROR", "NULL REF");
            return;
        }
        try {
            boolean mode = getMode();
            YesterdayWeather.getInstance().setWeather(weatherHistoryDataProvider.getWeatherYesterday(wrapper.getHistory()));
            Log.i("Set yesterday weather: ", "Success!");
            PollenForecastEvaluator pollenForecastEvaluator =
                    new PollenForecastEvaluator(weatherForecastDataProvider
                            .getWeather5DaysForecast(wrapper.getForecast()), normalConcentration, mode);
            Log.i("Got evaluator", "Success!");
            Map<Integer, Pollen> forecast;
            if (mode) {
                widgetView.setMode("on");
                forecast = pollenForecastEvaluator.getPollenForecast(getStartDay(), getDaysForecast());
            } else {
                widgetView.setMode("off");
                forecast = pollenForecastEvaluator.getDemoPollenForecast();
            }
            Log.i("Calculated forecast", "Success! :"+forecast);
            widgetView.setForecast(forecast);
            widgetView.setMessage("Updated...");
            widgetView.setUpdated("Востаннє оновлено: "+getDate());
        } catch (Exception e) {
            widgetView.setError("Failure processing data...");
            String err = (e.getMessage()==null)?"Failure processing data":e.getMessage();
            Log.e("onPostExecuteERROR", err);
        }
    }

    private int getStartDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    private int getDaysForecast() {
        int maxDay = normalConcentration.getEndDay();
        // last day
        if (maxDay-getStartDay()==0) return 1;
        // last but one day
        if (maxDay-getStartDay()==1) return 2;
        return 3;
    }

    // Demo or real-time modes
    private boolean getMode() {
        int dayOfYear = getStartDay();
        // Check if this day is within accepted period
        int minDay = normalConcentration.getStartDay();
        int maxDay = normalConcentration.getEndDay();
        if (dayOfYear>=minDay && dayOfYear<=maxDay) return true;
        return false;
    }


    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("EET"));
        return format.format(new Date());
    }
}
