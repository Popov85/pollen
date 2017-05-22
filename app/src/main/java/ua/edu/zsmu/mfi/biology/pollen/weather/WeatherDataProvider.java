package ua.edu.zsmu.mfi.biology.pollen.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Calendar.getInstance;

/**
 * Created by Andrey on 18.05.2017.
 */

public final class WeatherDataProvider {

    /**
     * Parses json data and stows it Java map
     * @param weather data from remote API
     * @return Weather forecast data per day for 5 days ahead
     */
    public Map<Integer, DayWeather> getWeather5DaysForecast(List<Weather> weather) {
        Map<Integer, DayWeather> result = new HashMap<>();
        int dayCounter = 1;
        Calendar calendar = getInstance();
        calendar.setTime(weather.get(0).getDateTime());
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        List<Weather> dayWeather = new ArrayList<>();
        for (Weather w : weather) {
            Date date = w.getDateTime();
            calendar.setTime(date);
            int nextDay = calendar.get(Calendar.DAY_OF_MONTH);
            if (currentDayOfMonth==nextDay) {
                dayWeather.add(w);
                if (dayWeather.size()==8 && dayCounter==5) {
                        result.put(dayCounter, new DayWeather(dayWeather));
                }
            } else {
                result.put(dayCounter, new DayWeather(dayWeather));
                dayCounter++;
                currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                dayWeather = new ArrayList<>();
                dayWeather.add(w);
                Log.i("PUT_Weather", String.valueOf(w));
            }
        }
        return result;
    }

    /**
     * Gets weather data for 5 days, multiple weather data per day
     * @param json
     * @return
     * @throws JSONException
     */
    public List<Weather> getWeather5DaysForecastRaw(String json) throws JSONException{
        List<Weather> weather = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray list = jsonObject.getJSONArray("list");
        for (int i = 0; i < list.length(); i++) {
            JSONObject weatherObject = list.getJSONObject(i);

            long date = weatherObject.getLong("dt");

            JSONObject mainObject = weatherObject.getJSONObject("main");
            double pressure = mainObject.getDouble("pressure");
            double humidity = mainObject.getDouble("humidity");

            JSONObject windObject = weatherObject.getJSONObject("wind");
            double wind = windObject.getDouble("speed");

            JSONObject rainObject = weatherObject.optJSONObject("rain");
            double rain = windObject.optDouble("3h");
            if (Double.isNaN(rain)) rain = 0;

            Weather w = new Weather(convertDate(date),pressure, wind, rain, humidity);
            weather.add(w);
        }
        return weather;
    }

    // Converts UNIX UTC date format to Java Date object
    private Date convertDate(long unixUTCDate) {
        return new Date(unixUTCDate*1000L);
    }

}
