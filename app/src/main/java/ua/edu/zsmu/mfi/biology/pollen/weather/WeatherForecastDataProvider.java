package ua.edu.zsmu.mfi.biology.pollen.weather;

import org.json.JSONException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.edu.zsmu.mfi.biology.pollen.util.RemoteJSONDownloader;

import static java.util.Calendar.getInstance;

/**
 * Created by Andrey on 18.05.2017.
 */

public final class WeatherForecastDataProvider {

    private static final String APP_ID = "887cd404c38497947eb969593d0aae87";

    private static final String CITY= "Zaporizhzhya,ua";

    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q="+CITY+"&lang=en&&appid="+APP_ID;

    public String downloadWeatherJSON() throws Exception {
        return RemoteJSONDownloader.downloadJSON(FORECAST_URL);
    }

    public Map<Integer, DayWeather> getWeather5DaysForecast(String json) throws JSONException {
        return getWeather5DaysForecast(new ForecastWeatherJSONParser().parseWeatherData(json));
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
}
