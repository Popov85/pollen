package ua.edu.zsmu.mfi.biology.pollen.weather;

/**
 * Created by Andrey on 23.05.2017.
 */

public class YesterdayWeather {

    private static final YesterdayWeather ourInstance = new YesterdayWeather();

    public static YesterdayWeather getInstance() {
        return ourInstance;
    }

    private Weather weather;

    private YesterdayWeather() {
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
