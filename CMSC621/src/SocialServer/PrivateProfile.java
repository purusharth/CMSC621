package SocialServer;

import java.util.ArrayList;

public class PrivateProfile {
	public String gid="";
	public String school="";
	public ArrayList<String> friends= new ArrayList<String>();
	public ArrayList<String> messages= new ArrayList<String>();
	
	/*
	public String getGID() { return gid;}
	public String getSchool() { return school;}
	public ArrayList<String> getFriends() { return friends;}
	public String[] getMessages() { return messages;}
	
	public void setGID(String gid) { this.gid = gid; }
	public void setSchool(String school) { this.school = school;}
	public void setFriends(ArrayList<String> friends) { this.friends = friends;}
	public void setMessages(String[] messages) { this.messages = messages;}
	
	public void addfriend(String friend){ friends.add(friend);}
	*/
	
	public String toString() {
        return String.format("gid=%s, school=%s, friends=[%s], messages=[%s]", gid, school, friends.toString() , messages.toString());
    }

}
