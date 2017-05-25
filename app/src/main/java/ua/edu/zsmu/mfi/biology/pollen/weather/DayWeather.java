package ua.edu.zsmu.mfi.biology.pollen.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 18.05.2017.
 */

public class DayWeather {

    // Day of year (order number)
    private int day;

    private List<Weather> weather = new ArrayList<>();

    public DayWeather(List<Weather> weather, int day) {
        this.weather = weather;
        this.day = day;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public int getDay() {
        return day;
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
        double sumWind = 0;
        int counter = 0;
        for (Weather w : weather) {
            sumWind+=w.getWind();
            counter++;
        }
        return sumWind/counter;
    }

    public double getAVGHumidity() {
        double sumHumidity = 0;
        int counter = 0;
        for (Weather w : weather) {
            sumHumidity+=w.getHumidity();
            counter++;
        }
        return sumHumidity/counter;
    }

    public double getTotalPrecipitation() {
        double sumRain = 0;
        for (Weather w : weather) {
            sumRain+=w.getRain();
        }
        return sumRain;
    }
}
