package exbot.dev.core.interfaces;

import java.util.ArrayList;
import java.util.Hashtable;

import exbot.platform.data.Publisher;
import exbot.platform.devices.Devices;


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
	
	
	public ArrayList<String> getSubscribeFrom() {
		return subscribeFrom;
	}
	
	/**
	 * if you want to subscribe from publishers, 
	 * call this method for registering your application to the publishers you delivers
	 * @param subscribeFrom: list of publishers
	 */
	public void setSubscribeFrom(ArrayList<String> subscribeFrom) {
		this.subscribeFrom = subscribeFrom;
	}

	public void requestToRegist(){
		try{
			for(String id: subscribeFrom){
				Operator op = Devices.getOperator(id);
				if(op!=null){
					op.getPublisher().regist(this);
					this.dataRepo.put(id, new Buffer());
				}else{
					throw new NotFoundOperatorException("The Operator '" + id + "' is not found\n");
				}
			}
			
		}catch(NotFoundOperatorException e){
			System.err.println(e);
		}
	}
	
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
			Devices.operatingDevicesOperator.size();
			if(b!=null && b.getData()!=null){
				recievedData.add(b.getData().clone());
//				b.getData().getDataList().clear();
				b.getData().set(null);
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
