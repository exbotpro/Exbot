package exbot.example.controllers;

import java.util.ArrayList;

import exbot.dev.core.interfaces.Operator;
import exbot.dev.core.interfaces.DataContainer;

public class Tracker extends Operator{
	
	public Tracker(String id){
		super(id);
		super.setSubscribeFrom(this.setPublisher());
	}
	
	/**
	 * implement this method to subscribe data from other applications sensing environment 
	 */
	private ArrayList<String> setPublisher(){
		ArrayList<String> publisher = new ArrayList<String>();
		publisher.add("046d:c52b");
		return publisher;
	}
	

	protected DataContainer performs(ArrayList<DataContainer> recievedData) {

		return new TrackDataContainer(super.ID);
	}

}