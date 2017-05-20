package ua.edu.zsmu.mfi.biology.pollen;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Andrey on 17.05.2017.
 */

public final class LocalFileDataProvider {

    private static final String NORM_CONCENTRATION_FILE_NAME = "jsonNormData.json";

    private static final String SAMPLE_WEATHER_FORECAST__FILE_NAME = "forecast.json";

    public String getJSONNormConcentrationFileContent(Context context) {
      return getJSONFileContent(context, NORM_CONCENTRATION_FILE_NAME);
    }

    public String getJSONWeatherForecastFileContent(Context context) {
        return getJSONFileContent(context, SAMPLE_WEATHER_FORECAST__FILE_NAME);
    }

    private String getJSONFileContent(Context context, String path) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.e("ERROR: ", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
