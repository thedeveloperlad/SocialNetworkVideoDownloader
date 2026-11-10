import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DailyMotionParser {
    String title;
    String urlImage;
    String urlDailyMotionM3U8Link;
    URLParser urlParser = new URLParser();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    private static final String REFERER = "https://dailymotion.com";

    public JSONObject dailyMotionParserFile(String dailyMotionJsonRaw, String urlVideo){
        JSONObject jsonObject = new JSONObject();
        try {

            JSONObject dailyMotionJsonObject = new JSONObject(dailyMotionJsonRaw);

            title = dailyMotionJsonObject.getString("title");
            // System.out.println(dailyMotionJsonObject.getString("description"));
            urlImage = dailyMotionJsonObject.getJSONObject("thumbnails").getString("1080");
            urlDailyMotionM3U8Link = dailyMotionJsonObject.getJSONObject("qualities").getJSONArray("auto").getJSONObject(0).getString("url");
            String m3u8String = urlParser.getStringFromHttpConnection(urlDailyMotionM3U8Link, urlVideo);
            System.out.println(m3u8String);

            jsonObject.put("name", title);
            // jsonObject.put("link", ogVideo);
            jsonObject.put("image", urlImage);
            // jsonObject.put("description", ogDescription);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // TODO: Get ID from URL link
    // https://www.dailymotion.com/video/x9cpy1a
    // https://www.dailymotion.com/video/x3hj7f5
    // https://www.dailymotion.com/video/<ID>
    public String getDailyMotionID(String url){
        String segments[] = url.split("/");
        String id = segments[segments.length - 1];
        return id;
    }

    // TODO: Get URL API
    public String getDailyMotionURLApi(String ID){
        return "https://www.dailymotion.com/player/metadata/video/"+ ID + "?embedder=";
    }

    // TODO: Get Json String from DailyMotion API
    private static List<String> fetchTsSegments(HttpClient client, String qualityUrl) throws Exception {
        List<String> tsList = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(qualityUrl))
                .header("User-Agent", USER_AGENT)
                .header("Referer", REFERER)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String[] lines = response.body().split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("http") && (line.contains(".ts") || line.contains("/fragment-"))) {
                tsList.add(line);
            }
        }
        return tsList;
    }
}
