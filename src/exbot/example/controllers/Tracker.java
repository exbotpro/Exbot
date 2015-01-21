package exbot.example.controllers;

import java.util.ArrayList;

import exbot.dev.core.interfaces.DataContainer;
import exbot.dev.core.interfaces.Operator;
import exbot.example.sensers.pixy.Coordinate;

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
		for(DataContainer container: recievedData){
			System.out.println("Track getData:  " + ((Coordinate)container.getData()).getX() + ":" + ((Coordinate)container.getData()).getY()
					+ " from " + container.getPortFrom());
		}
		return new TrackDataContainer(super.ID);
	}

}