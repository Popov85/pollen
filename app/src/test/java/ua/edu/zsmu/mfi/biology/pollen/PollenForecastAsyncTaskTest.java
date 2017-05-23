package ua.edu.zsmu.mfi.biology.pollen;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andrey on 19.05.2017.
 */

public class PollenForecastAsyncTaskTest {

    @Test(timeout=5000)
    public void itShouldDownloadWeatherDataSmoothly() {
        try {
            System.out.println("Downloaded: "+getContent());
        } catch (IOException e) {
            System.out.println("ERROR...");
        }
    }

    private String getContent() throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Zaporizhzhya,ua&lang=en&&appid=887cd404c38497947eb969593d0aae87");
            HttpURLConnection c =(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(5000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return buf.toString();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
