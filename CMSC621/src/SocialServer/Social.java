package SocialServer;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Social {
	private Profile profile;
	private String datafile;
	private DHTdata dd;
	
	public Social(Profile profile, String datafile) {
		this.profile = profile;
		this.datafile = datafile;
	}
	public void createPofile(){
		//Create Public/Private Key
		//populate Profile object
		profile.setGID("puru");
		profile.setAge(31);;
		profile.setName("Purusharth Prakash");
		profile.setSex("Male");
		profile.addHobby("swimming");
		profile.addHobby("reading");
		profile.addHobby("fishing");
		profile.setSchool("UMBC");
		profile.addFriend("somnath");
		profile.addFriend("jason");
		profile.insertMessage("Welcome");
				
		//Join CHORD, save ip,pubkey,pubprofile,
	}
	
	public void displayProfile(){
		Gson gson = new Gson();
		System.out.println(gson.toJson(profile));
	}
	
	public void saveProfile(){
		Gson gson = new Gson();
		PeerUtils.writeStringtoFile(datafile, gson.toJson(profile));
	}
	
	public void LoadProfile(){
		 String jsonTxt = PeerUtils.readFiletoString(datafile);  
	     profile = new Gson().fromJson(jsonTxt, Profile.class);
	}
	
	public String profileStr(){
		Gson gson = new Gson();
		return gson.toJson(profile);
	}
	
	public String getPubProfile(){
		return profile.getPubStr();
	}
	
	public String getPrivProfile(){
		return profile.getPrivStr();
	}
	
	public void RemoveProfile(){}
	
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
	
	//create data string to be stored in DHT
	public String makeDHTdata(){ 
	    dd = new DHTdata();
	    dd.setGID(profile.getGID());
	    PublicProfile pp = new PublicProfile();
	    pp.setName(profile.getName());
	    pp.setGID(profile.getGID());
	    pp.setSex(profile.getSex());
	    pp.setHobbies(profile.getHobbies());
	    dd.setPubProfile(pp);
	    //dd.addMessage("josh", "Hello");
	    //dd.addMessage("jeremy", "Where you at?");
	    //dd.addRequest("jason", "Lets Become Friends");
	    Gson gson = new Gson();
	    return gson.toJson(dd);
	}
	
	//Parse data string retrieved from DHT
	public void parseDHTdata(String data){
		Gson gson = new Gson();
		dd = gson.fromJson(data, DHTdata.class);
	}
	
	public String getNewMessage(){ return dd.getNextMessage(); }
	
	public String getNewRequest(){ return dd.getNextRequest(); }
	
	public void UpdateDHTPubProfile(){ dd.setPubProfile(profile.getPub());}
	
	public void joinNetwork(){}
	public void leaveNetwork(){}
	
	public void dhtInsert(String data){}
	//public String dhtGet(){}
	public void dhtUpdateProfile(){}
	public void dhtUpdatePubKey(){}
	public void dhtUpdateUserID(){}
	public void dhtGetMessages(){} //Get New Messages from DHT 
	public void dhtGetRequests(){}
	
	public void SendFriendRequest(){} 
	public void RespondToFriendRequest(){}
	public void AcknowledgeFriendRequestResponse(){}
	
	public void UpdatePrivProfile(){}
	public void getMessages(){} //get messages stored, locally
	public void SendMessage(){}

}
