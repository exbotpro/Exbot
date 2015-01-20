package exbot.dev.core.comm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import exbot.dev.core.interfaces.Operator;

public class TCPObjectReciever extends Thread{

	private int port;
	
	public TCPObjectReciever(int port){
		this.port = port;
	}
	
	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
			while (true) {
	            try {
	                final Socket socketToClient = serverSocket.accept();
                	ObjectInputStream inputStream = new ObjectInputStream(socketToClient.getInputStream());
                    Object o = inputStream.readObject();
                    System.out.println("Read object: "+((Operator)o).getID());
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
