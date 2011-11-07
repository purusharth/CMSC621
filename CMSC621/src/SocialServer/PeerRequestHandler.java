package SocialServer;

public class PeerRequestHandler{
	
	private static String dummyProfile = 
			"{"
				+"'gid':'123456AB',"
				+"'name':'someone',"
				+"'age':25,"
				+"'hobbies':['reading','baseball','football'],"
				+"'sex':'male'"
			+"}";
	
	public static String getResponse(String inputStr){
		String outputStr="";
		if (inputStr.equalsIgnoreCase("getData")){
			outputStr = dummyProfile;
		}
		return(outputStr);
	}

}
