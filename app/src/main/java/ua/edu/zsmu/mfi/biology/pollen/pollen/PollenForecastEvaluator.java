package ua.edu.zsmu.mfi.biology.pollen.pollen;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import ua.edu.zsmu.mfi.biology.pollen.weather.DayWeather;
import ua.edu.zsmu.mfi.biology.pollen.weather.YesterdayWeather;

/**
 * Created by Andrey on 20.05.2017.
 */

public class PollenForecastEvaluator {

    private NormalConcentration normalConcentrationStorage;

    private Map<Integer, DayWeather> weatherForecast;

    private YesterdayWeather yesterdayWeather = YesterdayWeather.getInstance();

    public PollenForecastEvaluator(Map<Integer, DayWeather> weatherForecast, NormalConcentration normalConcentration) {
        this.weatherForecast = weatherForecast;
        this.normalConcentrationStorage = normalConcentration;
    }

    /**
     * Gets pollen concentration forecast for 3 days
     * @return 1 (Today): value1, 2 (Tomorrow): value2, 3 (AfterTomorrow): value3
     */
    public Map<Integer, Pollen> getPollenForecast() {

        // TODO work here to get mode
        Map<Integer, Pollen> pollenForecast = new HashMap<>();

        double previousPressure = yesterdayWeather.getWeather().getAVGPressure();
        double previousWind =  yesterdayWeather.getWeather().getAVGWind();
        double previousHumidity = yesterdayWeather.getWeather().getAVGHumidity();
        for (int i = 1; i <=3 ; i++) {
            Pollen p = getExpectedPollen(i, previousPressure, previousWind, previousHumidity);
            pollenForecast.put(i, p);
            if (i<3) {
                DayWeather day = weatherForecast.get(i);
                previousPressure = day.getAVGPressure();
                previousWind = day.getAVGWind();
                previousHumidity = day.getAVGHumidity();
            }
        }
        return pollenForecast;
    }

    private Pollen getExpectedPollen(int day, double previousPressure,
                                     double previousWind, double previousHumidity) {
        DayWeather today = weatherForecast.get(day);
        double normalPollen = 100; //normalConcentrationStorage.getNormalConcentration(today.getDay());

        double k1 = getK1(previousPressure, today.getAVGPressure());
        Log.i("k1=", k1+"");

        double k2 = getK2(previousWind, today.getAVGWind());
        Log.i("k2=", k2+"");

        double k3 = getK3(today.getTotalPrecipitation());
        Log.i("k3=", k3+"");

        double k4 = getK4(previousHumidity, today.getAVGHumidity());
        Log.i("k4=", k4+"");

        Log.i("day=", day+"");
        double production = k1*k2*k3*k4;
        Log.i("production=", production+"");

        double expectedPollen = normalPollen*k1*k2*k3*k4;
        Log.i("day=", day+"");
        Log.i("expectedPollen=", expectedPollen+"");

        int expectedLevel = calculateLevel(expectedPollen);
        Pollen pollen = new Pollen(expectedPollen, expectedLevel);
        return pollen;
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

    private int calculateLevel(double value) {
        if (value<=19) return 1;
        if (value>19 &&value<=99) return 2;
        if (value>99 &&value<=399) return 3;
        if (value>399 &&value<=999) return 4;
        return 5;
    }
}
