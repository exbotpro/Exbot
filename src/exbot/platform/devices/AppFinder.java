package exbot.platform.devices;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import exbot.dev.core.device.Devices;
import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.tables.LookupTableWrapper;
import exbot.platform.download.Downloader;
import exbot.platform.download.Path;

public class AppFinder {
	
	private Path path;
	
    public Operator getDevice(String id){
    	
    	Operator op = null;
		try {
			String classPath = LookupTableWrapper.getPath(id);
    		//if no app for the device is in the local place, search for repository in Internet and download it!
			
			if(classPath.equals("")) {
	    		op = this.downloadApp(id, classPath);
	    	}else{
	    		Class<?> c = Class.forName(classPath);
				Constructor<?> con = c.getDeclaredConstructors()[0];
				op = (Operator) con.newInstance(new Object[] {new String(id)});
	    	}
			
		} catch (Exception e) {
//			System.err.println("A class for operating the device is not found");
		}
		
		return op;
	}

	private Operator downloadApp(String id, String jarPath)
			throws Exception {
		
		Operator op;
		
		path  = new Path();
		path.findApp(id);
		String repository = path.getRepository();
		String appPath = path.getPath();
		String appName = path.getApp();
		
		if(appPath.equals("") || appName.equals("")){
			return null;
		}
		
		Downloader downloader = new Downloader();
		downloader.downWithProxy(repository, appPath, appName, Path.localAppRepostoryPath);
		jarPath = Path.localAppRepostoryPath + appName;
		op = load2(id, jarPath);		
		
		return op;
	}

	private Operator load2(String id, String jarPath) throws Exception{
		Operator op = null;
		File file = new File(jarPath);
    	JarFile jar = new JarFile(file);
    	URL[] urls = {new URL("jar:" + file.toURI().toURL() + "!/")};
    	 
    	// It is important to set the parent ClassLoader in order to make sure that the new plugins
    	// and their folders will be added to the classpath correctly
    	URLClassLoader cl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
    	Enumeration<JarEntry> e = jar.entries();
    	while (e.hasMoreElements()) {
    	    JarEntry entry = (JarEntry) e.nextElement();
    	    if (entry.getName().endsWith(".class")) {
    	    	String className = extractClassName(entry);
    	        Class<?> c = cl.loadClass(className);
    	        
    	        if(className.equals(path.getClassPath())){
    	        	Constructor<?> con = c.getDeclaredConstructors()[0];
        			op = (Operator) con.newInstance(new Object[] {new String(id)});
    	        }
    	    }
    	}
    	if(op!=null) LookupTableWrapper.addDevice(Path.lookupTablePath, id, jarPath, path.getClassPath());
    	return op;
	}
	
	private static String extractClassName(JarEntry e){
    	String className = "";
    	String[] t = e.getName().split("\\.");
    	String te = t[0];
    	className = te.replace("/", ".");
    	
    	return className;
    }
	
    public static void registOperator(Operator op){
		Devices.addOperator(op);
    }
    
	public void init(Operator op) {
		(new Thread(op)).start();
	}
	
	

//	private Operator load(String id, String jarPath)
//			throws MalformedURLException, ClassNotFoundException,
//			InstantiationException, IllegalAccessException,
//			InvocationTargetException {
//		
//		Operator op;
//		LookupTableWrapper.addDevice(Path.lookupTablePath, id, jarPath, path.getClassPath());
//		
//		File jarFile = new File(jarPath);
//		
//		URL fileURL = jarFile.toURI().toURL();
//	    String jarURL = "jar:" + fileURL + "!/";
//	    URL urls [] = { new URL(jarURL) };
//	    
//	    URLClassLoader ucl = new URLClassLoader(urls);
//	    Class<?> c = Class.forName(path.getClassPath(), true, ucl);
//	    
//	    Constructor<?> con = c.getDeclaredConstructors()[0];
//		op = (Operator) con.newInstance(new Object[] {new String(id)});
//		
//		return op;
//	}
//    
}
