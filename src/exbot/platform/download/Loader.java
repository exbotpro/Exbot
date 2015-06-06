package exbot.platform.download;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URLClassLoader;

import org.w3c.dom.Document;

import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.tables.LookupTableWrapper;
import exbot.platform.xml.XMLHandler;

public class Loader {
	@SuppressWarnings({ "rawtypes", "resource" })
	public static Operator load(String id, String classPath, String jarPath){
		Operator op = null;
		try {
			URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			ClassLoaderFromJar l = new ClassLoaderFromJar(loader.getURLs());
			File jar = new File(jarPath);
			l.addURL(jar.toURI().toURL());
			Class c = l.loadClass(classPath);
			
			Constructor<?> con = c.getDeclaredConstructors()[0];
			op = (Operator) con.newInstance(new Object[] {new String(id)});
	    	if(op!=null) {
//	    		modifyClassPathFile(jarPath);
	    		LookupTableWrapper.addDevice(Path.lookupTablePath, id, jarPath, classPath);
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return op;
	}
	
	
	private static void modifyClassPathFile(String jarPath){
		Document doc = XMLHandler.getXMLDocument(Path.localClassPathFile);
		XMLHandler.addClassPath(doc, Path.localClassPathFile, "classpath", "classpathentry", jarPath);
	}
}
