package ua.edu.zsmy.biology.zsmu.pollen;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Andrey on 17.05.2017.
 */

public final class LocalFileNormalPollenConcentrationDataProvider {

    private static final String JSON_FILE_NAME = "jsonNormData.json";

    public String getJSONFileContent(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(JSON_FILE_NAME);
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
