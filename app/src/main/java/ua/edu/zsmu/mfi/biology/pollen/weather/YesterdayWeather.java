package ua.edu.zsmu.mfi.biology.pollen.weather;

/**
 * Created by Andrey on 23.05.2017.
 */

public class YesterdayWeather {

    private static final YesterdayWeather ourInstance = new YesterdayWeather();

    public static YesterdayWeather getInstance() {
        return ourInstance;
    }

    private DayWeather weather;

    private YesterdayWeather() {
    }

    public DayWeather getWeather() {
        return weather;
    }

    public void setWeather(DayWeather weather) {
        this.weather = weather;
    }
}
