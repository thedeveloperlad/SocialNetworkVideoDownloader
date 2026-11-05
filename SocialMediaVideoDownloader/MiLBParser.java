import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class MiLBParser {

    public static String findKey = "window.webMediaPerformanceConfig = {};    window.INIT_DATA =";

    public JSONObject milbParserFile(String milbJsonRaw){
        JSONObject jsonObject = new JSONObject();
        try {
            // milbJsonRaw = cleanJsonRaw(milbJsonRaw);
            JSONObject milbJsonRawObject = new JSONObject(cleanJsonRaw(milbJsonRaw)).getJSONObject("clip").getJSONObject("data");
            String ogTitle = milbJsonRawObject.getString("title");

            System.out.println(milbJsonRawObject.getString("title"));
            System.out.println(milbJsonRawObject.getString("description"));
            System.out.println(milbJsonRawObject.getString("type"));
            System.out.println(milbJsonRawObject.getString("url"));

            //System.out.println(milbJsonRawObject.getJSONObject("clip").getJSONObject("data").getJSONObject("id").getString("url"));
            //System.out.println(milbJsonRawObject.getJSONObject("clip").getJSONObject("data").getJSONObject("id").getString("image"));
            //System.out.println(milbJsonRawObject.getJSONObject("clip").getJSONObject("data").getJSONObject("id").getString("link"));

            String ogDescription = milbJsonRawObject.getString("description");
            String ogVideo = milbJsonRawObject.getString("url");
            String ogImage = "";
            jsonObject.put("name", ogTitle);
            jsonObject.put("link", ogVideo);
            jsonObject.put("image", ogImage);
            jsonObject.put("description", ogDescription);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // Extract JSON String raw
    public String getJSONString(String [] milbScriptRaw){
        String milbJsonRaw = "";
        for(int row = 0; row < milbScriptRaw.length; row++){

            if(milbScriptRaw[row].contains(findKey)){
                milbJsonRaw  = milbScriptRaw[row];
                break;
            }
        }
        return milbJsonRaw;
    }

    public String removeInitData(String milbJsonInitRaw){
        String milbJsonRaw = "";
        milbJsonRaw = milbJsonInitRaw.replace(findKey, "").trim();
        return milbJsonRaw;
    }

    public String cleanJsonRaw(String inputJsonRaw){
        String cleanJson = inputJsonRaw.trim();
        if (cleanJson.startsWith("\"") && cleanJson.endsWith("\"")) {
            cleanJson = cleanJson.substring(1, cleanJson.length() - 1);
        }

        // Replace the escaped quotes (\") with standard quotes (")
        cleanJson = cleanJson.replace("\\\"", "\"");
        return cleanJson;
    }
}
