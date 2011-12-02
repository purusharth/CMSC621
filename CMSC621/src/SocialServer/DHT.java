package SocialServer;

import java.util.HashMap;

public class DHT {
	private HashMap<String,String> dht;
	
	public DHT(){
		dht = new HashMap<String,String>();
	}
	
	public void insert(String key, String value){
		dht.put(key, value);
	}
	
	public String retrieve(String key){
		return dht.get(key);
	}
	
	public boolean isPresent(String key){
		return dht.containsKey(key);
	}
	
	public void delete(String key){
		dht.remove(key);
	}
	
	public void update(String key, String value){
		dht.remove(key);
		dht.put(key, value);
	}
	
}
