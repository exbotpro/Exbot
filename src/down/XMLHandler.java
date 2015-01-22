package down;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLHandler {

	protected Document getXMLDocument(String desciptorURL){
		Document doc=null;
		try {
			URL url = new URL(desciptorURL);
			URLConnection connection = url.openConnection();
	        doc = parseXML(connection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
                
        return doc;
    }

    private Document parseXML(InputStream stream) throws Exception{
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try{
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }catch(Exception ex) {
            throw ex;
        }

        return doc;
    }
}
