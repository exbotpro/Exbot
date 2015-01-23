package exbot.platform.download;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {
	
	public FileOutputStream downWithProxy(String repository, String path, String appName, String localPath){
		FileOutputStream out = null;
		try {
			URL website = new URL(repository + path + appName);
			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, 
					new InetSocketAddress("cache.kettering.edu",3128));
			
			URLConnection conn = website.openConnection(proxy);
			InputStream in = conn.getInputStream();
			
	        out = new FileOutputStream(localPath + "/" + appName);
	        
	        byte[] b = new byte[1024];
	        int count;
	        while ((count = in.read(b)) >= 0) {
	            out.write(b, 0, count);
	        }
	        out.flush();
			
	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out;
	}
	
	public FileOutputStream down(String repository, String path, String appName, String localPath){
		
		FileOutputStream out = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(repository + path + appName).openStream());
	        out = new FileOutputStream(localPath + "/" + appName);
	        
	        byte[] b = new byte[1024];
	        int count;
	        
	        while ((count = in.read(b)) >= 0) {
	            out.write(b, 0, count);
	        }
	        
	        out.flush();
	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out;
	}
	
}