/*package dht;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class BroadCast {
	private int listenPort = 5001;
	private DatagramSocket socket;
	public BroadCast() {
		try {
			socket = new DatagramSocket(null);
			//socket.setBroadcast(true);			
			//socket.connect(InetAddress.getLocalHost(), listenPort);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	public void listen() {
		try {
			socket.bind(new InetSocketAddress(InetAddress.getLocalHost(),listenPort)) ;
			socket.connect(InetAddress.getByName(""))
			//socket.setBroadcast(true);			
			//socket.connect(InetAddress.getLocalHost(), listenPort);
            try {
                byte[] buf = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                System.out.println("Listening for packets on " + listenPort + "...");
                socket.receive(packet);
    			System.out.println("I am here.");                
                String message = new String(buf);
                System.out.println("Recieved: " + message);
                if (message.equals("end"))
                    return; 
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            socket.close();
		} catch (Exception e) {
	        System.err.println("Listening failed. " + e.getMessage());
	    }
	}
	public void send(String msg) {
	    try {
	    	socket.connect(InetAddress.getByName("255.255.255.255"),listenPort); 
			//socket.setBroadcast(true);
			byte[] buf= msg.getBytes();
		    DatagramPacket packet= new DatagramPacket(buf, buf.length);
			socket.send(packet);
			socket.close();
	    } catch (Exception e) {
	        System.err.println("Sending failed. " + e.getMessage());
	    } 
	}
	
	public static void main(String[] args) throws SocketException {
		BroadCast b = new BroadCast();
		b.listen();
		b.send("Test Message ...");
	}

}
*/