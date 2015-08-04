package server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerThread extends Thread {

	protected DatagramSocket socket;
	private Server server = new Server();
	private Boolean singleton = true;
	private ArrayList<String> addresses;
	
	public ServerThread(Server server) throws Exception{
		this.server = server;
		socket = new DatagramSocket(7777);
		addresses = new ArrayList<String>();
	}


	public void run(){
		DatagramPacket packet;
		InetAddress address = null;
		int port = 0;
		while(true){
			try{
				
			if(singleton){
				singleton = false;
			byte[] buf = new byte[512];
            // receive request
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            addresses.add("" + packet.getAddress() + packet.getPort());
            address = packet.getAddress();
            port = packet.getPort();
			}

            byte[] buf2 = new byte[512];
            buf2 = server.getMessenger().getBytes();
            packet = new DatagramPacket(buf2, buf2.length, address, port);
            socket.send(packet);

			}
			catch(Exception e){};
		}
		
		
	}
}
