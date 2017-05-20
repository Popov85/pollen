package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import ua.edu.zsmu.mfi.biology.pollen.weather.DayWeather;
import ua.edu.zsmu.mfi.biology.pollen.weather.Weather;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherDataProvider;

import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 20.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class WeatherDataProviderInstrumentedTest {

    @Test
    public void itShouldCorrectlyParseWeatherJSON() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String json = localFileDataProvider.getJSONWeatherForecastFileContent(context);

        WeatherDataProvider weatherDataProvider = new WeatherDataProvider();
        List<Weather> weather = weatherDataProvider.getWeather5DaysForecastRaw(json);
        Log.i("WeatherList:", weather.toString());
        assertTrue(!weather.isEmpty());
    }

    @Test
    public void itShouldCorrectlyCreateWeatherPerDays() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String json = localFileDataProvider
                .getJSONWeatherForecastFileContent(context);

        WeatherDataProvider weatherDataProvider = new WeatherDataProvider();
        Map<Integer, DayWeather> weather = weatherDataProvider
                .getWeather5DaysForecast(weatherDataProvider
                        .getWeather5DaysForecastRaw(json));

        assertTrue(!weather.isEmpty());

        Log.i("NOT_EMPTY","NOT_EMPTY");

        DayWeather today = weather.get(1);
        assertTrue(today.getWeather().size()>1);

        DayWeather tomorrow = weather.get(2);
        assertTrue(tomorrow.getWeather().size()==8);

        DayWeather day3 = weather.get(3);
        assertTrue(day3.getWeather().size()==8);

        DayWeather day4 = weather.get(4);
        assertTrue(day4.getWeather().size()==8);

        DayWeather day5 = weather.get(5);
        assertTrue(day5.getWeather().size()==8);
    }

}
