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
    String description;
    URLParser urlParser = new URLParser();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    private static final String REFERER = "https://dailymotion.com";

    public JSONObject dailyMotionParserFile(String dailyMotionJsonRaw, String urlVideo){
        JSONObject jsonObject = new JSONObject();
        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject dailyMotionJsonObject = new JSONObject(dailyMotionJsonRaw);

            title = dailyMotionJsonObject.getString("title");
            description = dailyMotionJsonObject.getString("description");
            // System.out.println(dailyMotionJsonObject.getString("description"));
            urlImage = dailyMotionJsonObject.getJSONObject("thumbnails").getString("1080");
            urlDailyMotionM3U8Link = dailyMotionJsonObject.getJSONObject("qualities").getJSONArray("auto").getJSONObject(0).getString("url");
            System.out.println("urlDailyMotionM3U8Link: " + urlDailyMotionM3U8Link);
            System.out.println("urlImage: " + urlImage);
            // String qualityM3u8Url = fetchQualityM3u8Url(client, urlDailyMotionM3U8Link);
            // System.out.println("qualityM3u8Url: " + qualityM3u8Url);

            /*List<String> tsUrls = fetchTsSegments(client, qualityM3u8Url);
            if (tsUrls.isEmpty()) {
                System.err.println("No video fragments were found.");
                return jsonObject;
            }*/
            // String m3u8String = urlParser.getStringFromHttpConnection(urlDailyMotionM3U8Link, urlVideo);
            // System.out.println(m3u8String);

            jsonObject.put("name", title);
            jsonObject.put("link", urlDailyMotionM3U8Link);
            jsonObject.put("image", urlImage);
            jsonObject.put("description", description);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

        System.out.println("line: " + lines);
        for (String line : lines) {
            line = line.trim();
            System.out.println("line: " + line);
            if (line.startsWith("http") && (line.contains(".ts") || line.contains("/fragment-"))) {
                tsList.add(line);
            }
        }
        return tsList;
    }

    private static String fetchQualityM3u8Url(HttpClient client, String masterUrl) throws Exception {
        /*

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(masterUrl))
                .header("User-Agent", USER_AGENT)
                .header("Referer", REFERER)
                .GET()
                .build();

        */

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(masterUrl))
                .header("User-Agent", USER_AGENT)
                .header("Accept", "*/*")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Origin", "https://dailymotion.com")
                .header("Referer", "https://dailymotion.com/")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .GET()
                .build();

        URI baseUri = URI.create(masterUrl);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String[] lines = response.body().split("\\\\R");

        System.out.println("HTTP Status Code: " + response.statusCode());
        System.out.println("Response Headers: " + response.headers().map());

        System.out.println("line 0: " + lines[0]);
        System.out.println("lines length: " + lines.length);

        HttpRequest m3u8Req = HttpRequest.newBuilder()
                .uri(URI.create(masterUrl))
                .header("User-Agent", USER_AGENT)
                .header("Referer", "https://dailymotion.com")
                .header("Origin", "https://dailymotion.com")
                .GET()
                .build();

        HttpResponse<String> m3u8Res = client.send(m3u8Req, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status Master M3U8: " + m3u8Res.statusCode());
        System.out.println(m3u8Res.body());

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            System.out.println("lines: " + lines[0]);
            // 2. Look for the video stream tag to avoid accidentally grabbing an audio-only playlist
            if (line.startsWith("#EXT-X-STREAM-INF") && (i + 1) < lines.length) {
                String nextLine = lines[i + 1].trim();
                System.out.println("nextLine: " + nextLine);
                if (!nextLine.isEmpty() && !nextLine.startsWith("#")) {
                    // 3. Automatically converts relative paths (e.g., "1080p/index.m3u8") into full URLs
                    return baseUri.resolve(nextLine).toString();
                }
            }
        }
        return "";
    }
}
