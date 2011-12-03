package SocialServer;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Social {
	private Profile profile;
	private String datafile;
	private DHTdata dd;
	private DHT dht;
	
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
	public String makeDHTdata(){ 
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
	public void parseDHTdata(String data){
		Gson gson = new Gson();
		dd = gson.fromJson(data, DHTdata.class);
	}
	
	public String getNewMessage(){ return dd.getNextMessage(); }
	
	public String getNewRequest(){ return dd.getNextRequest(); }
	
	public void UpdateDHTPubProfile(){ dd.setPubProfile(profile.getPub());}

	//Store Current Profile into DHT (with empty Message and Request Lists)
	public void dhtInsert(){ 
		dht.insert(profile.getGID(), makeDHTdata()) ;
	}
	
	public String dhtRetrieve(){ 
		return dht.retrieve(profile.getGID());
	}
	
	public void dhtUpdateProfile(){ 
		dht.update(profile.getGID(), makeDHTdata());
	}
	
	public void dhtDisplay(){
		dht.print();
	}
	
	public void SendMessage(String receiverID, String message){
		//get receiver data from DHT
		String dhtdata = dht.retrieve(receiverID);
		//parse data and store into dd
		parseDHTdata(dhtdata);
		//add new message
		dd.addMessage(profile.getGID(), message);
		//update receiver data in DHT
		Gson gson = new Gson();
		dht.update(receiverID, gson.toJson(dd));
	}
	
	public void SendFriendRequest(String receiverID, String request){
		String dhtdata = dht.retrieve(receiverID);
		parseDHTdata(dhtdata);
		dd.addRequest(profile.getGID(), request);
		Gson gson = new Gson();
		dht.update(receiverID, gson.toJson(dd));
	} 
	public void RespondToFriendRequest(){}
	public void AcknowledgeFriendRequestResponse(){}
	
	
	//public void dhtUpdatePubKey(){}
	//public void dhtUpdateUserID(){}
	//public void dhtGetMessages(){} //Get New Messages from DHT 
	//public void dhtGetRequests(){}
	//public void UpdatePrivProfile(){}
		
	public void joinNetwork(){}
	public void leaveNetwork(){}

}
