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
 * Created by Andrey on 25.05.2017.
 */

public final class YesterdayWeatherJSONParser {

    private static final double MS = 10/36d;

    private static final double MB = 1.3332239;

    // 24 entries per day. Example of data is in history.json
    public List<Weather> parseWeatherData(String json) throws JSONException {
        List<Weather> weather = new ArrayList<>();

        JSONArray list = new JSONObject(json)
                .getJSONObject("forecast")
                .getJSONArray("forecastday")
                .getJSONObject(0)
                .getJSONArray("hour");

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
            Log.i("MY_MS=", MS+"");
            double wind = weatherObject.getDouble("wind_kph")*MS;
            double pressure = weatherObject.getDouble("pressure_mb")/MB;
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
}
