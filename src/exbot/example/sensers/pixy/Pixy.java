package exbot.example.sensers.pixy;

import exbot.dev.core.interfaces.Operator;
import exbot.dev.core.interfaces.ProducedData;

public class Pixy extends Operator{
	
	public Pixy(String id){
		super(id);
	}
	
	protected ProducedData performs() {
		return new PixyData("P");
	}
	
}
