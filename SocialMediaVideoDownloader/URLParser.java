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
}
