package exbot.example.controllers;

import exbot.dev.core.interfaces.ProducedData;

public class TrackData implements ProducedData{
	private String data;
	
	public TrackData(String data){
		this.data = data;
	}
}
