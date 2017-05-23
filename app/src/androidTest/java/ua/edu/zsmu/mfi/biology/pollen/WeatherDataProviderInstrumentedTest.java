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
        List<Weather> weather = weatherDataProvider.parseWeatherData(json);
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
                .getWeather5DaysForecast(json);

        assertTrue(!weather.isEmpty());

        Log.i("NOT_EMPTY","NOT_EMPTY");

        DayWeather today = weather.get(1);
        assertTrue(today.getWeather().size()>1);

        DayWeather tomorrow = weather.get(2);
        Log.i("tomorrow_size= ",String.valueOf(tomorrow.getWeather().size()));
        assertTrue(tomorrow.getWeather().size()==8);

        DayWeather day3 = weather.get(3);
        Log.i("3_size= ",String.valueOf(day3.getWeather().size()));
        assertTrue(day3.getWeather().size()==8);

        DayWeather day4 = weather.get(4);
        Log.i("4_size= ",String.valueOf(day4.getWeather().size()));
        assertTrue(day4.getWeather().size()==8);

        DayWeather day5 = weather.get(5);
        Log.i("5_size= ",String.valueOf(day5.getWeather().size()));
        assertTrue(day5.getWeather().size()==8);
    }

}
