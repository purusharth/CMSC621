package SocialServer;

import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

public class Social {
	private Profile profile;
	private String datafile;
	private DHTdata dd; //Data Structure for data stored in DHT
	private DHT dht; //Reference to hashtable
	//private Gson gson = new Gson();
	
	public Social(Profile profile, String datafile, DHT hash) {
		this.profile = profile;
		this.datafile = datafile;
		this.dht = hash;
	}
	
	public void createDefaultPofile(){
		//populate Profile object
		profile.setGID("puru");
		profile.setAge(31);
		profile.setName("Purusharth Prakash");
		profile.setSex("Male");
		profile.addHobby("swimming");
		profile.addHobby("reading");
		profile.addHobby("fishing");
		profile.setSchool("UMBC");
		profile.addFriend("somnath");
		profile.addFriend("jason");
		profile.insertMessage("Welcome");
	}
	
	public void createDefaultPofile2(){
		//populate Profile object
		profile.setGID("bryan");
		profile.setAge(25);
		profile.setName("Bryan Porter");
		profile.setSex("Male");
		profile.addHobby("cycling");
		profile.addHobby("diving");
		profile.setSchool("UMBC");
		profile.addFriend("josh");
		profile.addFriend("jason");
	}
	
	public void displayProfile(){
		Gson gson = new Gson();
		System.out.println(gson.toJson(profile));
	}
	
	//Save profile to file
	public void saveProfile(){
		Gson gson = new Gson();
		PeerUtils.writeStringtoFile(datafile, gson.toJson(profile));
	}
	
	//Load Profile from file
	public void LoadProfile(){
		 String jsonTxt = PeerUtils.readFiletoString(datafile);  
	     profile = new Gson().fromJson(jsonTxt, Profile.class);
	}
	
	//Convert profile object to JSON String
	public String profileStr(){
		Gson gson = new Gson();
		return gson.toJson(profile);
	}
	
	//Get Public Profile as JSON String
	public String getPubProfile(){
		return profile.getPubStr();
	}
	
	//Get Private Profile as JSON String
	public String getPrivProfile(){
		return profile.getPrivStr();
	}
	
	//public void RemoveProfile(){}
	
	public void DisplayFriendList(){
		ArrayList<String> fl = profile.getFriends(); 
		System.out.println("Friend List:");
	    int n = fl.size();
	    for(int i = 0; i < n ; i++) System.out.println( "  " + fl.get( i ) );
	}
	
	public void RemoveFriend(String friend){
		if (profile.isFriend(friend)) {
			profile.removeFriend(friend);
		}else{
			System.out.println("Unable to Remove Friend: "+friend+" is not a friend");
		}
	}
	
	public void DisplayMessages(){
		ArrayList<String> ml = profile.getMessages(); 
		System.out.println("Message List:");
	    int n = ml.size();
	    for(int i = 0; i < n ; i++) System.out.println( "  " + ml.get( i ) );
	}
	
	//Create dd data object, for DHT data structure.
	//Data object dd values cannot be modified directly, values are loaded from profile
	//create data string to be stored in DHT
	public String makeDHTdataFromProfile(){ 
	    dd = new DHTdata();
	    dd.setGID(profile.getGID());
	    PublicProfile pp = new PublicProfile();
	    pp.setName(profile.getName());
	    pp.setGID(profile.getGID());
	    pp.setSex(profile.getSex());
	    pp.setAge(profile.getAge());
	    pp.setHobbies(profile.getHobbies());
	    dd.setPubProfile(pp);
	    dd.updateIP();
	    dd.updateTimeStamp();
	    //dd.addMessage("josh", "Hello");
	    //dd.addMessage("jeremy", "Where you at?");
	    //dd.addRequest("jason", "Lets Become Friends");
	    Gson gson = new Gson();
	    return gson.toJson(dd);
	}
	
	//Parse data string retrieved from DHT into dd object
	public void parseDHTString(String data){
		Gson gson = new Gson();
		dd = gson.fromJson(data, DHTdata.class);
	}
	
	//Convert data object to JSON String Format for DHT
	public String createDHTString(){
		Gson gson = new Gson();
		return gson.toJson(dd);
	}
	
	//Get Next Message from Data Object
	public String getNewMessage(){ return dd.getNextMessage(); } //No embedded retrieve or update to prevent repeated queries to DHT
	
	//Get Next Request from Data Object
	public String getNewRequest(){ return dd.getNextRequest(); }
	

