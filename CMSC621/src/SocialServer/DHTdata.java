package SocialServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.ArrayList; 

import com.google.gson.Gson;

public class DHTdata {
	public class RequestStruct {
		public String sender = "";
		public String type = ""; //MSG, REQUEST, ACCEPT, DENY, ACK
		public String msg = "";
		public long expiry = 0; //Request/Response not valid after expiry timestamp, 0=forever
		
		public String toString(){
			//return String.format("{\"sender\":\"%s\",\"type\":\"%s\",\"msg\":\"%s\",\"expiry\":\"%d\"}",sender,type,msg,expiry);
			return String.format("sender: %s, message: %s",sender,msg);
		}
	}
	
	public class MessageStruct {
		public String sender = "";
		public String msg = "";
		public long time = 0; //Request/Response not valid after expiry timestamp, 0=forever
		
		public String toString(){
			//return String.format("{\"sender\":\"%s\",\"type\":\"%s\",\"msg\":\"%s\",\"expiry\":\"%d\"}",sender,type,msg,expiry);
			return String.format("sender: %s, message: %s, time: %d",sender,msg,time);
		}
	}
	
	private String gid=""; //User ID
	private String ip=""; //Last known IP-Address
	private long ts=0; //Last Seen Timestamp
	private String pubkey="RSAKEY"; //RSA Public Key
	private PublicProfile pubprofile = new PublicProfile(); //User Public Profile
	private ArrayList<MessageStruct> messages = new ArrayList<MessageStruct>(); //List of New Messages
	private ArrayList<RequestStruct> requests = new ArrayList<RequestStruct>(); //List of New Requests
	
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
  	
  	public void addMessage(String gid, String message,long time){
  		MessageStruct ms = new MessageStruct();
  		ms.sender = gid;
  		ms.time = time;
  		ms.msg = message;
  		messages.add(ms);
  	}
	
  	public String getNextRequest(){ //Get the next message and remove from list
  		RequestStruct rs = null;
  		if (requests.size() > 0) {
  			rs = requests.get(0); //Get Next Element
  			requests.remove(0); //Delete the Element from the list
  		}
  		Gson gson = new Gson();
  	    return gson.toJson(rs);
  	}
  	
  	public void addRequest(String gid, String request){
  		RequestStruct rs = new RequestStruct();
  		rs.sender = gid;
  		rs.type = "REQ";
  		rs.expiry = 0;
  		rs.msg = request;
  		requests.add(rs);
  	}
  	
  	public void addResponse(String gid, String response, boolean accept){
  		RequestStruct rs = new RequestStruct();
  		rs.sender = gid;
  		if (accept) {rs.type = "ACCEPT";} else {rs.type = "DENY";}
  		rs.expiry = 0;
  		rs.msg = response;
  		requests.add(rs);
  	}
  	
  	public void addAck(String gid, String message){
  		RequestStruct rs = new RequestStruct();
  		rs.sender = gid;
  		rs.type = "ACK";
  		rs.expiry = 0;
  		rs.msg = message;
  		requests.add(rs);
  	}
  	
  	public void setIP(InetAddress ipAddr){
  		ip = PeerUtils.getIPstr(ipAddr);
  	}
  	
  	public String getIP(){
  		return ip;
  	}
  	
  	public void updateIP(){
  		try {
			ip = PeerUtils.getIPstr(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			System.out.println("Failure in updateIP in DHTdata.");
			e.printStackTrace();
		}
  	}
  	
  	public long getTimeStamp(){
  		return ts;
  	}
  	
  	public void updateTimeStamp(){
  		Date date = new Date();
  		ts = date.getTime(); 
  	}
}
