package SocialServer;

import java.util.Arrays;

public class PrivateProfile {
	private String gid=null;
	private String school=null;
	private String[] friends=null;
	private String[] messages=null;
	
	public String getGID() { return gid;}
	public String getSchool() { return school;}
	public String[] getFriends() { return friends;}
	public String[] getMessages() { return messages;}
	
	public void setGID(String gid) { this.gid = gid; }
	public void setSchool(String school) { this.school = school;}
	public void setFriends(String[] friends) { this.friends = friends;}
	public void setMessages(String[] messages) { this.messages = messages;}
	
	public String toString() {
        return String.format("gid=%s, school=%s, friends=[%s], messages=[%s]", gid, school, Arrays.toString(friends), Arrays.toString(messages));
    }

}
