package exbot.platform.devices;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.tables.LookupTableWrapper;

public class AppFinder {
	
    public Operator getDevice(String id){
    	final String classPath = LookupTableWrapper.getPath(id);
    	Operator op = null;
		try {
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
		
		return op;
	}
    
    public static void registOperator(Operator op){
		Devices.getDeviceManager().addOperator(op);
    }

	public void init(Operator op) {
		(new Thread(op)).start();
	}
}
