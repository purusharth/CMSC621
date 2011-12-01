package SocialServer;


import com.google.gson.Gson;

public class PeerRequestHandler{
	
	private Profile myprof;
	
	
	public PeerRequestHandler(Profile profile){
		this.myprof = profile;
	};
	
	public String getResponse(String inputStr){
		String outputStr="";
		PeerRequest reqJson = new Gson().fromJson(inputStr, PeerRequest.class);
        String request = reqJson.getRequest();
        String gid = reqJson.getGID();
        
		if (request.equalsIgnoreCase("getPublicProfile")){
			outputStr = this.myprof.getPubStr(); //getPublicProfile
		} else if(request.equalsIgnoreCase("getPrivateProfile")){
			if(myprof.isFriend(gid)){
				
			}
		} else {
			outputStr = "Unknown Request";
		}
		return(outputStr);
	}
	
	public String makeRequest(String gid, String request){
		String outputStr="";
		PeerRequest reqJson = new PeerRequest();
		Gson gson = new Gson();
		reqJson.setGID(gid);
		reqJson.setRequest(request);		
		outputStr = gson.toJson(reqJson);

		return(outputStr);
	}
}
