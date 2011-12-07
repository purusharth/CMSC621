package SocialServer;

import java.net.InetAddress;
//import com.google.gson.Gson;

/*
 * Junk code for testing
 */
public class Test
{

  public static void main(String args[]) throws Exception  {

	  /*
	  String jsonTxt = PeerUtils.readFiletoString("profile.json");  
      Profile myProf = new Gson().fromJson(jsonTxt, Profile.class);
      System.out.println(myProf);
      myProf.addFriend("Jason");
      System.out.println(myProf.isFriend("Jason"));
      System.out.println(myProf.getPubStr());
      //System.out.println(myProf.removeFriend("Jason"));
      //System.out.println(myProf.isFriend("Jason"));
       */
      

		System.out.println(InetAddress.getLocalHost().getHostAddress());
		System.out.println(InetAddress.getLocalHost());
		System.out.println(PeerUtils.getIPstr(InetAddress.getLocalHost()));
	
		
    /*
    PeerRequestHandler ph = new PeerRequestHandler(myProf);
    String jsonrequest = ph.makeRequest("1234","getData");
    System.out.println(jsonrequest);
    PeerUtils.writeStringtoFile("profile-request.json", jsonrequest);
    */
    
    DHT dht = new DHT();
    RSA rsa = new RSA(512);
    
    //Create New Profile
    Profile prof1 = new Profile();
    Social social = new Social(prof1, "profile.json", dht,rsa);
    social.createDefaultPofile();
    social.displayProfile();
    social.saveProfile();  //Save Profile to Disk
    social.dhtInsertNew(); //Insert Profile in DHT
    social.DisplayFriendList();
    social.DisplayStoredMessages();
    social.dhtDisplay();
    
    //Send Message to user puru.
    social.SendMessage("puru", "Hi there");
    social.SendMessage("puru", "How Art Thou");
    social.dhtDisplay();
    
    //Check for new messages
    social.dhtRetrieve();
    social.ProcessMessages();
    social.dhtUpdate();
    social.dhtDisplay();
    
    
    Profile prof2 = new Profile();
    Social social2 = new Social(prof2, "profile2.json", dht,rsa);
    social2.createDefaultPofile2();
    social2.displayProfile();
    social2.saveProfile();
    social2.dhtInsertNew();
    social2.dhtDisplay();
    
    //Send Friend Request
    social.SendFriendRequest("bryan","Lets Become Friends");
    social.dhtDisplay();
    
    //Check for new requests
    social2.dhtRetrieve();
    social2.ProcessRequests();
    social2.dhtUpdate();
    social2.dhtDisplay();
    
    //Process request response
    social.dhtRetrieve();
    social.ProcessRequests();
    social.dhtUpdate();
    social.dhtDisplay();
    social.DisplayFriendList();
    
    //Print Profile
    System.out.println(prof1);
    
    //-------------------------------------------------------------------------------


  }
}
