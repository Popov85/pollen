package ua.edu.zsmu.mfi.biology.pollen.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 18.05.2017.
 */

public class DayWeather {

    private List<Weather> weather = new ArrayList<>();

    public DayWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public double getAVGPressure() {
        double sumPressure = 0;
        int counter = 0;
        for (Weather w : weather) {
            sumPressure+=w.getPressure();
            counter++;
        }
        return sumPressure/counter;
    }

    public double getAVGWind() {
        return 0d;
    }

    public double getAVGHumidity() {
        return 0d;
    }

    public double getTotalPrecipitation() {
        return 0d;
    }
}
