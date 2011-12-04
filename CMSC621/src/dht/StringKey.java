package dht;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.uniba.wiai.lspi.chord.service.Key;

public class StringKey implements Key {
	private String theString;
	public StringKey(String str) {
		this.theString = HashCode(str);
	}
	public byte[] getBytes() {
		return this.theString.getBytes();
	}
	public String HashCode(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(str.getBytes(),0,str.length());
		return new String(md.digest());
		
	}
	public boolean equals(Object o) {
		if (o instanceof StringKey) {
			((StringKey) o).theString.equals(this.theString);
		}
		return false;
	}
}
