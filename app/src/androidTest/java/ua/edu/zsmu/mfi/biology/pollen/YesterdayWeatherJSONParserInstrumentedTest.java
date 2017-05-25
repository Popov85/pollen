package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import ua.edu.zsmu.mfi.biology.pollen.util.LocalFileDataProvider;
import ua.edu.zsmu.mfi.biology.pollen.weather.DayWeather;
import ua.edu.zsmu.mfi.biology.pollen.weather.Weather;
import ua.edu.zsmu.mfi.biology.pollen.weather.YesterdayWeatherJSONParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 25.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class YesterdayWeatherJSONParserInstrumentedTest {

    private static double[] PRESSURE_OF_YESTERDAY_20170524 = {1013.0, 1014.0, 1014.0, 1014.0, 1014.0, 1014.0, 1014.0, 1014.0, 1015.0, 1015.0, 1016.0, 1017.0,
                                                                1018.0, 1018.0, 1018.0, 1018.0, 1018.0, 1018.0, 1018.0, 1018.0, 1018.0, 1018.0, 1019.0, 1019.0};

    private static double[] WIND_OF_YESTERDAY_20170524 = {16.9, 17.2, 17.4, 17.6, 18.5, 19.3, 20.2, 20.5, 20.9, 21.2, 22.3, 23.4,
                                                                24.5, 23.2, 21.8, 20.5, 18.7, 16.9, 15.1, 13.3, 11.5, 9.7, 9.5, 9.2};

    private static double[] RAIN_OF_YESTERDAY_20170524 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                                                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

    private static double[] HUMIDITY_OF_YESTERDAY_20170524 = {69, 70, 70, 71, 71, 72, 73, 68, 62, 57, 51, 44,
                                                                38, 36, 35, 33, 34, 35, 35, 40, 45, 49, 53, 58};

    private DayWeather dayWeather;

    @Before
    public void before() throws Exception {

        Context context = InstrumentationRegistry.getTargetContext();
        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String json = localFileDataProvider.getJSONWeatherHistoryFileContent(context);

        YesterdayWeatherJSONParser yesterdayWeatherJSONParser = new YesterdayWeatherJSONParser();
        List<Weather> weather = yesterdayWeatherJSONParser.parseWeatherData(json);
        this.dayWeather = new DayWeather(weather, 0);
    }

    @Test
    public void itShouldCorrectlyParseWeatherJSON() throws Exception {
        Log.i("WeatherList:", dayWeather.getWeather().toString());
        assertTrue(!dayWeather.getWeather().isEmpty());
        assertTrue(dayWeather.getWeather().size()==24);
    }


    @Test
    public void itShouldCorrectlyCalculateAVGPressure() throws Exception {
        double avgPressureActual = dayWeather.getAVGPressure();
        Log.i("PRESSURE=", avgPressureActual+"");
        assertEquals(getAVGPressureExpected(), avgPressureActual, 0.001);
    }

    @Test
    public void itShouldCorrectlyCalculateAVGWind() throws Exception {
        double avgWindActual = dayWeather.getAVGWind();
        Log.i("WIND=", avgWindActual+"");
        assertEquals(getAVGWindExpected(), avgWindActual, 0.001);
    }

    @Test
    public void itShouldCorrectlyCalculateSUMRain() throws Exception {
        double sumRainActual = dayWeather.getTotalPrecipitation();
        Log.i("RAIN=", sumRainActual+"");
        assertEquals(getSumRainExpected(), sumRainActual, 0.001);
    }

    @Test
    public void itShouldCorrectlyCalculateAVGHumidity() throws Exception {
        double avgHumidityActual = dayWeather.getAVGHumidity();
        Log.i("HUMIDITY=", avgHumidityActual+"");
        assertEquals(getAVGHumidityExpected(), avgHumidityActual, 0.001);
    }


    private double getAVGHumidityExpected() {
        double sum = 0;
        int counter = 0;
        for (double h : HUMIDITY_OF_YESTERDAY_20170524) {
            sum+=h;
            counter++;
        }
        return sum/counter;
    }

    private double getSumRainExpected() {
        double sumRain = 0;
        for (double r : RAIN_OF_YESTERDAY_20170524) sumRain+=r;
        return sumRain;
    }

    private double getAVGWindExpected() {
        double sum = 0;
        int counter = 0;
        for (double w : WIND_OF_YESTERDAY_20170524) {
            sum+=w*(10/36d);
            counter++;
        }
        return sum/counter;
    }

    private double getAVGPressureExpected() {
        double sum = 0;
        int counter = 0;
        for (double p : PRESSURE_OF_YESTERDAY_20170524) {
            sum+=p/1.3332239d;
            counter++;
        }
        return sum/counter;
    }

}
