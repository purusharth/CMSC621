package SocialServer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

public class PeerMain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		String nodeIP = "127.0.0.1";  //Server IP
		int port = 31337; //Server Port
		
		String fileTxt = PeerUtils.readFiletoString("profile.json"); //Read User Profiles
		Profile profile = new Gson().fromJson(fileTxt, Profile.class); //Parse User Profile from JSON format
		PeerRequestHandler ph = new PeerRequestHandler(profile); //Initialize Request Handler Object
		
		new PeerServer(port,profile).start(); //Start Server Thread, pass instance of User Profile
		
		//Run Client Thread
		Socket clSocket =  PeerClient.connect(nodeIP,port); //Open Connection
		
		System.out.println("----------------Client Socket Parameters -------------");
		PeerUtils.printSocketParameters(clSocket);
		
		
		String clRequest = ph.makeRequest("1234","getPublicProfile");
		String srvMsg =  PeerClient.sendRequest(clSocket, clRequest); //Send Request & Get Response
		PeerClient.disconnect(clSocket); //Close Connection
		System.out.println("[CLIENT] RECEIVED-MSG: " + srvMsg); //Print Response
		
	}

}
