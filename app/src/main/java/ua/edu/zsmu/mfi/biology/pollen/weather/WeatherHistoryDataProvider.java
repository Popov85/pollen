package ua.edu.zsmu.mfi.biology.pollen.weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ua.edu.zsmu.mfi.biology.pollen.util.RemoteJSONDownloader;

/**
 * Created by Andrey on 24.05.2017.
 */

public class WeatherHistoryDataProvider {

    private static final String APP_ID = "c240265dd48b4cda9c981236172405";

    private static final String CITY = "Zaporizhzhya";

    private static final String DATE;

    private static final int DAY_OF_YEAR;

    static {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DATE = dateFormat.format(calendar.getTime());
        DAY_OF_YEAR = calendar.get(Calendar.DAY_OF_YEAR);
    }

    // DATE should be like "2017-05-23" because of API requirement
    private static final String HISTORY_URL = "http://api.apixu.com/v1/history.json?key="+APP_ID+"&q="+CITY+"&dt="+DATE;

    public String downloadWeatherJSON() throws Exception {
        return RemoteJSONDownloader.downloadJSON(HISTORY_URL);
    }

    public DayWeather getWeatherYesterday(String json) throws Exception {
        List<Weather> weathers = new YesterdayWeatherJSONParser().parseWeatherData(json);
        return new DayWeather(weathers, DAY_OF_YEAR);
    }
}
