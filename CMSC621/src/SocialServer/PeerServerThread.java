package SocialServer;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class PeerServerThread extends Thread{
	private Socket clientSocket = null;
	private long threadID=0;
	private Profile profile;
	
    public PeerServerThread(Socket socket, long count, Profile profile) throws SocketException {
    super("PeerServerThread");
    this.clientSocket = socket;
    this.threadID = count;
    this.profile = profile;
	PeerServer.threadCount++;
	//System.out.println("Current Number of threads: "+PeerServer.threadCount);
    //System.out.println("----------------Server Socket Parameters -------------");
    //PeerUtils.printSocketParameters(clientSocket);
    //System.out.println("------------------------------------------------------");
    }
    
 
    public void run() {
 
    try { 	
		//Get socket input, output streams
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		String inputLine = ""; 
		String outputLine;
		
		//Print Client Communication
		outputLine = "Peer Server v0.1 Ready";
		out.println(outputLine);
		
		try{
			inputLine = in.readLine();
			System.out.println("[SERVER]["+threadID+"]["+System.currentTimeMillis()+"] CLIENT-MSG: "+inputLine);
			PeerRequestHandler prh = new PeerRequestHandler(profile);
			outputLine = prh.getResponse(inputLine);
			out.println(outputLine);
			if (outputLine.length() == 0){
				System.out.println("[SERVER]["+threadID+"]["+System.currentTimeMillis()+"] Server Response: Empty String");
			}

		} catch (SocketTimeoutException e){
			System.err.println("[SERVER]["+threadID+"]["+System.currentTimeMillis()+"] "+
					"TIMEOUT: Timeout reached waiting for client "+threadID+" to respond. CLosing Connection.");
			
		}
		
		//Close open streams and sockets
		out.close();
		in.close();
		clientSocket.close();
		System.out.println("[SERVER]["+threadID+"]["+System.currentTimeMillis()+"] Client "+threadID+" Disconnected");
    }
    
    catch (IOException e) {
        e.printStackTrace();
    }
	finally{
		System.out.println("[SERVER]["+threadID+"]["+System.currentTimeMillis()+"] Peer Server ThreadID "+threadID+" Terminated");
		PeerServer.threadCount--;
		//System.out.println("Current Number of threads: "+PeerServer.threadCount);
	}
    }
}
