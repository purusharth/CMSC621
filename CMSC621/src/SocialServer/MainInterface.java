package SocialServer;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

public class MainInterface {
	
	public static void main(String[] args) throws Exception {
		
		//String nodeIP = InetAddress.getLocalHost().getHostAddress(); //ServerIP
		int port = 31337; //Server Port
		
		//Create/Join DHT
		DHT dht = new DHT();
		
		//Create New RSA Key-Pair
		RSA rsa = new RSA();
		
		//Create New Profile
	    Profile profile = new Profile();
	    Social social = new Social(profile, "profile.json", dht, rsa); //Create Social object for profile 
	    //social.createDefaultPofile(); //Create a Default Profile and populate with values
	    //social.dhtInsertNew(); //Insert New Profile in DHT.
	    //Profile prof2 = new Profile();
	    //Social social2 = new Social(prof2, "profile2.json", dht);
	    //social2.createDefaultPofile2();
	    //social2.dhtInsertNew();
		

		new PeerServer(port,profile).start(); 
		//Start Server Thread, pass instance of User Profile
		//When new request comes it is handed off to a new instance PeerRequestHandler 
		//with reference to current profile 
		
		char option='0';
	    Scanner input=new Scanner(System.in); 
	    //BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	    String userID="";
	    String temp="";
	    
		while(option!='q'){
			System.out.print(
					"Option Menu\n"+
					"  0 : Print DHT Entries on this node\n"+
					"  1 : Create Default Profile\n"+
					"  2 : Display Current Profile\n"+
					"  3 : Get Public Profile\n"+
					"  4 : Get Private Profile\n"+
					"  5 : Send Message\n"+
					"  6 : Send Friend Request\n"+
					"  7 : Check New Messages\n"+
					"  8 : Check Pending Requests\n"+
					"  9 : Display Stored Messages\n"+
					"  f : Display Friends\n"+
					"  c : Display DHT Details\n"+
					"  l : Load Profile From Disk\n"+
					"  s : Save Profile to Disk\n"+
					"  q : Exit & Leave Chord Network\n"+
					" Enter Choice:"
					//Load Profile, Save Profile, Display Friends, Display Public Profile, Display Private Profile
					//Server Status, Port Parameters
					//Chord Status, Display Finger Table, Display Pred, Display Succ, Display
					);
			temp = input.next();
			option = temp.charAt(0);
			//temp = JOptionPane.showInputDialog("Enter another value\n");
			//JOptionPane.showMessageDialog(null, temp);
			
			switch(option){
			
			case '0':
				dht.print();
				break;
			case '1':
				social.createDefaultPofile();
				social.dhtInsertNew();
				//System.out.println("Profile created with default values");
				JOptionPane.showMessageDialog(null, "Profile created with default values");
				//System.out.println("Enter any key to return to Menu");
				//temp = input.next(); 
				break;
				
			case '2': 
				System.out.println("------------Current Profile----------------");
				//System.out.println(profile);
				JOptionPane.showMessageDialog(null, profile);
				//System.out.println("Enter any key to return to Menu");
				//temp = input.next(); 
				break;
				
			case '3': 
			{
				System.out.println("------------Get Public Profile----------------");
				System.out.print("Enter the userID:");
				userID = input.next();
				
				String dhtdata = dht.retrieve(userID);
				if (dhtdata != null){
					Gson gson = new Gson();
					DHTdata dd = gson.fromJson(dhtdata, DHTdata.class);
					PublicProfile pp = dd.getPubProfile();
					System.out.println("Public Profile of "+userID+":");
					System.out.println(pp);
				}else{
					System.out.println("User does not Exist.");
				}

				System.out.println("Enter any key to return to Menu");
				temp = input.next();
				break;
			}	
			
			case '4': 
			{
				System.out.println("------------Get Private Profile----------------");
				System.out.print("Enter the userID:");
				userID = input.next();
				
				String dhtdata = dht.retrieve(userID);
				if (dhtdata != null){
					Gson gson = new Gson();
					DHTdata dd = gson.fromJson(dhtdata, DHTdata.class);
					String userIP = dd.getIP();
				
					String clRequest = social.makeRequest(profile.getGID(),"getPrivateProfile");
				
					//Connect to Peer directly
					Socket clSocket =  PeerClient.connect(userIP,port); //Open Connection
					String srvMsg =  PeerClient.sendRequest(clSocket, clRequest); //Send Request & Get Response
					PeerClient.disconnect(clSocket); //Close Connection
				
					System.out.println("Private Profile of "+userID+":");
					System.out.println(srvMsg);
				} else{
					System.out.println("User does not Exist.");
				}
				System.out.println("Enter any key to return to Menu");
				temp = input.next();
				break;
			}
			
			case '5': 
			{
				System.out.println("------------Send Message----------------");
				System.out.print("Enter the userID:");
				userID = input.next();
				System.out.print("Enter the Message:");
				String message = input.next();
				social.SendMessage(userID, message); //If user does not exist, then no message sent
				System.out.println("Message Sent");
				System.out.println("Enter any key to return to Menu");
				temp = input.next();
				break;
			}
			case '6': 
			{
				System.out.println("------------Send Friend Request----------");
				System.out.print("Enter the userID:");
				userID = input.next();
				System.out.print("Enter the Message:");
				String request = input.next();
				social.SendFriendRequest(userID, request);
				System.out.println("Request Sent");
				System.out.println("Enter any key to return to Menu");
				temp = input.next();
				break;
			}
			case '7': 
				System.out.println("----------Display New Messages-------------");
			    social.dhtRetrieve();
			    social.ProcessMessages();
			    social.dhtUpdate();
				break;
			case '8': 
				System.out.println("----------Process Pending Requests---------");
			    social.dhtRetrieve();
			    social.ProcessRequests();
			    social.dhtUpdate();
				break;
			case '9': 
			{
				System.out.println("\n----------Display Old Messages--------------");
				social.DisplayStoredMessages();
				System.out.println("\nEnter any key to return to Menu");
				temp = input.next();
				break;
			}
			case 'f': 
			{
				System.out.println("\n----------Display Friends-------------------");
				social.DisplayFriendList();
				System.out.println("\nEnter any key to return to Menu");
				temp = input.next();
				break;
			}
			case 'c': 
			{
				System.out.println("\n----------Display DHT Information------------");
				dht.print();
				dht.printsp();
				System.out.println("\nEnter any key to return to Menu");
				temp = input.next();
				break;
			}
			case 'l': 
			{
				System.out.println("\n----------Load Profile From Disk--------------");
				social.LoadProfile();
				System.out.println("\nEnter any key to return to Menu");
				temp = input.next();
				break;
			}
			case 's': 
			{
				System.out.println("\n----------Save Profile to Disk----------------");
				social.saveProfile();
				System.out.println("\nEnter any key to return to Menu");
				temp = input.next();
				break;
			}
			default: option='q'; dht.leave(); break;	
			}
		}
		System.out.println("Program Terminated\n");
		System.exit(0);
		
	}
}
