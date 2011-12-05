/**
 * 
 */
package dht;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
//import java.nio.channels.IllegalBlockingModeException;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.Chord;
import de.uniba.wiai.lspi.chord.service.ServiceException;
import de.uniba.wiai.lspi.chord.service.impl.ChordImpl;
//import de.uniba.wiai.lspi.chord.service.Chord;

public class Node {

	//DHT Master Node
	private String bootstrapIP;
	//Public IP of this Node
	private String publicIP;
	
	public Node() {
		bootstrapIP = "";
		publicIP = "";
	}
	
	public Chord init() {
		
		Properties props = new Properties();
		try {
			String configPath = "config.properties";
			InputStream is = new FileInputStream(configPath); 
			props.load(is);
		} catch (IOException e1) {
			System.out.println(" ERROR: Problem in reading network config file");
			e1.printStackTrace();
			System.exit(0);
		} catch (IllegalArgumentException e2) {
			System.out.println("Illegal arguments in network config file");
			e2.printStackTrace();
		} 
		
		
		//get the static bootstrap IP
		this.bootstrapIP = props.getProperty("DHT_bootstrap");		
		//connect to the DHT
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
		URL localURL = null;
		URL bootstrap = null;
		boolean joined = false;
		Chord chord = null;
		try {
			chord = new ChordImpl();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			System.out.println(e2.getMessage());
			e2.printStackTrace();
		}
		System.out.println("BootStrap IP = " + bootstrapIP);
		while (true) {
			try {
				this.publicIP = getMyIP();
				//if current node is bootstrap, create new chord
				if (publicIP.equals(bootstrapIP)) {
					localURL = new URL(protocol + "://" + this.publicIP + ":" + DHTConstants.DHT_PORT + "/");
					try {
						System.out.println(localURL.toString());
						chord.create(localURL);
						break;
					} catch (ServiceException e) {
						System.out.println("No response from bootstrap node.");
						System.out.println(e.getMessage());
					}
				//if current node is not bootstrap, join the chord with bootstrapIP
				} else {
					localURL = new URL(protocol + "://" + this.publicIP + ":" + DHTConstants.DHT_PORT + "/");
					bootstrap = new URL(protocol + "://" + this.bootstrapIP + ":" + DHTConstants.DHT_PORT + "/");
					joined = false;
					try {
						chord.join(localURL, bootstrap);
						System.out.println("JOIN:Joined successfully to the chord " + chord);
						joined = true;
						//start the thread for handling new join requests in the already joined node (apart from the bootstrap node)
						new HandleJoinRequest(InetAddress.getByName(bootstrapIP), InetAddress.getLocalHost()).start();
						break;
					} catch (ServiceException e) { //bootstrap is down.
						System.out.println("Failed to create the chord, bootstrap = " + bootstrap);
						System.out.println(e.getMessage());
					}
					/*
					if (!joined) {
						DatagramSocket socket = new DatagramSocket(5001);						
						try {
							//broadcasts a new join request to all online peers (apart from the bootstrap which is down)							
							byte[] buf = new byte[1000];
							String message = "JOIN:" + bootstrapIP; //request the bootstrapIP to join
							buf = message.getBytes();
							DatagramPacket packet = new DatagramPacket(buf,
									buf.length, InetAddress
											.getByName("255.255.255.255"), 5001);
							System.out.println("Broadcasting join message.");
							socket.send(packet);
							byte[] newbuf = new byte[1000];
							packet = new DatagramPacket(newbuf, newbuf.length);
							socket.receive(packet); //waiting to receive ACK of joining from the peer node
							message = new String(newbuf);
							StringTokenizer st = new StringTokenizer(message,
									":");
							String joinedBootStrapIP = "";
							while (st.hasMoreTokens()) {
								joinedBootStrapIP = st.nextToken();
							}
							if (joinedBootStrapIP.equals(bootstrapIP)) {
								System.out
										.println("Joined successfully to the chord "
												+ chord);
								//start the thread for handling new join requests in the already joined node (apart from the bootstrap node)
								new HandleJoinRequest(InetAddress
										.getByName(bootstrapIP), InetAddress
										.getLocalHost()).start();
								break;
							}
						} catch (Exception e) {
							System.out
									.println("Service Failure: Joining the node to chord");
							System.out.println(e.getMessage());
						} finally {
							if (socket != null) {
								socket.close();
							}
						}
					} //if (!joined)
					*/
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				throw new RuntimeException("Invalid URL for DHT bootstrap", e1);
			} catch (ServiceException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not create/join DHT", e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error creating/joining DHT", e);
			}
		}
		return chord;
	}
	
	public void insertData(Chord chord, String key, String val) {
		StringKey sk = new StringKey(key); //create the MD5 hash on the key supplied
		try {
			chord.insert(sk, val);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeData(Chord chord, String key) {
		StringKey sk = new StringKey(key);
		try {
			Set<Serializable> val = chord.retrieve(sk);
			for (Serializable s : val) {
				chord.remove(sk, s);
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//public void updateData(Chord chord, String key, Serializable Obj) {
	public void updateData(Chord chord, String key, String value) {
		StringKey sk = new StringKey(key);
		try {
			Set<Serializable> val = chord.retrieve(sk); 
			for (Serializable s: val) {
				chord.remove(sk, s);
			}
			chord.insert(sk, value);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getData(Chord chord, String key) {
		StringKey sk = new StringKey(key);
		String val = "";
		try {
			Set<Serializable> Sval = chord.retrieve(sk);
			val = "";
			for (Serializable s: Sval) {
				val += s.toString();
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
	
	private String getMyIP() throws Exception {
		try {
			InetAddress i = InetAddress.getLocalHost();
			return i.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Please connect to network. Could not get host address");
		}
	}
}
