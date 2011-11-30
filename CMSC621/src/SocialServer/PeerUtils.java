package SocialServer;


import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class PeerUtils {

	
	
   public static String readFiletoString(String filePath) {
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(filePath));
	        f.read(buffer);
	    } catch (IOException e) {
			e.printStackTrace();
		}
	    finally {
	        if (f != null) try { f.close(); } catch (IOException ignored) { }
	    }
	    return new String(buffer);
	}
   
   public static void writeStringtoFile(String filePath, String data){
	   
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(data);
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
   }
   
	public static String getIPstr(InetAddress inputAddr) {
		byte[] ipAddr = inputAddr.getAddress();
		StringBuffer str = new StringBuffer();
		for(int i=0; i<ipAddr.length; i++) {
			if(i > 0) str.append('.');
			str.append(ipAddr[i] & 0xFF);				
		}
		return str.toString();
	}
	
	public static void printSocketParameters(Socket cl) throws SocketException{
		
		boolean SO_KEEPALIVE = cl.getKeepAlive(); 
		boolean TCP_NODELAY = cl.getTcpNoDelay();
		
		int SO_LINGER = cl.getSoLinger();
		int SO_TIMEOUT = cl.getSoTimeout();
		
		int SO_RCVBUF = cl.getReceiveBufferSize();
		int SO_SNDBUF = cl.getSendBufferSize();
		
		int trafficClassVal = cl.getTrafficClass();
		/*
		  		0 <= trafficClassVal <= 255
				IPTOS_LOWCOST (0x02)
				IPTOS_RELIABILITY (0x04)
    			IPTOS_THROUGHPUT (0x08)
				IPTOS_LOWDELAY (0x10)

		 */
		
		int remotePort = cl.getPort();
		int localPort = cl.getLocalPort();
		String localIP = getIPstr(cl.getLocalAddress());
		String remoteIP = getIPstr(cl.getInetAddress());
		
		System.out.println("Socket Paramaters :");
		System.out.println("SO_KEEPAILVE = "+SO_KEEPALIVE+" TCP_NODELAY = "+TCP_NODELAY);
		System.out.println("SO_LINGER = "+SO_LINGER+"  SO_TIMEOUT = "+SO_TIMEOUT);
		System.out.println("SO_RCVBUF = " +SO_RCVBUF+"  SO_SNDBUF = "+SO_SNDBUF);
		System.out.println("Traffic Class = "+trafficClassVal);
		System.out.println("Local Address = "+localIP+":"+localPort);
		System.out.println("Remote Address = "+remoteIP+":"+remotePort);
	}
}
