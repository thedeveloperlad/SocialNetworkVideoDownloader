import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterXParser {

    String []fileResolutions = {"m3u8_file", "480x270", "640x360", "1280x720", "1920x1080", "3840x2160"};
    int MAX_OBJECT_ROW = 120;

    public JSONObject TwitterXParserFile(String htmlString){
        JSONObject jsonObject = new JSONObject();
        String rawTwitterJson = "";
        for(int objectrow=0; objectrow<MAX_OBJECT_ROW;objectrow++) {
            rawTwitterJson = XJsonMediaExtractor(htmlString, objectrow);
            if(!rawTwitterJson.equals("")){
                break; // stop the thread.
            }
        }

        try {
            JSONObject twitterXJsonObject = new JSONObject(rawTwitterJson);

            String value = twitterXJsonObject.getString("value");
            System.out.println("VALUE: " + value);
            JSONObject data = new JSONObject(value);
            String dataRaw = data.getJSONObject("data").toString();
            JSONObject dataJson = new JSONObject(dataRaw);
            // @Testing print
            // System.out.println("dataJson: " + dataJson);
            String tweetResultByRestId = dataJson.getJSONObject("tweet_result_by_rest_id").toString();
            JSONObject resultTag = new JSONObject(tweetResultByRestId);
            String resultJsonTag = resultTag.getJSONObject("result").toString();

            /*String fullText = resultTag.getJSONObject("full_text").toString(); //TO SHOW ON DISPLAY...
            System.out.println("fullText: " + fullText);*/

            JSONObject mediaEntities2 = new JSONObject(resultJsonTag);
            JSONArray mediaArray = mediaEntities2.getJSONArray("media_entities2");
            JSONObject firstItem = mediaArray.getJSONObject(0);
            String video_info = firstItem.getJSONObject("video_info").toString();
            JSONObject videoInfoJson = new JSONObject(video_info);
            JSONArray variants = videoInfoJson.getJSONArray("variants");

            for(int row = 0; row < variants.length(); row++){
                JSONObject mediaItem = variants.getJSONObject(row);
                jsonObject.put(fileResolutions[row], mediaItem.getString("url"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String XJsonMediaExtractor(String htmlString, int row){

        // @ TESTING ROW
        // System.out.println("RAW: " + htmlString);
        String xTwitterJsonRaw = "";

        String startMarker = "result:$R[" + row + "]={kind:\"Result.Ok\"";
        String endMarker = "],graphqlRequestStream:";

        int markerStartIndex = htmlString.indexOf(startMarker);

        if (markerStartIndex != -1) {
            int jsonStartIndex = htmlString.indexOf(startMarker);
            int jsonEndIndex = htmlString.indexOf(endMarker);

            if (jsonEndIndex != -1 && jsonEndIndex >= jsonStartIndex) {
                String part1 = htmlString.substring(0, jsonStartIndex);
                String part2 = htmlString.substring(jsonStartIndex, jsonEndIndex);
                String part3 = htmlString.substring(jsonEndIndex);

                System.out.println("¡Match found in row = " + row + "!");
                System.out.println("part 2 (JSON): " + part2);
                xTwitterJsonRaw =  part2;
                String blockToDelete = "result:$R[" + row + "]=";
                xTwitterJsonRaw = xTwitterJsonRaw.replace(blockToDelete, "");
                System.out.println("Row " + row + " processed.");
            } else {
                System.out.println("ALERT: Row not found." + row);
            }
        }
        return xTwitterJsonRaw;
    }

    private static String extractBalancedJson(String text, int startIndex) {
        int length = text.length();
        char openChar = text.charAt(startIndex);
        char closeChar;

        // Determine whether the JSON begins with an array [] or an object {}
        if (openChar == '[') {
            closeChar = ']';
        } else if (openChar == '{') {
            closeChar = '}';
        } else {
            // If there are spaces before the JSON, we look for the first valid character.
            while (startIndex < length && Character.isWhitespace(text.charAt(startIndex))) {
                startIndex++;
            }
            openChar = text.charAt(startIndex);
            closeChar = (openChar == '[') ? ']' : '}';
        }

        int counter = 0;
        int currentIndex = startIndex;

        while (currentIndex < length) {
            char currentChar = text.charAt(currentIndex);

            if (currentChar == openChar) {
                counter++;
            } else if (currentChar == closeChar) {
                counter--;
            }

            // When the counter returns to 0, we find the precise closure of the structure.
            if (counter == 0) {
                return text.substring(startIndex, currentIndex + 1);
            }
            currentIndex++;
        }

        return text.substring(startIndex); // Fallback in case it doesn't close properly
    }

    private static String removeBlock(String str, String block) {
        String[] parts = {};

        if(str.contains(block)){
            parts = str.split(block, 2);
        }
        return parts[0];
    }
}