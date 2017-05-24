package ua.edu.zsmu.mfi.biology.pollen.weather;

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

public final class WeatherForecastDataProvider {

    private static final String APP_ID = "887cd404c38497947eb969593d0aae87";

    private static final String CITY= "Zaporizhzhya,ua";

    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q="+CITY+"&lang=en&&appid="+APP_ID;

    public static final double MM_OF_MERCURY_CONST = 0.750061561303;

    public Map<Integer, DayWeather> getWeather5DaysForecast(String json) throws JSONException {
        return getWeather5DaysForecast(parseWeatherData(json));
    }

    /**
     * Parses json data and stows it Java map
     * @param weathers data from remote API
     * @return Weather forecast data per day for 5 days ahead
     */
    private Map<Integer, DayWeather> getWeather5DaysForecast(List<Weather> weathers) {
        Calendar calendar = getInstance();
        Map<Integer, DayWeather> result = new HashMap<>();
        int day = 1;
        int currentDayOfMonth = getDay(calendar, weathers.get(0).getDateTime());
        List<Weather> dayWeather = new ArrayList<>();
        for (Weather weather : weathers) {
            int nextDay = getDay(calendar, weather.getDateTime());
            if (currentDayOfMonth!=nextDay) {
                result.put(day++, new DayWeather(dayWeather, day));
                currentDayOfMonth = nextDay;
                dayWeather = new ArrayList<>();
            }
            dayWeather.add(weather);
        }
        result.put(day, new DayWeather(dayWeather, day));
        return result;
    }

    private int getDay(Calendar calendar, Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

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

    public String downloadWeatherJSON() throws Exception {
        return RemoteJSONDownloader.downloadJSON(FORECAST_URL);
    }

}
