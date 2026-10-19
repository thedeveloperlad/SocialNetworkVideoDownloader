import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterXParser {

    String []fileResolutions = {"m3u8_file", "480x270", "640x360", "1280x720", "1920x1080"};

    public JSONObject TwitterXParserFile(String htmlString){
        JSONObject jsonObject = new JSONObject();
        String rawTwitterJson = twitterMediaExtractor(htmlString);
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

    public String twitterMediaExtractor(String jsonRawStr){
        System.out.println("RAW: " + jsonRawStr);
        String twitterJsonRaw = "";
        // result:$R[27]=
        // "media_entities2":[
        Pattern pattern = Pattern.compile("\\$R\\[(27)\\]\\s*=\\s*");
        // Pattern pattern = Pattern.compile("\"media_entities2\":\\[");
        Matcher matcher = pattern.matcher(jsonRawStr);

        if (matcher.find()) {
            int startIndexOfJson = matcher.end(); // The JSON begins right after the '='

            // Extract the raw JSON block by balancing the opening and closing characters.
            String rawJson = extractBalancedJson(jsonRawStr, startIndexOfJson);
            twitterJsonRaw = rawJson;
        } else {
            System.out.println(""+pattern.toString()+ "is not found.");
            return "";
        }
        return twitterJsonRaw;
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
}

/* JSON TWITTER SAMPLE
{"data":
                            {"tweet_result_by_rest_id":{"id":"VHdlZXRSZXN1bHRzOjIxMTE1NjI5MDM1Njg3MjQzMjk=","rest_id":"2111562903568724329",
                                                        "result":{"__typename":"Tweet","cashtag_attachments":[],
                                                        "core":{"user_results":{
                                                            "id":"VXNlclJlc3VsdHM6MTQ1NzM5MDA=",
                                                            "rest_id":"14573900",
                                                            "result":{"__typename":"User",
                                                            "avatar":{"image_url":"https://pbs.twimg.com/profile_images/1988659455366500352/RbDXMQEB_normal.jpg"},
                                                            "core":{"name":"Manchester City","screen_name":"ManCity"},
                                                            "id":"VXNlcjoxNDU3MzkwMA==",
                                                            "identity_profile_labels_highlighted_label":{},
                                                            "legacy":{},
                                                            "privacy":{"protected":false},
                                                            "profile_bio":{"description":"Est. 1894 🩵","entities":{"description":{}}},
                                                            "profile_image_shape":"Square",
                                                            "profile_translation":{"translator_type_enum":"None"},
                                                            "relationship_counts":{"followers":17906823,"following":45711},
                                                            "rest_id":"14573900",
                                                            "super_follow_eligible":false,
                                                            "tweet_counts":{"tweets":180720},
                                                            "verification":{"is_blue_verified":true,"verified_type":"Business"}}}},
                                                            "counts":{"bookmark_count":31,"favorite_count":1237,"quote_count":5,"reply_count":16,"retweet_count":98},
                                                            "details":{"cashtag_entities":[],"created_at_ms":1792270801000,"display_text_range":[0,77],
                                                            "full_text":"Highlights as we continued our 100% league start with victory over Ipswich 💪🩵 https://t.co/OGVozl7CaD",
                                                            "hashtag_entities":[],"smarttags":[]},
                                                            "edit_control":{"__typename":"EditControlInitial","edit_tweet_ids":["2111562903568724329"]},
                                                            "grok_translated_post_with_availability":{"is_available":false},
                                                            "id":"VHdlZXQ6MjExMTU2MjkwMzU2ODcyNDMyOQ==",
                                                            "is_translatable":false,
                                                            "legacy":{"entities":{},"lang":"en","possibly_sensitive":false},
                                                            "media_entities2":[
                                                                {"additional_media_info":
                                                                {"description":"",
                                                                "monetizable":true,
                                                                "title":""},
                                                                "allow_download_status":{"allow_download":false},
                                                                "expanded_url":"https://x.com/ManCity/status/2111562903568724329/video/1",
                                                                "ext_media_availability":{"status":"Available"},
                                                                "ext_playlists":[],
                                                                "id_str":"2111536777533997056",
                                                                "indices":[78,101],
                                                                "media_url_https":"https://pbs.twimg.com/media/HU2vs3VXcAAeFUT.jpg",
                                                                "original_info":{"height":1080,"width":1920},
                                                                "type":"video","video_info":{"duration_millis":119880,
                                                                "variants":[{"content_type":"application/x-mpegURL",
                                                                "url":"https://video.twimg.com/amplify_video/2111536777533997056/pl/A3qc9dpWOX3nOvdI.m3u8?tag=29&v=bdb"},
                                                                {"bitrate":256000,"content_type":"video/mp4",
                                                                "url":"https://video.twimg.com/amplify_video/2111536777533997056/vid/avc1/480x270/54SGnkRRg9Ofxvo-.mp4?tag=29"},
                                                                {"bitrate":832000,"content_type":"video/mp4","url":"https://video.twimg.com/amplify_video/2111536777533997056/vid/avc1/640x360/MJLq3xWdAqjgvGRe.mp4?tag=29"},
                                                                {"bitrate":2176000,"content_type":"video/mp4","url":"https://video.twimg.com/amplify_video/2111536777533997056/vid/avc1/1280x720/qPjNMUOTQWPIyO1e.mp4?tag=29"},
                                                                {"bitrate":10368000,"content_type":"video/mp4","url":"https://video.twimg.com/amplify_video/2111536777533997056/vid/avc1/1920x1080/mGVBKlcJCTNG7WRi.mp4?tag=29"}]}}],
                                                                "mention_entities":[],"rest_id":"2111562903568724329","url_entities":[],"views":{"count":"63486"}}}}}
* */