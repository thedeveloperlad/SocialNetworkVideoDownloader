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
