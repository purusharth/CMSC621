package SocialServer;

import java.util.ArrayList;

import com.google.gson.Gson;

public class DHTdata {
	public class MessageStruct {
		public String sid = "";
		public String val = "";
		
		public String toString(){
			return String.format("{\"sid\":\"%s\",\"val\":\"%s\"}",sid,val);
		}
	}
	
	private String gid=""; //User ID
	private String pubkey="RSAKEY"; //RSA Public Key
	private PublicProfile pubprofile = new PublicProfile(); //User Public Profile
	private ArrayList<MessageStruct> messages = new ArrayList<MessageStruct>(); //List of New Messages
	private ArrayList<MessageStruct> requests = new ArrayList<MessageStruct>(); //List of New Requests
	
	public String getGID() { return gid;}
	
	public void setGID(String id) { this.gid = id;}
	
	public String getPubKey() {return pubkey;}
	
	public void setPubKey(String pkey) { this.pubkey = pkey;}
	
	public PublicProfile getPubProfile() { return pubprofile;}
	
	public void setPubProfile(PublicProfile pubprof){ this.pubprofile = pubprof;}
	
  	public String getPubStr(){ //return Public Profile as JSON String
	  Gson gson = new Gson();
	  return gson.toJson(pubprofile);
  	}
  	
  	public String getNextMessage(){ //Get the next message and remove from list
  		MessageStruct ms = null;
  		if (messages.size() > 0) {
  			ms = messages.get(0); //Get Next Element
  			messages.remove(0); //Delete the Element from the list
  		}
  		Gson gson = new Gson();
  	    return gson.toJson(ms);
  	}
  	
  	public void addMessage(String gid, String message){
  		MessageStruct ms = new MessageStruct();
  		ms.sid = gid;
  		ms.val = message;
  		messages.add(ms);
  	}
	
  	public String getNextRequest(){ //Get the next message and remove from list
  		MessageStruct ms = null;
  		if (requests.size() > 0) {
  			ms = requests.get(0); //Get Next Element
  			requests.remove(0); //Delete the Element from the list
  		}
  		Gson gson = new Gson();
  	    return gson.toJson(ms);
  	}
  	
  	public void addRequest(String gid, String request){
  		MessageStruct ms = new MessageStruct();
  		ms.sid = gid;
  		ms.val = request;
  		requests.add(ms);
  	}
  	
}
