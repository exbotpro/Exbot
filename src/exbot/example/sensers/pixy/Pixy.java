package exbot.example.sensers.pixy;

import java.util.ArrayList;

import exbot.dev.core.interfaces.Operator;
import exbot.dev.core.interfaces.DataContainer;

public class Pixy extends Operator{
	private static double x=0;
	private static double y=1000;
	
	public Pixy(String id){
		super(id);
	}
	
	protected DataContainer performs(ArrayList<DataContainer> recievedData) {
		PixyDataContainer dataContainer = new PixyDataContainer(super.ID);
		
		x+=0.01;
		y-=0.01;
		try {
			(new Thread(this)).sleep(1000);
			dataContainer.set(new Coordinate(x, y));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataContainer;
	}
	
}
