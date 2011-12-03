package SocialServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
/*
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
//import org.apache.commons.io.IOUtils;
*/
import com.google.gson.Gson;

/*
 * Junk code for testing
 */
public class Test
{

  public static void main(String args[]){

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
      

    try {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		System.out.println(InetAddress.getLocalHost());
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    /*
    PeerRequestHandler ph = new PeerRequestHandler(myProf);
    String jsonrequest = ph.makeRequest("1234","getData");
    System.out.println(jsonrequest);
    PeerUtils.writeStringtoFile("profile-request.json", jsonrequest);
    */
    Profile prof1 = new Profile();
    DHT dht = new DHT();
    Social social = new Social(prof1, "profile.json", dht);
    social.createDefaultPofile();
    social.displayProfile();
    //social.saveProfile();
    social.DisplayFriendList();
    social.DisplayMessages();
    //System.out.println(social.makeDHTdata()); //Create 
    social.dhtInsert(); //Insert
    social.dhtDisplay();
    social.SendMessage("puru", "Hi there");
    social.dhtDisplay();
    
    Profile prof2 = new Profile();
    Social social2 = new Social(prof2, "profile2.json", dht);
    social2.createDefaultPofile2();
    social2.displayProfile();
    social2.dhtInsert();
    social.dhtDisplay();
    
    
    //-------------------------------------------------------------------------------


  }
}
