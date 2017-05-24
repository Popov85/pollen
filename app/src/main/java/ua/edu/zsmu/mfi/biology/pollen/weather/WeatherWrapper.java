package ua.edu.zsmu.mfi.biology.pollen.weather;

/**
 * Created by Andrey on 24.05.2017.
 */

public final class WeatherWrapper {

    private String forecast;
    private String history;

    public WeatherWrapper(String forecast, String history) {
        this.forecast = forecast;
        this.history = history;
    }

    public String getForecast() {
        return forecast;
    }

    public String getHistory() {
        return history;
    }
}
