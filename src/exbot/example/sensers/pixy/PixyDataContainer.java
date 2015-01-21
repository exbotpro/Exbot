package exbot.example.sensers.pixy;

import exbot.dev.core.interfaces.DataContainer;

public class PixyDataContainer extends DataContainer{
	
	public PixyDataContainer(String deviceID) {
		super(deviceID);
	}
	
	public void set(Coordinate coordinate){
		super.set(coordinate);
	}
	
}