package exbot.dev.core.interfaces;

import java.util.ArrayList;
import java.util.Hashtable;

import exbot.dev.core.comm.Publisher;
import exbot.dev.core.device.Devices;


public abstract class Operator implements Runnable{
	protected String ID;
	protected boolean running = true;
	protected ArrayList<String> subscribeFrom = new ArrayList<String>();
	private Publisher publisher;
	private Hashtable<String, Buffer> dataRepo;
	
	/**
	 * This constructor is to set the ID of your application 
	 * @param id: USB-ID (e.g., productID: vendorID)
	 */
	public Operator(String id) {
		this.ID = id;
		this.publisher = new Publisher(id);
		this.dataRepo = new Hashtable<String, Buffer>();
	}
	
	/**
	 * This method is for Controller and Actuator Application
	 * if you want to subscribe from publishers, 
	 * call this method for registering your application to the publishers you delivers
	 * @param subscribeFrom: list of publishers
	 */
	public void setSubscribeFrom(ArrayList<String> subscribeFrom) {
		this.subscribeFrom = subscribeFrom;
	}
	

	/**
	 * This method is for Controller and Actuator applications
	 * @return
	 */
	public ArrayList<String> getSubscribeFrom() {
		return subscribeFrom;
	}
	
	
	/**
	 * This method is for Controller and Actuator applications.
	 * The method requests to regist the current operator to other applications 
	 * that the current operator wants to be subscribed from.
	 */
	public void requestToRegist(){
		try{
			for(String id: subscribeFrom){
				Devices.operatingDevicesOperator.size();
				Operator op = Devices.getOperator(id);
				if(op!=null){
					op.getPublisher().regist(this); //regist the current operator to the publisher of the operator producing data that the current want to recieve.
					this.dataRepo.put(id, new Buffer());
				}else{
					//If the operator is not exist, throw the "NotFoundOperatorException"
					throw new NotFoundOperatorException("The Operator '" + id + "' is not found\n");
				}
			}
			
		}catch(NotFoundOperatorException e){
			//If this exception is thrown, the re-scan the devices to figure out the device truly doesn't exist.
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	/**
	 * Main method to run the operator (device)
	 * The method is infinitely run until "running" flag turns into false value.
	 * When the operator runs, 1) getting recieved data, producing new data in DataContainer,
	 * 2) announcing the produced data to the its subscribers using publisher are carried out.
	 */
	public void run() {
		while(running){
			DataContainer data = this.performs(this.getRecievedData());
			this.publisher.announce(data);
		}
	}

	private ArrayList<DataContainer> getRecievedData() {
		ArrayList<DataContainer> recievedData = new ArrayList<DataContainer>();
		
		for(String device: subscribeFrom){
			Buffer b = this.dataRepo.get(device);
			if(b!=null && b.getData()!=null){
				recievedData.add(b.getData().clone());
				this.dataRepo.get(device).setData(null);
			}
		}
		
		return recievedData;
	}

	public void stop(boolean running) {
		this.running = running;
	}
	
	public Publisher getPublisher() {
		return publisher;
	}
	
	public Hashtable<String, Buffer> getDataRepo() {
		return dataRepo;
	}

	public String getID() {
		return this.ID;
	}
	
	/**
	 * implement this method for carrying out the functionality of the applications you are developing.
	 */
	protected abstract DataContainer performs(ArrayList<DataContainer> recievedData);
	
	public class Buffer {
		
		private DataContainer data;
		
		public DataContainer getData() {
			return data;
		}
		
		public void setData(DataContainer data) {
			this.data = data;
		}
		
	}
}
