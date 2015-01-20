package exbot.platform.devices.tables;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LookupTableWrapper {

	private static Document doc;
	
	public static void initTable(){
		try {
			File fXmlFile = new File("src/exbot/platform/devices/tables/lookup_table.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Element getDevice(String id) throws NullPointerException{
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
	
	
	public static void setDevice(String id, String name, String app_name, String path){
		
	}
	
	public static String getPath(String id){
		
		String path = "";
		
		try{
			path = getDevice(id).getAttribute("path");
		}catch(NullPointerException e){
			System.err.println("Cannot Found " + id + " Device");
		}
		
		return path;
	}
}