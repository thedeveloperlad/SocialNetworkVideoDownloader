import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
    HTMLParser(){}

    public Document getHtmlDocument(String htmlString) {
        return Jsoup.parse(htmlString);
    }

    public String getTitle(Document doc){
        try{
            return doc.title();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String getHtml(Document doc){
        try{
            return doc.html();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    // <script nonce="L9qjo6405c+mjSSOue4NGw==" data-tsr-stream-part="">
    public static String getMediaDataScriptType(Document document) {
        // Use a CSS selector to find the specific meta tag
        Element scriptElement = document.select("script[data-tsr-stream-part]").first();
        String scriptData = "";
        for (Element script : scriptElement) {
            String nonce = script.attr("nonce");

            // NOTE: .data() extract data content
            scriptData = script.data();

            /*
            @debug // TESTING
            System.out.println("Nonce detected: " + nonce);
            System.out.println("Streams:");
            System.out.println(scriptData.trim());

            */
        }

        if(scriptElement != null){
            return scriptData;
        }
        return "";
    }

    /*
    *  Meta Extractor
    * */
    public String getMetaProperty(Document doc, String property){
        return doc.select("meta[property=og:"+property+"]").attr("content");
    }

    public static String getMetaContentByProperty(Document document, String property) {
        // Use a CSS selector to find the specific meta tag
        Element metaTag = document.select("meta[property=" + property + "]").first();
        if (metaTag != null) {
            return metaTag.attr("content");
        }
        return null;
    }

    public static String getMetaContentByScriptType(Document document, String property) {
        // Use a CSS selector to find the specific meta tag
        Element scriptElement = document.select("script[type='" + property + "']").first();

        if(scriptElement != null){
            return scriptElement.data().trim();
        }
        return "";
    }
}
