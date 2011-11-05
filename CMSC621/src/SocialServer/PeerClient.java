package SocialServer;

import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;

public class PeerClient {
	//connect
	//sendrequest(getdata)
	//disconnect
	
	public static Socket connect(String nodeIP,int port) throws UnknownHostException,IOException{
		 Socket clientSocket = null;
		 InetAddress nodeAddr = null;
		 InetAddress serverAddr = null;
		 
		try {
			   nodeAddr = InetAddress.getByName(nodeIP);
		} catch (UnknownHostException e) {
				System.err.println("[CLIENT] ERROR: Unknown Host: " + nodeIP);
				throw e;
		}
		 
		 
	    try {
				System.out.println("[CLIENT] Connecting to Server: " + PeerUtils.getIPstr(nodeAddr) +":" + port);
	            clientSocket = new Socket(nodeIP, port);
				serverAddr = clientSocket.getInetAddress() ;
				
	    } catch (UnknownHostException e) {
	            System.err.println("[CLIENT] ERROR: Unable to find IP Address: " + nodeAddr.toString());
	            throw e;
	    } catch (IOException e) {
	            System.err.println("[CLIENT] ERROR: I/O Exception for connection to " + nodeAddr.toString());
	            throw e;
	    }
	    
	    System.out.println("[CLIENT] Connection Established to Server: " + PeerUtils.getIPstr(serverAddr));
		return clientSocket;
	}
	
	public static void disconnect(Socket clientSocket) throws IOException{
		clientSocket.close();
	}
	
	public static String sendRequest(Socket clientSocket, String request) throws IOException{
        PrintWriter out = null;
        BufferedReader in = null;
		out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverOutput=null;

		if ((serverOutput = in.readLine()) != null) {
			System.out.println("[CLIENT] SERVER-INFO: " + serverOutput);
		}
        out.println(request);
		if ((serverOutput = in.readLine()) != null) {
			//System.out.println("SERVER-RESPOSNSE: " + serverOutput);
		}
		out.close();
		in.close();
		
		return serverOutput;
	}
	

	/*
    public static void main(String[] args) throws IOException {

		String nodeIP = "127.0.0.1";
		int port = 31337;
		Socket clSocket = null;
		String srvMsg = null;
		
		clSocket = connect(nodeIP,port);
		srvMsg = sendRequest(clSocket, "getData");
		disconnect(clSocket);
		System.out.println("RECEIVED-MSG: " + srvMsg);
		
		
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		InetAddress nodeAddr = InetAddress.getByName(nodeIP);
		//InetAddress nodeIP = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1});
		InetAddress serverAddr = null;
		String userInput;
		String serverOutput;

        try {
			System.out.println("[INFO] Connecting to Server: " + nodeAddr.toString() +":" + port);
            clientSocket = new Socket(nodeIP, port);
			serverAddr = clientSocket.getInetAddress() ;
			System.out.println("[INFO] Connection Established: " + serverAddr.toString());
        } catch (UnknownHostException e) {
            System.err.println("ERROR: Unable to find Host: " + nodeAddr.toString());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("ERROR: I/O Exception for connection to " + nodeAddr.toString());
            System.exit(1);
        }
		
		//Get Socket Streams
		out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//Create Input Stream
		BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));

		//Print Client Server Communicationl
		if ((serverOutput = in.readLine()) != null) {
			System.out.println("RAW-SERVER-OUTPUT: " + serverOutput);
			Data data = new Gson().fromJson(serverOutput, Data.class);
			System.out.println("PARSED-DATA: " + data);
			//System.out.println("PARSED-DATA: " + Arrays.toString(data.getHobbies()));
		}
		
		//while ((userInput = stdIn.readLine()) != null) {
	    //	out.println(userInput);
	    //	if ((serverOutput = in.readLine()) != null) System.out.println("SERVER: " + serverOutput);
		//	if (userInput.equals("bye")) break;
		//}
		System.out.println("[INFO] Disconnecting from Server");
		
		out.close();
		in.close();
		stdIn.close();
		clientSocket.close();
		
    }
    */


}