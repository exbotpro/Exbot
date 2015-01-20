package exbot.platform;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import exbot.dev.core.interfaces.Operator;
import exbot.platform.devices.Devices;
import exbot.platform.devices.USBWatcher;
import exbot.platform.devices.tables.LookupTableWrapper;

public class Control {

	public static void main(String[] args) throws InterruptedException {
		
		LookupTableWrapper.initTable();
		new USBWatcher();
		
		
		ConcurrentHashMap<String, Operator> map = Devices.getOperatingDevicesOperator();
		Enumeration<String> e = map.keys();
		while(e.hasMoreElements()) {
	        System.out.println(e.nextElement());
	    }
		
		
		while(true){}
	}
}
