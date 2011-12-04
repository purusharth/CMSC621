package dht;

import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.PropertiesLoader;

public class Test {

	/**
	 * @param args
	 */
	public static final String PROPERTY_WHERE_TO_FIND_PROPERTY_FILE="src/dht/chord.properties";
	public static void main(String[] args) {
		PropertiesLoader.loadPropertyFile();
		Chord CHRD = new ChordImpl(); 
		Node BSNode = new Node();
		CHRD = BSNode.init();
		System.out.println("The chord is " + CHRD);
		//Node Node1 = new Node();
		//Chord CHRD = Node1.init();
		//System.out.println("Inserting data into DHT ...");
		//Node1.insertData(CHRD, key, val);
	}

}
