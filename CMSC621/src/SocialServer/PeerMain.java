package SocialServer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

public class PeerMain {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		String nodeIP = "127.0.0.1";  //Server IP
		int port = 31337; //Server Port
		
		//Load Profile
		String fileTxt = PeerUtils.readFiletoString("profile.json"); //Read User Profiles
		Profile profile = new Gson().fromJson(fileTxt, Profile.class); //Parse User Profile from JSON format
		PeerRequestHandler ph = new PeerRequestHandler(profile); //Initialize Request Handler Object
		
		//Start Server Thread
		new PeerServer(port,profile).start(); //Start Server Thread, pass instance of User Profile
		
		//Client1: Start Thread
		Socket clSocket =  PeerClient.connect(nodeIP,port); //Open Connection
		
		//Client2: Start Thread
		Socket clSocket2 =  PeerClient.connect(nodeIP,port); //Open Connection
		
		//Print Client Socket Parameters.
		//System.out.println("----------------Client Socket Parameters -------------");
		//PeerUtils.printSocketParameters(clSocket);
		
		//Client2: Send Request, Receive Response
		String clRequest2 = ph.makeRequest("sam","getPrivateProfile");
		String srvMsg2 =  PeerClient.sendRequest(clSocket2, clRequest2); //Send Request & Get Response
		PeerClient.disconnect(clSocket2); //Close Connection
		System.out.println("[CLIENT][2]["+System.currentTimeMillis()+"] RECEIVED-MSG: " + srvMsg2); //Print Response
		
		Thread.sleep(2000);
		
		//Client1: Send Request, Receive Response
		String clRequest = ph.makeRequest("1234","getPublicProfile");
		String srvMsg =  PeerClient.sendRequest(clSocket, clRequest); //Send Request & Get Response
		PeerClient.disconnect(clSocket); //Close Connection
		System.out.println("[CLIENT][1]["+System.currentTimeMillis()+"] RECEIVED-MSG: " + srvMsg); //Print Response
		
	}

}
