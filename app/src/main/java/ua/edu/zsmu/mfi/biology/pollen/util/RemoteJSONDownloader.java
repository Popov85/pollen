package ua.edu.zsmu.mfi.biology.pollen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andrey on 24.05.2017.
 */
public final class RemoteJSONDownloader {

    private static final String METHOD = "GET";
    // Timeout in ms
    private static final int TIMEOUT = 15000;

    public static String downloadJSON(String urlStr) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection c =(HttpURLConnection)url.openConnection();
            c.setRequestMethod(METHOD);
            c.setReadTimeout(TIMEOUT);
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
