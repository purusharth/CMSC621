package dht;

import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.Report;
import de.uniba.wiai.lspi.chord.service.PropertiesLoader;
//import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;
//import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;

public class InitDHT {

	/**
	 * initialize DHT Layer
	 */
	public static void main(String[] args) {
		PropertiesLoader.loadPropertyFile();
		Chord chord = new ChordImpl(); 
		Node Nd = new Node(); //create the DHT node
		chord = Nd.init(); //sign in the DHT node to chord
		System.out.println("Chord ID: " + chord.getID());
		System.out.println("Chord URL: " + chord.getURL());
		
		
		Report report = (Report) chord;
		System.out.println(report.printEntries());
		System.out.println(report.printFingerTable());
		System.out.println(report.printPredecessor());
		System.out.println(report.printSuccessorList());
		System.out.println(report.printReferences());
		
		String val = "{"
			+"'gid':'123456AB',"
			+"'name':'someone',"
			+"'age':25,"
			+"'hobbies':['reading','baseball','football'],"
			+"'sex':'male'"
		+"}";
         
		//Nd.insertData(CHRD, "someone", val);
		//System.out.println(Nd.getData(CHRD, "someone"));
		System.exit(0);
	}

}
