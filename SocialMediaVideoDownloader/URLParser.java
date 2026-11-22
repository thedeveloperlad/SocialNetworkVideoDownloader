import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class URLParser {
    URLParser(){}

    public String getStringFromURL(String path) {
        String htmlString = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = URI.create(path).toURL();
            //try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // System.out.println(line);
                    stringBuilder.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Validation Failed: Target server is offline or unreachable." + e.getMessage());
        }
        htmlString = stringBuilder.toString();
        return htmlString;
    }

    public String getStringFromHttpConnection(String path, String urlVideo) {
        String htmlString = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            java.net.URL url = java.net.URI.create(path).toURL();
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            // 2. EVITAR EL 403: Indicar de dónde proviene el reproductor (Por ejemplo, la web de Dailymotion)
            // Cambia "https://www.dailymotion.com/" por la URL exacta del video en el navegador si la tienes
            connection.setRequestProperty("Referer", urlVideo);

            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Origin", "https://www.dailymotion.com");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                }
            } else {
                System.err.println("The server denied the access, HTTP Code: " + responseCode);

                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.err.println("Server details: " + errorLine);
                    }
                } catch (Exception ignored) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Validation Failed: Target server is offline, unreachable or blocking access. " + e.getMessage());
        }
        htmlString = stringBuilder.toString();
        return htmlString;
    }
}
