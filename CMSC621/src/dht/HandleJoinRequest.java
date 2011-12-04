package dht;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.PortUnreachableException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.StringTokenizer;

import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;

public class HandleJoinRequest extends Thread {
	//Thread handling the join request from new nodes
	//this thread will be running in the peers that are already joined in the chord
	private InetAddress bootstrapIP; //bootstrap IP of chord the peer belongs to
	private InetAddress iNetAddr; //IP of the peer (in the chord)
	public HandleJoinRequest(InetAddress bIP, InetAddress addr) {
		bootstrapIP = bIP;
		iNetAddr = addr;
	}

	public void run() {
		DatagramSocket socket;
		while (true) {
            try {
            	socket = new DatagramSocket(5001);
                byte[] buf = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress addr = packet.getAddress();
                int port = packet.getPort();
                String message = new String(buf);
                //peer retrieves the requested bootStrap IP from the incoming request
                StringTokenizer st = new StringTokenizer(message, ":");
                String reqBootStrapIP = ""; 
                while (st.hasMoreTokens()) {
                	reqBootStrapIP = st.nextToken();
                }
                //peer checks if it's a member of the requested bootstrap IP 
                if (InetAddress.getByName(reqBootStrapIP).equals(bootstrapIP.toString())) { 
                    System.out.println("Joining node to the chord with BootStrap=" + bootstrapIP);
                    Chord chord;
                    //1. peer joins the new node to its chord
                    //2. peer sends the new peer - peer IP + the bootstrap IP of its chord
                    try { 
                    	chord = new ChordImpl();
                    	chord.join(new URL(addr.getHostName()), new URL(bootstrapIP.getHostName()));
                        message = new String(iNetAddr.getHostAddress() + ":" + bootstrapIP.getHostName());  
                        buf = message.getBytes();
                        packet = new DatagramPacket(buf, buf.length, addr, port);
                        socket.send(packet);                	
                    } catch (ServiceException e) { 
                    	System.out.println("Service Failure: Joining the node to chord");
                    	System.out.println(e.getMessage());                	
                    } catch (IOException ie) {
                    	System.out.println("I/O or Unreachable Port Failure: Sending data packet to the new-joined node");
                    	System.out.println(ie.getMessage());
                    } catch (SecurityException se) {
                    	System.out.println("Security Failure: Sending data packet to the new-joined node");
                    	System.out.println(se.getMessage());
                    } catch (IllegalBlockingModeException be) {
                    	System.out.println("Blocking Mode Failure: Sending data packet to the new-joined node");
                    	System.out.println(be.getMessage());
                    } finally {
                    	if (socket != null) {
                    		socket.close();
                    	}
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
	}
	
}
