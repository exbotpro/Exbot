package exbot.platform.devices.tables;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import exbot.platform.xml.XMLHandler;

public class LookupTableWrapper extends XMLHandler{

	private static Document doc;
	
	public static void setDevice(String id, String name, String app_name, String path){
		
	}
	
	public static String getElement(String id, String attName){
		
		String att = "";
		
		try{
			att = XMLHandler.getDevice(doc, id).getAttribute(attName);
		}catch(NullPointerException e){
			System.err.println("Cannot Found " + id + " Device");
		}
		
		return att;
	}
	
	
	public static void addDevice(String docPath, String id, String jarPath, String classPath){
		doc = XMLHandler.getXMLDocument(docPath);
		XMLHandler.addDevice(doc, "DeviceList", "Device", docPath, jarPath, id, classPath);
		
	}
	
	public static void initTable(String url){
		try {
			doc = XMLHandler.getXMLDocument(url);
			initialCheckPath();
			save(doc, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initialCheckPath(){
		try{
			
			NodeList nList = doc.getElementsByTagName("Device");
			
			for(int i =0 ; i < nList.getLength() ; i++){
				Element e = (Element)nList.item(i);
				String jarFile = e.getAttribute("jar");
				
				File tmpFile = new File(jarFile);
				
				if(!tmpFile.exists() || jarFile.equals("")){
					removeNode(e);
				}
			}
			
		}catch(Exception e){
			System.err.println("error");
		}
	}
	
	private static void removeNode(Element e) throws Exception{
		e.getParentNode().removeChild(e);
	}
	
}