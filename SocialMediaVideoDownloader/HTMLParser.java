import java.io.StringReader;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import static javax.swing.text.html.HTML.Tag.TITLE;

public class HTMLParser {
    HTMLParser(){}

    public HTMLDocument getHtmlDocument(String htmlString) {
        try{
            HTMLEditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            kit.read(new StringReader(htmlString), doc, 0);
            return doc;
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

    public String getTitle(HTMLDocument doc){
        try{
            return doc.getProperty(TITLE).toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
