import java.io.StringReader;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class HTMLParser {
    HTMLParser(){}

    public HTMLDocument getHtmlDocument(String htmlString) {
        try{
            HTMLEditorKit kit = new HTMLEditorKit();
            return (HTMLDocument) kit.createDefaultDocument();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getHtmlDocumentLength(HTMLDocument doc) {
        try{
            HTMLEditorKit kit = new HTMLEditorKit();
            return doc.getLength();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
