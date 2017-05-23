package ua.edu.zsmu.mfi.biology.pollen.weather;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ua.edu.zsmu.mfi.biology.pollen.NormalConcentration;

/**
 * Created by Andrey on 20.05.2017.
 */

public class PollenForecastEvaluator {

    private NormalConcentration normalConcentrationStorage;

    private Map<Integer, DayWeather> weatherForecast;

    private YesterdayWeather yesterdayWeather = YesterdayWeather.getInstance();

    private Calendar calendar = Calendar.getInstance();

    public PollenForecastEvaluator(Map<Integer, DayWeather> weatherForecast, NormalConcentration normalConcentration) {
        this.weatherForecast = weatherForecast;
        this.normalConcentrationStorage = normalConcentration;
    }

    /**
     * Gets pollen concentration forecast for 3 days
     * @return 1 (Today): value1, 2 (Tomorrow): value2, 3 (AfterTomorrow): value3
     */
    public Map<Integer, Pollen> getPollenForecast() {

        Map<Integer, Pollen> pollenForecast = new HashMap<>();

        double previousPressure = yesterdayWeather.getWeather().getPressure();
        double previousWind =  yesterdayWeather.getWeather().getWind();
        double previousHumidity = yesterdayWeather.getWeather().getHumidity();
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

     /*   DayWeather today = weatherForecast.get(1);
        double normalPollenToday = normalConcentrationStorage.getNormalConcentration(today.getDay());
        double todayPressure = today.getAVGPressure();
        double todayWind = today.getAVGWind();
        double todayHumidity = today.getAVGHumidity();
        double expectedPollenToday = normalPollenToday
                *getK1(yesterdayWeather.getWeather().getPressure(), todayPressure)
                *getK2(yesterdayWeather.getWeather().getWind(), todayWind)
                *getK3(today.getTotalPrecipitation())
                *getK4(yesterdayWeather.getWeather().getHumidity(), todayHumidity);
        int expectedLevelToday = calculateLevel(expectedPollenToday);
        Pollen pollen1 = new Pollen(expectedPollenToday, expectedLevelToday);
        pollenForecast.put(1, pollen1);


        DayWeather tomorrow = weatherForecast.get(2);
        double normalPollenTomorrow = normalConcentrationStorage.getNormalConcentration(tomorrow.getDay());
        double tomorrowPressure = tomorrow.getAVGPressure();
        double tomorrowWind = tomorrow.getAVGWind();
        double tomorrowHumidity = tomorrow.getAVGHumidity();
        double expectedPollenTomorrow = normalPollenTomorrow
                *getK1(todayPressure, tomorrowPressure)
                *getK2(todayWind, tomorrowWind)
                *getK3(tomorrow.getTotalPrecipitation())
                *getK4(todayHumidity, tomorrowHumidity);
        int expectedLevelTomorrow = calculateLevel(expectedPollenTomorrow);
        Pollen pollen2 = new Pollen(expectedPollenTomorrow, expectedLevelTomorrow);
        pollenForecast.put(2, pollen2);*/

        return pollenForecast;
    }

    private Pollen getExpectedPollen(int day, double previousPressure,
                                     double previousWind, double previousHumidity) {
        DayWeather today = weatherForecast.get(day);
        double normalPollen = 100; //normalConcentrationStorage.getNormalConcentration(today.getDay());
        double expectedPollen = normalPollen
                *getK1(previousPressure, today.getAVGPressure())
                *getK2(previousWind, today.getAVGWind())
                *getK3(today.getTotalPrecipitation())
                *getK4(previousHumidity, today.getAVGHumidity());
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
        if (value<=10) return 1;
        if (value>10 &&value<=20) return 2;
        if (value>20 &&value<=70) return 3;
        if (value>70 &&value<=199) return 4;
        return 5;
    }
}
