import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MLBParser {

    public JSONObject mlbParserFile(String htmlString){
        JSONObject jsonObject = new JSONObject();
        try {

            Document doc = Jsoup.parse(htmlString);

            // 1. Get the content of a <meta name="..."> tag
            // @TestOnly
            // String description = getMetaContentByName(doc, "description");
            // System.out.println("Description: " + description);

            // 2. Get the content of a <meta property="..."> tag (e.g., Open Graph)
            String ogTitle = HTMLParser.getMetaContentByProperty(doc, "og:title");
            String ogVideo = HTMLParser.getMetaContentByProperty(doc, "og:video");
            String ogImage = HTMLParser.getMetaContentByProperty(doc, "og:image");
            String ogDescription = HTMLParser.getMetaContentByProperty(doc, "og:description");
            String mlbJsonRaw = HTMLParser.getMetaContentByScriptType(doc, "application/ld+json");

            JSONObject mlbJsonRawObject = new JSONObject(mlbJsonRaw);
            jsonObject.put("name", ogTitle);
            jsonObject.put("link", ogVideo);
            jsonObject.put("image", ogImage);
            jsonObject.put("description", ogDescription);
            jsonObject.put("mlbJsonRaw", mlbJsonRawObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
