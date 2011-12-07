package SocialServer;

import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.PropertiesLoader;
import de.uniba.wiai.lspi.chord.service.Report;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;
import dht.Node;

public class DHT {
	private Chord chord;
	private Node Nd;
	Report report;
	//public static final String PROPERTY_WHERE_TO_FIND_PROPERTY_FILE="chord.properties";
	
	public DHT(){
		
		PropertiesLoader.loadPropertyFile();
		chord = new ChordImpl(); 
		Nd = new Node(); //create the DHT node
		chord = Nd.init(); //sign in the DHT node to chord
		report = (Report) chord;
	}
	
	public void insert(String key, String value){
		Nd.insertData(chord, key, value);
	}
	
	public String retrieve(String key){
		return Nd.getData(chord, key);
	}
	
	public boolean isPresent(String key){
		return (Nd.getData(chord, key) != null);
	}
	
	public void delete(String key){
		Nd.removeData(chord, key);
	}
	
	public void update(String key, String value){
		Nd.updateData(chord, key, value);
	}
	
	public void leave(){
		try {
			chord.leave();
		} catch (ServiceException e) {
			System.out.println("ERROR Trying to leave Chord Network");
			e.printStackTrace();
		}
	}
	
	public void print(){
		System.out.println(report.printEntries());
	}
	
	public void printsp(){
		System.out.println(report.printPredecessor());
		System.out.println(report.printSuccessorList());
	}	
}

