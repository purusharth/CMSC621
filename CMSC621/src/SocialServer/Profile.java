package SocialServer;

public class Profile {
	  public Profile() {}

	  private PublicProfile publicprofile;
	  private PrivateProfile privateprofile;
	  
	  public PublicProfile getPub(){ return publicprofile;}
	  public PrivateProfile getPriv(){ return privateprofile;}
	  
	  public String toString() {
	        return String.format("PUBLIC-PROFILE:%s\nPRIVATE-PROFILE:%s", publicprofile,privateprofile);
	  }
}
