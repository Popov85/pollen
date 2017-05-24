package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import ua.edu.zsmu.mfi.biology.pollen.weather.Weather;
import ua.edu.zsmu.mfi.biology.pollen.weather.WeatherHistoryDataProvider;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andrey on 24.05.2017.
 */
@RunWith(AndroidJUnit4.class)
public class WeatherHistoryDataProviderInstrumentedTest {

    @Test
    public void itShouldCorrectlyParseWeatherJSON() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        LocalFileDataProvider localFileDataProvider = new LocalFileDataProvider();
        String json = localFileDataProvider.getJSONWeatherHistoryFileContent(context);

        WeatherHistoryDataProvider weatherHistoryDataProvider = new WeatherHistoryDataProvider();
        List<Weather> weather = weatherHistoryDataProvider.parseWeatherData(json);
        Log.i("WeatherList:", weather.toString());
        assertTrue(!weather.isEmpty());
        assertTrue(weather.size()==24);
    }
}
