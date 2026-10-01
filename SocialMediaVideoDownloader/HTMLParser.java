import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
}
