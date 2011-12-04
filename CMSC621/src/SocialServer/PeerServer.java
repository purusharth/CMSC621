package SocialServer;

import java.io.*;
import java.net.*;

public class PeerServer extends Thread{
	    
		private int port;
		private ServerSocket serverSocket = null;
		public static long threadCount = 0; //current number of running Server threads
		private Profile profile;
		
        public PeerServer(int port, Profile profile) {
        	super("PeerServer");
        	this.port = port;
        	this.profile = profile;
        }
        
        public void run() {
    		try {
    		    serverSocket = new ServerSocket(port);
    		} catch (IOException e) {
    		    System.out.println("[SERVER] ERROR: Could not create server socket on port: " + port);
    		    System.exit(-1);
    		}
    		System.out.println("[SERVER] Created Server Socket. " + serverSocket.toString()); 
    		
    		//Wait for Client to Connect
    		boolean listening = true;
    		Socket clSocket = null;
    		//InetAddress clientAddr;
    		long threadID = 1;
    		
    		while (listening){
    			try {
    				System.out.println("[SERVER] Waiting for another client to connect"); 
    			    clSocket = serverSocket.accept(); //wait for client to connect, no timeout
    				clSocket.setSoTimeout(5000);//Set Socket Timeout for terminating blocking read calls on InputStream

    				
    			} catch (IOException e) {
    			    System.out.println("[SERVER] ERROR: Accept failed on port=" + port);
    			    break; //System.exit(-1);
    			}
    			//clientAddr = serverSocket.getInetAddress();
    			System.out.println("[SERVER] Accepted Client "+threadID+" Connection.");
    			try {
					new PeerServerThread(clSocket,threadID++,profile).start();
				} catch (SocketException e) {
					e.printStackTrace();
				}
    		}
    		
    		try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("[SERVER] ERROR: Failed to close Server Socket");
				//e.printStackTrace();
			}
        }
         
        /*
         public static void main(String[] args) throws IOException {
         int port = 31337;
         ServerSocket serverSocket = null;
    	
    	//Create server socket and listen on specified port

	    
		try {
		    serverSocket = new ServerSocket(port);
		} catch (IOException e) {
		    System.out.println("ERROR: Could not create server socket on port: " + port);
		    System.exit(-1);
		}
		System.out.println("[INFO] Created Server Socket. " + serverSocket.toString()); 
		
		//Wait for Client to Connect
		boolean listening = true;
		Socket clSocket = null;
		InetAddress clientAddr;
		long count = 1;
		
		while (listening){
			try {
				System.out.println("SERVER: Waiting for another client to connect"); 
			    clSocket = serverSocket.accept();
			} catch (IOException e) {
			    System.out.println("ERROR: Accept failed on port=" + port);
			    break; //System.exit(-1);
			}
			clientAddr = serverSocket.getInetAddress();
			System.out.println("SERVER: Accepted Client "+count+" Connection. IP="+clientAddr.toString());
			new PeerServerThread(clSocket,count++).start();
		}
		serverSocket.close();
	}
	*/
}
