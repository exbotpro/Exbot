package exbot.platform.devices;

import java.util.HashMap;

import exbot.dev.core.interfaces.Operator;

public class Devices {

	public static HashMap<String, Operator> operatingDevicesOperator = new HashMap<String, Operator>();
	public static HashMap<String, Integer> portTable = new HashMap<String, Integer>();
	
	public static HashMap<String, Operator> getOperatingDevicesOperator() {
		return operatingDevicesOperator;
	}

	public static HashMap<String, Integer> getPortTable() {
		return portTable;
	}

	public static void addPort(String id, int port){
		portTable.put(id, port);
	}
	
	public static int getPort(String id){
		return portTable.get(id);
	}
	
	public static void addOperator(Operator op){
		operatingDevicesOperator.put(op.getID(), op);
	}
	
	public static Operator getOperator(String id){
		return operatingDevicesOperator.get(id);
	}
	
	public static void removeOperator(String id){
		operatingDevicesOperator.remove(id);
		portTable.remove(id);
	}

}
