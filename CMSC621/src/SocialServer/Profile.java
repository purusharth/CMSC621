package SocialServer;

import java.util.ArrayList;
import com.google.gson.Gson;

public class Profile {
	   // Only one instance of Profile Class is maintained. All threads access/modify this instance.
	  	public Profile() {}

	  	private PublicProfile publicprofile;
	  	private PrivateProfile privateprofile;
	  
	  	synchronized public PublicProfile getPub(){ return publicprofile;}
	  	synchronized public PrivateProfile getPriv(){ return privateprofile;}
	  	
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
	  
	  	synchronized public String getGID() { return publicprofile.getGID();}
		synchronized public String getName() { return publicprofile.getName();}
		synchronized public Integer getAge() { return publicprofile.getAge();}
		synchronized public String[] getHobbies() { return publicprofile.getHobbies();}
		synchronized public String getSex() {return publicprofile.getSex();}
		
		synchronized public String getSchool() { return this.privateprofile.school;}
		synchronized public ArrayList<String> getFriends() { return this.privateprofile.friends;}
		synchronized public ArrayList<String> getMessages() { return this.privateprofile.messages;}
		
		synchronized public void setGID(String gid) { publicprofile.setGID(gid);}
		synchronized public void setName(String name) { publicprofile.setName(name);}
		synchronized public void setAge(Integer age) { publicprofile.setAge(age);}
		synchronized public void setHobbies(String[] hobbies) { publicprofile.setHobbies(hobbies);}
		synchronized public void setSex(String sex) { publicprofile.setSex(sex);}
		
		synchronized public void setSchool(String school) { this.privateprofile.school = school;}
		synchronized public void setFriends(ArrayList<String> friends) {this.privateprofile.friends = friends;}
		synchronized public void setMessages(ArrayList<String>  messages) {this.privateprofile.messages = messages;}
		
		synchronized public void addFriend(String friend){ privateprofile.friends.add(friend);}
		synchronized public boolean removeFriend(String friend){ return privateprofile.friends.remove(friend);}
		public boolean isFriend(String friend){ return privateprofile.friends.contains(friend);}
		
		synchronized public void insertMessage(String message){ privateprofile.messages.add(message);}
		synchronized public void deleteMessage(String message){ privateprofile.messages.remove(message);}
	  
		public String toString() {
	        return String.format("PUBLIC-PROFILE:%s\nPRIVATE-PROFILE:%s", publicprofile,privateprofile);
	    }
}
