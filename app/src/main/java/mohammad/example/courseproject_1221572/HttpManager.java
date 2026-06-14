package mohammad.example.courseproject_1221572;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpManager {

    public static String getData(String link) {

        BufferedReader bufferedReader = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(link);

            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestProperty("User-Agent", "Android");

            int responseCode = httpURLConnection.getResponseCode();

            Log.d("HttpManager", "Response Code: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            httpURLConnection.getInputStream(),
                            StandardCharsets.UTF_8
                    )
            );

            StringBuilder stringBuilder = new StringBuilder();

            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = bufferedReader.readLine();
            }

            return stringBuilder.toString();

        } catch (Exception ex) {
            Log.d("HttpManager", "Error: " + ex.toString());
            return null;

        } finally {

            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
}