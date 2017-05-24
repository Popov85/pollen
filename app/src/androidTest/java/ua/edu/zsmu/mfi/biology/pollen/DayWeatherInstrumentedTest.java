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
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherForecastDataProvider;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrey on 23.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class DayWeatherInstrumentedTest {

    private static double[] PRESSURE_OF_DAY_20170520 = {1008.09, 1007.36, 1006.98, 1006.64, 1007.23, 1006.82};

    private static double[] WIND_OF_DAY_20170520 = {2.56, 2.46, 2.2, 1.87, 1.07, 2.57};

    private static double[] RAIN_OF_DAY_20170520 = {0.0, 0.04, 0.17, 0.0, 0.0, 0.0};

    private static double[] HUMIDITY_OF_DAY_20170520 = {86, 76, 72, 63, 71, 85};

    private DayWeather dayWeather;

    @Before
    public void before() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        WeatherForecastDataProvider weatherForecastDataProvider = new WeatherForecastDataProvider();
        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String json = localFileDataProvider.getJSONWeatherForecastFileContent(context);
        Map<Integer, DayWeather> weatherForecast =
                weatherForecastDataProvider.getWeather5DaysForecast(json);
        this.dayWeather = weatherForecast.get(1);
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
        for (double h : HUMIDITY_OF_DAY_20170520) {
            sum+=h;
            counter++;
        }
        return sum/counter;
    }

    private double getSumRainExpected() {
        double sumRain = 0;
        for (double r : RAIN_OF_DAY_20170520) sumRain+=r;
        return sumRain;
    }

    private double getAVGWindExpected() {
        double sum = 0;
        int counter = 0;
        for (double w : WIND_OF_DAY_20170520) {
            sum+=w;
            counter++;
        }
        return sum/counter;
    }

    private double getAVGPressureExpected() {
        double sum = 0;
        int counter = 0;
        for (double v : PRESSURE_OF_DAY_20170520) {
            sum+=v* WeatherForecastDataProvider.MM_OF_MERCURY_CONST;
            counter++;
        }
        return sum/counter;
    }

}
