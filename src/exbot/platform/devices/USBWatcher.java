package exbot.platform.devices;

import java.util.List;

import javax.usb.UsbDevice;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbServices;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;

import org.usb4java.Context;

import exbot.dev.core.interfaces.Operator;

public class USBWatcher implements Runnable{
	private AppFinder appInitiator = new AppFinder(); 
			
	public USBWatcher(){
		new Thread(this).start();
	}
	
	public void run(){
		this.scanDevice();
		this.findDevice(new Context(), (short) (0x046d), (short) (0xc077));
	}
	
	@SuppressWarnings("unchecked")
	private void scanDevice(){
		
		try {
			UsbServices services = UsbHostManager.getUsbServices();
			UsbHub hub = services.getRootUsbHub();
			List<UsbDevice> device = hub.getAttachedUsbDevices();
			
			for(UsbDevice d: device){
				String id = this.getID(d.toString());
				Operator op = appInitiator.getDevice(id);
				if(op!=null){
					System.out.println(id);
					AppFinder.registOperator(op);
					appInitiator.init(op);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (UsbException e) {
			e.printStackTrace();
		} catch (UsbDisconnectedException e) {
			e.printStackTrace();
		}
	}

	private String getID(String device){
		String[] tok = device.split(" ");
		return tok[tok.length-1];
	}
	
	public void findDevice(Context context, short vendorId, short productId) {
		 
		 try {
				UsbServices services = UsbHostManager.getUsbServices();
				services.addUsbServicesListener(new UsbServicesListener(){
					public void usbDeviceAttached(UsbServicesEvent arg0) {
						String id = getID(arg0.getUsbDevice().toString());
						Operator op = appInitiator.getDevice(id);
						if(op!=null){
							System.out.println("plugged device: "+id);
							AppFinder.registOperator(op);
							appInitiator.init(op);
						}else{
							System.err.println(id);
						}
						
					}

					public void usbDeviceDetached(UsbServicesEvent arg0) {
						String id = getID(arg0.getUsbDevice().toString());
						System.out.println("unplugged device: "+id);
						Operator op = Devices.getDeviceManager().getOperator(id);
						if(op!=null){
							op.stop(false);
							(new Thread(op)).stop();
							Devices.getDeviceManager().removeOperator(id);
						}
					}
					
		        });
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UsbException e) {
				// TODO Auto-generated catch block
			}
    }
}
