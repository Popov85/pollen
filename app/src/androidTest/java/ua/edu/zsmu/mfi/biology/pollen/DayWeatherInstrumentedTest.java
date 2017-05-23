package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;

import ua.edu.zsmu.mfi.biology.pollen.weather.DayWeather;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherDataProvider;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrey on 23.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class DayWeatherInstrumentedTest {

    private static double[] PRESSURE_OF_DAY_20170520 = {1008.09, 1007.36, 1006.98, 1006.64, 1007.23, 1006.82};

    private static double[] WIND_OF_DAY_20170520 = {2.56, 2.46, 2.2, 1.87, 1.07, 2.57};

    @Before
    public void before() {

    }

    @Test
    public void itShouldCorrectlyCalculateAVGPressure() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        WeatherDataProvider weatherDataProvider = new WeatherDataProvider();

        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String json = localFileDataProvider.getJSONWeatherForecastFileContent(context);

        Map<Integer, DayWeather> weatherForecast =
                weatherDataProvider.getWeather5DaysForecast(json);

        DayWeather today = weatherForecast.get(1);

        double avgPressureActual = today.getAVGPressure();

        assertEquals(getAVGPressureExpected(), avgPressureActual, 0.001);
    }




    private double getAVGPressureExpected() {
        double sum = 0;
        int counter = 0;
        for (double v : PRESSURE_OF_DAY_20170520) {
            sum+=v;
            counter++;
        }
        return sum/counter;
    }

}
