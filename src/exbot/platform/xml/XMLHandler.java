package exbot.platform.xml;

import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHandler {

	public static Document getXMLDocument(String docPath){
		Document doc = null;
		try {
			File fXmlFile = new File(docPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public static Element getDevice(Document doc, String id) throws NullPointerException{
		NodeList nList = doc.getElementsByTagName("Device");
		Element e = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) nNode;
	 			if(e.getAttribute("id").equals(id)){
	 				return e;
	 			}
			}
		}

		return null;
	}
	
	public static boolean addDevice(
			Document doc, String parentNode, String newNode, 
			String docPath, String jarPath, String id, String path) throws NullPointerException{
		
		try{
			NodeList nList = doc.getElementsByTagName(parentNode);
			Element cp = (Element)nList.item(0);
			Element entry = doc.createElement(newNode);
			entry.setAttribute("id", id);
			entry.setAttribute("jar", jarPath);
			entry.setAttribute("path", path);
			cp.appendChild(entry);	
			save(doc, docPath);
			
		}catch(Exception e){
			return false;
		}
		
        
		return true;
	}
	
	public static boolean addClassPath(Document doc, String docPath, 
			String parentNode, String newNode, String path) throws NullPointerException{
		
		try{
			NodeList nList = doc.getElementsByTagName(parentNode);
			Element cp = (Element)nList.item(0);
			Element entry = doc.createElement(newNode);
			entry.setAttribute("kind", "lib");
			entry.setAttribute("path", path);
			cp.appendChild(entry);	
			save(doc, docPath);
			
		}catch(Exception e){
			return false;
		}
		
        
		return true;
	}
	
	public static void save(Document doc, String docPath) throws Exception{
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(docPath));
        transformer.transform(source, result);
        
	}
	
	public static Document getXMLDocumentFromWeb(String desciptorURL){
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
	
	public static Document getXMLDocumentFromWebViaProxy(String desciptorURL){
		Document doc=null;
		try {
			URL url = new URL(desciptorURL);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cache.kettering.edu", 3128));
			URLConnection connection = url.openConnection(proxy);
	        doc = parseXML(connection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
                
        return doc;
    }
	

    private static Document parseXML(InputStream stream) throws Exception{
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
