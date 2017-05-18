package ua.edu.zsmy.biology.zsmu.pollen.weather;

import java.util.List;

/**
 * Created by Andrey on 18.05.2017.
 */

public class WeatherForecastProvider {

    private List<DayWeather> weatherData;

    public WeatherForecastProvider() {
        this.weatherData = new WeatherDataProvider().getWeather5DaysForecast();
    }


}
