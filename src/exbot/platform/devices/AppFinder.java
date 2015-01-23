package exbot.platform.devices;

import java.lang.reflect.Constructor;

import exbot.dev.core.device.Devices;
import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.tables.LookupTableWrapper;
import exbot.platform.download.Downloader;
import exbot.platform.download.Loader;
import exbot.platform.download.Path;

public class AppFinder {
	
	private Path path;
	
    public Operator getDevice(String id){
    	
    	Operator op = null;
		try {
			String classPath = LookupTableWrapper.getElement(id, "path");
			String jarPath = LookupTableWrapper.getElement(id, "jar");
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
		downloader.down(repository, appPath, appName, Path.localAppRepostoryPath);
		jarPath = Path.localAppRepostoryPath + appName;
		op = Loader.load(id, path.getClassPath(), jarPath);		
		
		return op;
	}

	
	
    public static void registOperator(Operator op){
		Devices.addOperator(op);
    }
    
	public void init(Operator op) {
		(new Thread(op)).start();
	}
	
}
