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
    Social social = new Social(prof1, "profile.json");
    social.createPofile();
    social.displayProfile();
    //social.saveProfile();
    social.DisplayFriendList();
    
    //-------------------------------------------------------------------------------
    DHTdata dd = new DHTdata();
    dd.setGID("sam");
    PublicProfile pp = new PublicProfile();
    pp.setName("Sam Bam");
    pp.setGID("sam");
    pp.setSex("Male");
    pp.addHobby("sleeping");
    dd.setPubProfile(pp);
    dd.addMessage("josh", "Hello Sam");
    dd.addMessage("jeremy", "Where you at?");
    dd.addRequest("jason", "Lets Become Friends");
    Gson gson = new Gson();
    System.out.println(gson.toJson(dd));
    //------------------------------------------------------------------------------

  }
}
