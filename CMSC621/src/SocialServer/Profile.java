package SocialServer;

import java.util.ArrayList;

public class Profile {
	  public Profile() {}

	  private PublicProfile publicprofile;
	  private PrivateProfile privateprofile;
	  
	  public PublicProfile getPub(){ return publicprofile;}
	  public PrivateProfile getPriv(){ return privateprofile;}
	  
		public String getGID() { return publicprofile.getGID();}
		public String getName() { return publicprofile.getName();}
		public Integer getAge() { return publicprofile.getAge();}
		public String[] getHobbies() { return publicprofile.getHobbies();}
		public String getSex() {return publicprofile.getSex();}
		
		public String getSchool() { return this.privateprofile.school;}
		public ArrayList<String> getFriends() { return this.privateprofile.friends;}
		public ArrayList<String> getMessages() { return this.privateprofile.messages;}
		
		public void setGID(String gid) { publicprofile.setGID(gid);}
		public void setName(String name) { publicprofile.setName(name);}
		public void setAge(Integer age) { publicprofile.setAge(age);}
		public void setHobbies(String[] hobbies) { publicprofile.setHobbies(hobbies);}
		public void setSex(String sex) { publicprofile.setSex(sex);}
		
		public void setSchool(String school) { this.privateprofile.school = school;}
		public void setFriends(ArrayList<String> friends) {this.privateprofile.friends = friends;}
		public void setMessages(ArrayList<String>  messages) {this.privateprofile.messages = messages;}
		
		public void addFriend(String friend){ privateprofile.friends.add(friend);}
		public boolean removeFriend(String friend){ return privateprofile.friends.remove(friend);}
		public boolean isFriend(String friend){ return privateprofile.friends.contains(friend);}
		
		public void insertMessage(String message){ privateprofile.messages.add(message);}
		public void deleteMessage(String message){ privateprofile.messages.remove(message);}
	  
		public String toString() {
	        return String.format("PUBLIC-PROFILE:%s\nPRIVATE-PROFILE:%s", publicprofile,privateprofile);
	    }
}
