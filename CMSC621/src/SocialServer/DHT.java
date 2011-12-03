package SocialServer;

import java.util.HashMap;
import java.util.Map;

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
	
	public void print(){
		System.out.println("--------------------Hashtable Entries--------------------");
		for (Map.Entry<String, String> entry : dht.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key + " : " + value);
		}
		System.out.println("---------------------------------------------------------");
		
	}
	
}
