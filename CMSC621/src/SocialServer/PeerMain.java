package SocialServer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PeerMain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		String nodeIP = "127.0.0.1";  //Server IP
		int port = 31337; //Server Port
		
		new PeerServer(port).start(); //Start Server Thread
		
		//Run Client Thread
		Socket clSocket =  PeerClient.connect(nodeIP,port); //Open Connection
		
		System.out.println("----------------Client Socket Parameters -------------");
		PeerUtils.printSocketParameters(clSocket);
		
		String srvMsg =  PeerClient.sendRequest(clSocket, "getData"); //Send Request & Get Response
		PeerClient.disconnect(clSocket); //Close Connection
		System.out.println("[CLIENT] RECEIVED-MSG: " + srvMsg); //Print Response
		
	}

}
