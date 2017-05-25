package ua.edu.zsmu.mfi.biology.pollen.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey on 25.05.2017.
 */

public final class ForecastWeatherJSONParser {

    public static final double MM_OF_MERCURY_CONST = 0.750061561303;

    /**
     * Gets weather data for 5 days, multiple weather data per day
     * @param json
     * @return
     * @throws JSONException
     */
    public List<Weather> parseWeatherData(String json) throws JSONException{
        List<Weather> weather = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray list = jsonObject.getJSONArray("list");
        for (int i = 0; i < list.length(); i++) {
            JSONObject weatherObject = list.getJSONObject(i);

            long date = weatherObject.getLong("dt");

            JSONObject mainObject = weatherObject.getJSONObject("main");
            double pressure = mainObject.getDouble("pressure")*MM_OF_MERCURY_CONST;
            double humidity = mainObject.getDouble("humidity");

            JSONObject windObject = weatherObject.getJSONObject("wind");
            double wind = windObject.getDouble("speed");

            JSONObject rainObject = weatherObject.optJSONObject("rain");
            double rain = 0;
            if (rainObject!=null) {
                rain = rainObject.optDouble("3h");
                if (Double.isNaN(rain)) rain = 0;
            }
            Weather w = new Weather(convertDate(date), pressure, wind, rain, humidity);
            weather.add(w);
        }
        return weather;
    }

    // Converts UNIX UTC date format to Java Date object
    private Date convertDate(long unixUTCDate) {
        return new Date(unixUTCDate*1000L);
    }

}
