package ua.edu.zsmu.mfi.biology.pollen;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import ua.edu.zsmu.mfi.biology.pollen.weather.DayWeather;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherHistoryDataProvider;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 24.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class WeatherHistoryDataProviderInstrumentedTest {

    @Test
    public void itShouldCorrectlyParseWeatherRemoteJSONFile() throws Exception {
        WeatherHistoryDataProvider weatherHistoryDataProvider = new WeatherHistoryDataProvider();
        DayWeather dayWeather = weatherHistoryDataProvider.getWeatherYesterday();
        Log.i("DayWeather", dayWeather.toString());
        Log.i("AVG_PRESSURE=", dayWeather.getAVGPressure()+"");
        Log.i("AVG_WIND=:", dayWeather.getAVGWind()+"");
        Log.i("AVG_HUMIDITY=", dayWeather.getAVGHumidity()+"");
        Log.i("SUM_RAIN=", dayWeather.getTotalPrecipitation()+"");

        assertTrue(dayWeather!=null);
        assertTrue(dayWeather.getWeather().size()==24);
    }

}
