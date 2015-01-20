package exbot.dev.core.comm;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import exbot.dev.core.interfaces.Operator;

public class TCPObjectSender {

	public static void sendObject(String host, int port, Operator op){
		
		try {
			
			Socket socket = new Socket(host, port);
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			outStream.writeObject(op);
			outStream.flush();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
