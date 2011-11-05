package SocialServer;

import java.io.*;
import java.net.Socket;

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
	
    public PeerServerThread(Socket socket, long count) {
    super("PeerServerThread");
    this.clientSocket = socket;
    this.count = count;
    }
    
 
    public void run() {
 
    try {
		//Get socket input, output streams
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		String inputLine, outputLine;
		
		//Print Client Communication
		outputLine = "Peer Server v0.1 Ready";
		out.println(outputLine);
		
		outputLine = profile;
		inputLine = in.readLine();
		if (inputLine.equalsIgnoreCase("getData")){
		//while ((inputLine = in.readLine()) != null) {  
			//outputLine = "Message Received=[" + inputLine + "]";//debug
			out.println(outputLine);
			System.out.println("[SERVER] CLIENT-MSG: " + inputLine); 
		    //if (inputLine.equals("bye")) break;
		}
		System.out.println("[SERVER][THREAD] Client "+count+" Disconnected");
		
		//Close open streams and sockets
		out.close();
		in.close();
		clientSocket.close();
    }
    
    catch (IOException e) {
        e.printStackTrace();
    }
    }
}
