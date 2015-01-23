package exbot.platform.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileLoader
{

    public static void main (String args []) throws IOException, ClassNotFoundException
    {
    	File file = new File("C:/Users/zuna/git/Exbot/lib/camera.jar");
    	JarFile jar = new JarFile(file);
    	URL[] urls = {new URL("jar:" + file.toURI().toURL() + "!/")};
    	 
    	// It is important to set the parent ClassLoader in order to make sure that the new plugins
    	// and their folders will be added to the classpath correctly
    	URLClassLoader cl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
    	 
    	Enumeration e = jar.entries();
    	while (e.hasMoreElements()) {
    	    JarEntry entry = (JarEntry) e.nextElement();
    	 
    	    if (entry.getName().endsWith(".class")) {
    	        Class<?> clazz = cl.loadClass(extractClassName(entry));
//    	        if(AnalyzerPlugin.class.isAssignableFrom(clazz)) {
//    	            AnalyzerPlugin pl = (AnalyzerPlugin) clazz.newInstance();
//    	            loadedPlugins.put(pl.getInternalName(), pl);
//    	        }
    	    }
    	}
    }
    
    private static String extractClassName(JarEntry e){
    	String className = "";
    	String[] t = e.getName().split("\\.");
    	String te = t[0];
    	className = te.replace("/", ".");
    	
    	return className;
    }
}