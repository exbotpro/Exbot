package exbot.platform.devices;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import down.Downloader;
import down.Path;
import exbot.dev.core.device.Devices;
import exbot.dev.core.interfaces.NotFoundOperatorException;
import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.tables.LookupTableWrapper;

public class AppFinder {
	
	private Path path = new Path();
	
	
    public Operator getDevice(String id){
    	String classPath = LookupTableWrapper.getPath(id);
    	//if no app for the device is in the local place, search for repository in Internet and download it! 
    	if(classPath.equals("")) {
    		System.out.println("down");
    		path.findApp("046d:c52b");
    		String repository = path.getRepository();
    		String appPath = path.getPath();
    		String appName = path.getApp();
    		
    		Downloader downloader = new Downloader();
    		downloader.down(repository, appPath, appName, path.getLocalPath());
    		
    		classPath = path.getLocalPath() + "/" + appName;
    	}
    	
    	Operator op = null;
    	
		try {
			
//			if(classPath==null){ 
//				throw new NotFoundOperatorException("The app for the device [" + id + "] "
//						+ "that you have been put in cannot be found in the repository.");
//			}
					
			
			Class<?> c = Class.forName(classPath);
			Constructor<?> con = c.getDeclaredConstructors()[0];
			op = (Operator) con.newInstance(new Object[] {new String(id)});
			
		} catch (ClassNotFoundException e) {
//			System.err.println("A class for operating the device is not found");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
//		catch (NotFoundOperatorException e) {
//			System.err.println(e);
//		}
		
		return op;
	}
    
    public static void registOperator(Operator op){
		Devices.addOperator(op);
    }
    
	public void init(Operator op) {
		(new Thread(op)).start();
	}
}
