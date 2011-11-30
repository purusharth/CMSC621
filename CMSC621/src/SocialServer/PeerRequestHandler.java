package SocialServer;


import com.google.gson.Gson;

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
		PeerRequest reqJson = new Gson().fromJson(inputStr, PeerRequest.class);

		if (reqJson.getRequest().equalsIgnoreCase("getData")){
			outputStr = dummyProfile;
		}
		return(outputStr);
	}
	
	public static String makeRequest(String gid, String request){
		String outputStr="";
		PeerRequest reqJson = new PeerRequest();
		Gson gson = new Gson();
		
		reqJson.setGID(gid);
		reqJson.setRequest(request);		
		outputStr = gson.toJson(reqJson);

		return(outputStr);
	}
}
