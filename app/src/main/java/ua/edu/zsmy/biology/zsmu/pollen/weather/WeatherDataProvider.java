package ua.edu.zsmy.biology.zsmu.pollen.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 18.05.2017.
 */

public final class WeatherDataProvider {

    private static final String APP_ID = "887cd404c38497947eb969593d0aae87";

    private static final String CITY= "Zaporizhzhya,ua";

    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q="+CITY+"&lang=en&&appid="+APP_ID;

    private static final String YESTERDAY_URL = "http://history.openweathermap.org/data/2.5/history/city?q="+CITY+"&type=hour&start={start}&cnt=1";

    // TODO
    public List<DayWeather> getWeather5DaysForecast() {
        return new ArrayList<>();
    }

    // TODO
    public List<DayWeather> getYesterdayWeather() {
        return new ArrayList<>();
    }

    private String getWeather5DaysForecastJSON() {
        return "";
    }

    private String getYesterdayWeatherJSON() {
        return "";
    }
}
