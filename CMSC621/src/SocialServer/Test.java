package SocialServer;

import java.io.IOException;
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
  public class MyJson {

		  public MyJson() {}

		  private PublicProfile publicprofile;
		  private PrivateProfile privateprofile;
		  private String gid;
		  private float coolness;
		  // other attributes

		  public void setgid(String gid) {
		    this.gid = gid;
		  }

		  public String getgid() {
		    return gid;
		  }

		  public void setCoolness(float coolness) {
		    this.coolness = coolness;
		  }

		  public float getCoolness() {
		    return coolness;
		  }
		  
		  public String toString() {
		        return String.format("pub:%s\npriv:%s", publicprofile,privateprofile);
		  }


  }
  public static void main(String args[]){
	  //JSONObject object=new JSONObject();
	  /*
	  object.put("name","Amit Kumar");
	  object.put("Max.Marks",new Integer(100));
	  object.put("Min.Marks",new Double(40));
	  object.put("Scored",new Double(66.67));
	  object.put("nickname","Amit");
	  System.out.println(object);
	  
	  JSONArray arrayObj=new JSONArray();
	  arrayObj.add("name :");
	  arrayObj.add("Amit Kumar");
	  arrayObj.add("Max.Marks :");
	  arrayObj.add(new Integer(50));
	  arrayObj.add("Min.Marks");
	  arrayObj.add(new Double(20));
	  arrayObj.add("Scored");
	  arrayObj.add(new Double(33.33));
	  System.out.println(arrayObj);
	  */
	  String jsonTxt=null;
	  try {
		jsonTxt = PeerUtils.readFiletoString("sample-json.txt");
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	 
	  /*
      JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );        
      double coolness = json.getDouble( "coolness" );
      int altitude = json.getInt( "altitude" );
      JSONObject pilot = json.getJSONObject("pilot");
      String firstName = pilot.getString("firstName");
      String lastName = pilot.getString("lastName");
      
      System.out.println( "Coolness: " + coolness );
      System.out.println( "Altitude: " + altitude );
      System.out.println( "Pilot: " + lastName );
      */
	  
      MyJson myJson = new Gson().fromJson(jsonTxt, MyJson.class);

      // now you have a real java object
     System.out.println(myJson);
      

      try {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


  }
}


/*
import java.io.*;
import java.lang.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test t = new Test();
		try{
		    t.methodA();
		    System.out.println("Hello world 2");
		}
		catch(IOException e){
			System.out.println("Message: IOException");
		}
		finally{
			
			System.out.println("hello world finally");
		}
		

	}
	
	public void methodA() throws IOException,SecurityException
	{
		int i = 50;
		
		if (i > 50){
			throw new IOException();
			
		}
		
		System.out.println("Value of i1: " + i);
		if (i < 50){
			throw new SecurityException();
		}
		System.out.println("Value of i2: " + i);
	}

}
*/