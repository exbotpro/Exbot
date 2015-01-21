package exbot.platform.data;

import java.util.ArrayList;

import exbot.dev.core.interfaces.DataContainer;
import exbot.dev.core.interfaces.Operator;

public class Publisher {
	
	private ArrayList<Operator> subscribers = new ArrayList<Operator>();
	private String masterDeviceID;
	
	
	public Publisher(String masterDeviceID) {
		this.masterDeviceID = masterDeviceID;
	}

	public void regist(Operator op){
		this.subscribers.add(op);
	}
	
	public void announce(DataContainer data){
		for(Operator sb: subscribers){
			sb.getDataRepo().get(masterDeviceID).setData(data);
		}
	}
	
}