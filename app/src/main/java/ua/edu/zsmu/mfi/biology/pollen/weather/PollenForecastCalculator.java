package ua.edu.zsmu.mfi.biology.pollen.weather;

import java.util.HashMap;
import java.util.Map;

import ua.edu.zsmu.mfi.biology.pollen.NormalConcentration;

/**
 * Created by Andrey on 20.05.2017.
 */

public class PollenForecastCalculator {

    private NormalConcentration normalConcentrationStorage;

    private Map<String, DayWeather> weatherForecast;

    public PollenForecastCalculator(Map<String, DayWeather> weatherForecast, NormalConcentration normalConcentration) {
        this.weatherForecast = weatherForecast;
        this.normalConcentrationStorage = normalConcentration;
    }

    /**
     * Gets pollen concentration forecast for 3 days
     * @return Today: value1, Tomorrow: value2, AfterTomorrow: value3
     */
    public Map<String, Double> getPollenForecast() {

        return new HashMap<>();
    }

    private double getK1(double pressurePreviousDay, double pressureNextDay) {
        if ((pressurePreviousDay-pressureNextDay)>0) {
            return ((pressurePreviousDay-pressureNextDay)*15+10)*0.12;
        } else {
            return ((pressureNextDay-pressurePreviousDay)*20+10)*0.12;
        }
    }

    private double getK2(double windPreviousDay, double windNextDay) {
        if ((windNextDay-windPreviousDay)>0) {
            return 1.3;
        } else {
            return 0.8;
        }
    }

    // 0 - no rain during the day
    private double getK3(double rainDay) {
        if (rainDay == 0) return 1;
        if (rainDay > 0 && rainDay < 10) return 1.5;
        return 0.5;
    }

    private double getK4(double humidityPreviousDay, double humidityNextDay) {
        if (humidityNextDay>80) {
            return 0.3*(humidityPreviousDay/humidityNextDay);
        } else {
            return 1.5*(humidityPreviousDay/humidityNextDay);
        }
    }
}
