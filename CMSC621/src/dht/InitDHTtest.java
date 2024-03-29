package dht;

import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.Report;
import de.uniba.wiai.lspi.chord.service.PropertiesLoader;
//import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;
//import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;

public class InitDHTtest {

	/**
	 * initialize DHT Layer
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
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
	
        
		//Nd.insertData(chord, "someone", val);
		//System.out.println(Nd.getData(chord, "someone"));
		System.exit(0);
	}

}
