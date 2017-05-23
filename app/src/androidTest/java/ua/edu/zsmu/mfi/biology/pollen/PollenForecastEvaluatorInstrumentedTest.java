package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;
import ua.edu.zsmu.mfi.biology.pollen.weather.DayWeather;
import ua.edu.zsmu.mfi.biology.pollen.weather.Pollen;
import ua.edu.zsmu.mfi.biology.pollen.weather.PollenForecastEvaluator;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherDataProvider;

import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 23.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class PollenForecastEvaluatorInstrumentedTest {

    @Test(timeout=2000)
    public void itShouldCorrectlyCalculatePollenForecastFor3Days() throws Exception {

        Context context = InstrumentationRegistry.getTargetContext();
        NormalPollenConcentrationDataProvider normalPollenConcentrationDataProvider = new NormalPollenConcentrationDataProvider();
        NormalConcentration normalConcentration = normalPollenConcentrationDataProvider.getDataFromLocalFile(context);
        Log.i("normalConcentration", normalConcentration.toString());

        WeatherDataProvider weatherDataProvider = new WeatherDataProvider();
        Map<Integer, DayWeather> weatherForecast =
                weatherDataProvider.getWeather5DaysForecast(weatherDataProvider.downloadWeatherJSON());

        PollenForecastEvaluator pollenForecastEvaluator = new PollenForecastEvaluator(weatherForecast, normalConcentration);

        Map<Integer, Pollen> forecast =  pollenForecastEvaluator.getPollenForecast();

        assertTrue(!forecast.isEmpty());
    }

}