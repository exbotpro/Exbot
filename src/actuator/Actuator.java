package actuator;

import java.util.ArrayList;

import controller.WheelControlData;
import exbot.dev.core.interfaces.DataContainer;
import exbot.dev.core.interfaces.Operator;

public class Actuator extends Operator{

	public Actuator(String id) {
		super(id);
		super.setSubscribeFrom(this.getPublishingDevice());
		// TODO Auto-generated constructor stub
	}

	private ArrayList<String> getPublishingDevice(){
		ArrayList<String> publishingDevice = new ArrayList<String>();
		publishingDevice.add("b1ac:f000");
		return publishingDevice;
	}

	protected DataContainer performs(ArrayList<DataContainer> recievedData) {
		// TODO Auto-generated method stub
		for(DataContainer dc: recievedData){
			WheelControlData data = (WheelControlData)dc.getData();
			System.out.println("Actuator: degee X: " + data.getDegX() + ", degree Y: " + data.getDegY());
		}
		
		return null;
	}
}
