package SocialServer;

import java.util.ArrayList;
import com.google.gson.Gson;

public class Profile {
	

	   // Only one instance of Profile Class is maintained. All threads access/modify this instance.
		public class PublicProfile {
			public String gid="";
			public String name="";
			public Integer age=0;
			public ArrayList<String> hobbies = new ArrayList<String>();
			public String sex="";
		}
		
		public class PrivateProfile {
			public String gid="";
			public String school="";
			public ArrayList<String> friends= new ArrayList<String>();
			public ArrayList<String> messages= new ArrayList<String>();
		}
		
	  	private PublicProfile publicprofile;
	  	private PrivateProfile privateprofile;
		
	  	public Profile() {
	  		publicprofile = new PublicProfile();
	  		privateprofile = new PrivateProfile();
	  	}


	  
	  	synchronized public PublicProfile getPub(){ return this.publicprofile;}
	  	synchronized public PrivateProfile getPriv(){ return this.privateprofile;}
	  	
	  	synchronized public String getPubStr(){ //return Public Profile as JSON String
		  Gson gson = new Gson();
		  return gson.toJson(publicprofile);
	  	}
	  	
	  	synchronized public String getPrivStr(){ //Return Private Profile as JSON String
		  Gson gson = new Gson();
		  return gson.toJson(privateprofile);
	  	}
	  	//All the get/set/add/remove methods are synchronized. Implements partial ordering? or happens-after.
	  	//so that any read/write operation "happens after" the previous one is finished. 
	  	//Prevents from multiple threads modifying object data at the same time and interleaving.
	  
	  	synchronized public String getGID() { return this.publicprofile.gid;}
		synchronized public String getName() { return this.publicprofile.name;}
		synchronized public Integer getAge() { return this.publicprofile.age;}
		synchronized public ArrayList<String> getHobbies() { return publicprofile.hobbies;}
		synchronized public String getSex() {return publicprofile.sex;}
		
		synchronized public String getSchool() { return this.privateprofile.school;}
		synchronized public ArrayList<String> getFriends() { return this.privateprofile.friends;}
		synchronized public ArrayList<String> getMessages() { return this.privateprofile.messages;}
		
		synchronized public void setGID(String gid) { this.publicprofile.gid=gid; this.privateprofile.gid = gid;}
		synchronized public void setName(String name) { this.publicprofile.name=name;}
		synchronized public void setAge(Integer age) { this.publicprofile.age = age;}
		synchronized public void setHobbies(ArrayList<String> hobbies) { this.publicprofile.hobbies = hobbies;}
		synchronized public void setSex(String sex) { this.publicprofile.sex = sex;}
		
		synchronized public void addHobby(String hobby){ publicprofile.hobbies.add(hobby);}
		synchronized public void removeHobby(String hobby){ publicprofile.hobbies.remove(hobby);}
		
		synchronized public void setSchool(String school) { this.privateprofile.school = school;}
		synchronized public void setFriends(ArrayList<String> friends) {this.privateprofile.friends = friends;}
		synchronized public void setMessages(ArrayList<String>  messages) {this.privateprofile.messages = messages;}
		
		synchronized public void addFriend(String friend){ privateprofile.friends.add(friend);}
		synchronized public boolean removeFriend(String friend){ return privateprofile.friends.remove(friend);}
		public boolean isFriend(String friend){ return privateprofile.friends.contains(friend);}
		
		synchronized public void insertMessage(String message){ privateprofile.messages.add(message);}
		synchronized public void deleteMessage(String message){ privateprofile.messages.remove(message);}
	  
		public String toString(Profile prof) {
	        //return String.format("PUBLIC-PROFILE:%s\nPRIVATE-PROFILE:%s", publicprofile,privateprofile);
			Gson gson = new Gson();
			//return (gson.toJson(publicprofile) + gson.toJson(privateprofile)) ;
			return gson.toJson(prof);
	    }
}