	//Store Current Profile into DHT (with empty Message and Request Lists)
	//Assumes no profile exists in DHT. Otherwise use dhtUpdate
	public void dhtInsertNew(){ 
		dht.insert(profile.getGID(), makeDHTdataFromProfile()) ;
	}
	
	//Retrieve DHT entry and and parse into dd data object
	public void dhtRetrieve(){ 
		String dhtdata = dht.retrieve(profile.getGID());
		parseDHTString(dhtdata);
	}
	
	//Update DHT entry using current dd data object
	public void dhtUpdate(){
		dht.update(dd.getGID(), createDHTString());
	}
	
	//Update DHT data with current profile
	//IP and Time Stamp are also updated
	public void dhtUpdateProfile(){ 
		dht.update(profile.getGID(), makeDHTdataFromProfile());
	}
	
	public void dhtDisplay(){
		dht.print();
	}
	
	public void SendMessage(String receiverID, String message){
		Gson gson = new Gson();
		//get receiver data from DHT
		String dhtdata = dht.retrieve(receiverID);
		//parse data and store into dd
		//parseDHTString(dhtdata);
		DHTdata dr = gson.fromJson(dhtdata, DHTdata.class);
		//add new message
		dr.addMessage(profile.getGID(), message);
		//update receiver data in DHT
		dht.update(receiverID, gson.toJson(dr));
	}
	
	public void SendFriendRequest(String receiverID, String request){
		Gson gson = new Gson();
		String dhtdata = dht.retrieve(receiverID);
		DHTdata dr = gson.fromJson(dhtdata, DHTdata.class);
		dr.addRequest(profile.getGID(), request);
		dht.update(receiverID, gson.toJson(dr));
	} 
	
	public void SendFriendResponse (String receiverID, String response, boolean accept){
		Gson gson = new Gson();
		String dhtdata = dht.retrieve(receiverID);
		DHTdata dr = gson.fromJson(dhtdata, DHTdata.class);
		dr.addResponse(profile.getGID(), response, accept);
		dht.update(receiverID, gson.toJson(dr));
	} 
	
	public void SendResponseACK(String receiverID, String ackresponse){
		Gson gson = new Gson();
		String dhtdata = dht.retrieve(receiverID);
		DHTdata dr = gson.fromJson(dhtdata, DHTdata.class);
		dr.addAck(profile.getGID(), ackresponse);
		dht.update(receiverID, gson.toJson(dr));
	}
	
	public void ProcessRequests(){
		Gson gson = new Gson();
		DHTdata.MessageStruct ms;
	    String msg = getNewRequest();
	    //System.out.println(msg);
	    String kb;
	    Scanner input=new Scanner(System.in); 
	    while (!(msg.equals(""))){
	    	ms = gson.fromJson(msg, DHTdata.MessageStruct.class);
	    	//System.out.println(msg);
	    	if (ms.type.equals("REQ")){
	    		System.out.println("Incoming Friend Request from "+ms.sender);
	    		System.out.println("Message from "+ms.sender+" : "+ms.msg);
	    		System.out.print("Do you wish to accept [yes|no]: ");
	    		kb = input.next();
	    		if (kb.equals("yes")){
	    			SendFriendResponse(ms.sender, "Sure", true);
	    		}
	    		else{
	    			SendFriendResponse(ms.sender, "Sorry", false);
	    		}
	    	}
	    	else if(ms.type.equals("ACCEPT")){
	    		System.out.println("Friend Request Accepted by "+ms.sender);
	    		System.out.println("Adding \""+ms.sender+"\" as friend.");
	    		profile.addFriend(ms.sender);
	    		SendResponseACK(ms.sender, "Received ACCEPT.");
	    	}
	    	else if(ms.type.equals("DENY")){
	    		System.out.println("Friend Request Denied by "+ms.sender);
	    	}
	    	else if(ms.type.equals("ACK")){
	    		System.out.println("Friend Request Acceptance Acknowledged by "+ms.sender);
	    		System.out.println("Message : "+ms.msg);
	    		System.out.println("Adding \""+ms.sender+"\" as friend.");
	    		profile.addFriend(ms.sender);
	    	}
	    	else{
	    		System.out.println("ERROR: Unknown TYPE for request/response.");
	    	}
	    	msg = getNewRequest();
	    }
	}
	
	
	
	
	
	//public void dhtUpdatePubKey(){}
	//public void dhtUpdateUserID(){}
	//public void dhtGetMessages(){} //Get New Messages from DHT 
	//public void dhtGetRequests(){}
	//public void UpdatePrivProfile(){}
		
	public void joinNetwork(){}
	public void leaveNetwork(){}

}
