package ua.edu.zsmu.mfi.biology.pollen.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey on 24.05.2017.
 */

public class WeatherHistoryDataProvider {

    private static final String APP_ID = "c240265dd48b4cda9c981236172405";

    private static final String CITY= "Zaporizhzhya";
    // dt=2017-05-23
    private static final String HISTORY_URL = "http://http://api.apixu.com/v1/history.json?key="+APP_ID+"q="+CITY+"&dt=";


 /*   public Weather getWeatherYesterday(String date) throws Exception {
        DayWeather dayWeather = new DayWeather(getWeathersYesterday(date), 1);
        double avgPressure = dayWeather.getAVGPressure();
        return new Weather();
    }*/


    /**
     * @param date "2017-05-23"
     * @return
     * @throws JSONException
     */
    public List<Weather> getWeathersYesterday(String date) throws Exception {
        String json = RemoteJSONDownloader.downloadJSON(HISTORY_URL+date);
        return parseWeatherData(json);
    }

    // 24 entries per day
    public List<Weather> parseWeatherData(String json) throws JSONException {
        List<Weather> weather = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONObject forecastObject = jsonObject.getJSONObject("forecast");

        JSONArray forecastDayArray = forecastObject.getJSONArray("forecastday");
        JSONObject yesterdayObject = forecastDayArray.getJSONObject(0);
        JSONArray list = yesterdayObject.getJSONArray("hour");
        for (int i = 0; i < list.length(); i++) {
            JSONObject weatherObject = list.getJSONObject(i);
            String date = weatherObject.getString("time");
            Date d;
            try {
                d = convertDate(date);
            } catch (ParseException e) {
                Log.i("ERROR: ", e.getMessage());
                d = new Date();
            }
            // TODO convert to m/s
            double wind = weatherObject.getDouble("wind_kph");
            // TODO convert to mm of mercury
            double pressure = weatherObject.getDouble("pressure_mb");
            double rain = weatherObject.getDouble("precip_mm");
            double humidity = weatherObject.getDouble("humidity");

            Weather w = new Weather(d, pressure, wind, rain, humidity);
            weather.add(w);
        }
        return weather;
    }

    private Date convertDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(date);
    }

    public String downloadWeatherJSON() throws Exception {
        return RemoteJSONDownloader.downloadJSON(HISTORY_URL);
    }

}
