package SocialServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class PeerServerThread extends Thread{
	private Socket clientSocket = null;
	private long count = 0;
    
	private String profile = 
		"{"
			+"'gid':'123456AB',"
			+"'name':'someone',"
			+"'age':25,"
			+"'hobbies':['reading','baseball','football'],"
			+"'sex':'male'"
		+"}";
	
    public PeerServerThread(Socket socket, long count) throws SocketException {
    super("PeerServerThread");
    this.clientSocket = socket;
    this.count = count;
    System.out.println("----------------Server Socket Parameters -------------");
    PeerUtils.printSocketParameters(clientSocket);
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
		
		outputLine = profile;
		try{
			inputLine = in.readLine();
			if (inputLine.equalsIgnoreCase("getData")){
				out.println(outputLine);
				System.out.println("[SERVER] CLIENT-MSG: " + inputLine); 
			}
		} catch (SocketTimeoutException e){
			System.err.println("TIMEOUT: Timeout reached waiting for client "+count+" to respond. CLosing Connection.");
			
		}
		
		//Close open streams and sockets
		out.close();
		in.close();
		clientSocket.close();
		System.out.println("[SERVER][THREAD] Client "+count+" Disconnected");
    }
    
    catch (IOException e) {
        e.printStackTrace();
    }
	finally{
		System.out.println("Peer Server Thread "+count+" Terminated");
	}
    }
}
