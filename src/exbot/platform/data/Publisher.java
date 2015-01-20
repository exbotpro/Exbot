package exbot.platform.data;

import java.util.ArrayList;

import exbot.dev.core.interfaces.Operator;
import exbot.dev.core.interfaces.ProducedData;

public class Publisher {
	
	private ArrayList<Operator> subscribers = new ArrayList<Operator>();
	
	public void regist(Operator op){
		this.subscribers.add(op);
	}
	
	public void announce(ProducedData data){
		for(Operator sb: subscribers){
			
		}
	}
	
}
