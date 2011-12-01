package SocialServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
/*
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
//import org.apache.commons.io.IOUtils;
*/
import com.google.gson.*;

/*
 * Junk code for testing
 */
public class Test
{

  public static void main(String args[]){

	  
	  String jsonTxt = PeerUtils.readFiletoString("sample-json.txt");  
      Profile myProf = new Gson().fromJson(jsonTxt, Profile.class);
      System.out.println(myProf);
      myProf.addFriend("Jason");
      System.out.println(myProf.isFriend("Jason"));
      System.out.println(myProf.removeFriend("Jason"));
      System.out.println(myProf.isFriend("Jason"));
      

    try {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
    String jsonrequest = PeerRequestHandler.makeRequest("1234","getData");
    System.out.println(jsonrequest);
    PeerUtils.writeStringtoFile("profile.json", jsonrequest);

  }
}
