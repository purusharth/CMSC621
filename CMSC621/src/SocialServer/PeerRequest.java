package SocialServer;

import java.util.Arrays;

public class PeerRequest {
	private String gid = null;
	private String request = null;
	
	public String getGID() { return gid;}
	public String getRequest() { return request; }
	
	public void setGID(String gid){ this.gid = gid; }
	public void setRequest(String request){ this.request = request; }
	
	public String toString() {
        return String.format("gid=%s, request=%s", gid, request);
    }
	
}
