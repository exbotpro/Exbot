package controller;

import java.io.File;
import java.util.ArrayList;

import sensor.ImageDataContainer;
import exbot.dev.core.interfaces.DataContainer;
import exbot.dev.core.interfaces.Operator;

public class Controller extends Operator{

	public Controller(String id) {
		super(id);
		super.setSubscribeFrom(this.getPublishingDevice());
		// TODO Auto-generated constructor stub
	}

	private ArrayList<String> getPublishingDevice(){
		ArrayList<String> publishingDevice = new ArrayList<String>();
		publishingDevice.add("046d:c52b");
		return publishingDevice;
	}
	
	protected DataContainer performs(ArrayList<DataContainer> recievedData){
		DataContainer controlDataContainer = new ControlData(super.ID);
		double x = 0, y = 0;
		
		// TODO Auto-generated method stub
		for(DataContainer dc: recievedData){
			DataContainer wcd=null;
			
			if(dc instanceof ImageDataContainer){
				wcd = (ImageDataContainer)dc;
				File image = (File) wcd.getData();
				try{
					System.out.println(super.ID + ": the image of " + image.getName());
					x = this.getDegX(image.getName());
					y = 1000-x;
				
				}catch(Exception e){
					System.out.println("---");
				}
				
			}
			
		}
		
		controlDataContainer.set(new WheelControlData(x, y, 0, 0));
		return controlDataContainer;
	}
	
	private double getDegX(String name) throws Exception{
		String[] splitName = name.split("\\.");
		name = splitName[0];
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0 ; i < name.length() ; i++){
			if(Character.isDigit(name.charAt(i))){
				sb.append(name.charAt(i));
			}
		}
		
		return Double.parseDouble(sb.toString());
	}

}
