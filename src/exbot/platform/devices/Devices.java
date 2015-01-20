package exbot.platform.devices;

import java.util.concurrent.ConcurrentHashMap;

import exbot.dev.core.interfaces.Operator;

public class Devices {

	private static Devices deivceManager;
	private static ConcurrentHashMap<String, Operator> operatingDevicesOperator = new ConcurrentHashMap<String, Operator>();
	private static ConcurrentHashMap<String, Integer> portTable = new ConcurrentHashMap<String, Integer>();
	
	private Devices(){}
	
	public static Devices getDeviceManager(){
		if(deivceManager==null){
			return new Devices();
		}else{
			return deivceManager;
		}
	}
	
	public static ConcurrentHashMap<String, Operator> getOperatingDevicesOperator() {
		return operatingDevicesOperator;
	}

	public static ConcurrentHashMap<String, Integer> getPortTable() {
		return portTable;
	}

	public void addPort(String id, int port){
		portTable.put(id, port);
	}
	
	public int getPort(String id){
		return portTable.get(id);
	}
	
	public void addOperator(Operator op){
		operatingDevicesOperator.put(op.getID(), op);
	}
	
	public Operator getOperator(String id){
		return operatingDevicesOperator.get(id);
	}
	
	public void removeOperator(String id){
		operatingDevicesOperator.remove(id);
		portTable.remove(id);
	}

}
