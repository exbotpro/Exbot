package exbot.platform.devices.tables;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import exbot.dev.core.interfaces.Operator;
import exbot.platform.download.Path;
import exbot.platform.xml.XMLHandler;

public class LookupTableWrapper extends XMLHandler{

	private static Document doc;
	
	public static void setDevice(String id, String name, String app_name, String path){
		
	}
	
	public static String getPath(String id){
		
		String path = "";
		
		try{
			path = XMLHandler.getDevice(doc, id).getAttribute("path");
		}catch(NullPointerException e){
			System.err.println("Cannot Found " + id + " Device");
		}
		
		return path;
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
				String classPath = e.getAttribute("path");
				
				File tmpFile = new File(Path.localAppRepostoryPath + "/" + jarFile);
				
				if(!tmpFile.exists() || jarFile.equals("")){
					removeNode(e);
				}else{
					if(!initialCheckClassLoading(tmpFile, classPath)){
						removeNode(e);
					}
				}
			}
			
		}catch(Exception e){
			
		}
	}
	
	private static void removeNode(Element e) throws Exception{
		e.getParentNode().removeChild(e);
	}
	
	private static boolean initialCheckClassLoading(File jarFile, String classPath) throws Exception{
		boolean chk = false;
		
		URLClassLoader cl = URLClassLoader.newInstance(new URL[] {new URL("file://" + jarFile.getAbsolutePath())});
		
		Class<?> c = cl.loadClass(classPath);
		Constructor<?> con = c.getDeclaredConstructors()[0];
		Object obj = con.newInstance(new Object[] {new String("test")});
		
		if(obj instanceof Operator){
			chk = true;
		}else{
			chk = false;
		}
		
		return chk;
	}
	
}