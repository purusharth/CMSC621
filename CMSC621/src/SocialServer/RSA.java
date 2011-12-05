package SocialServer;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import org.apache.commons.codec.*;
import org.apache.commons.codec.binary.Hex;

public class RSA {
	private KeyPair keypair;
	private PublicKey pubkey;
	private PrivateKey privkey;
	
	RSA() throws Exception{
		
		//Generate new key pair
        KeyPairGenerator kpg = null;
		kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        
        keypair = kpg.generateKeyPair();
        pubkey = keypair.getPublic();
        privkey = keypair.getPrivate();      
        
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(pubkey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(privkey, RSAPrivateKeySpec.class);
        
        //System.out.println(pub.getModulus()+" "+pub.getPublicExponent());
        //System.out.println(priv.getModulus()+" "+priv.getPrivateExponent());
	}
	
	RSA(String PrivateKeyFile, String PublicKeyFile) throws Exception{
		pubkey = readPubKey(PublicKeyFile);
		privkey = readPrivKey(PrivateKeyFile);
		
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(pubkey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(privkey, RSAPrivateKeySpec.class);
        
        System.out.println(pub.getModulus().toString(16)+" "+pub.getPublicExponent().toString(16));
        System.out.println(priv.getPrivateExponent().toString(16));
	}
	
	/*
	 *  http://codeartisan.blogspot.com/2009/05/public-key-cryptography-in-java.html
		Creating the keypair. 

		# generate a 512-bit RSA private key
		$ openssl genrsa -out private_key.pem 512

		# convert private Key to PKCS#8 format (so Java can read it)
		$ openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

		# output public key portion in DER format (so Java can read it)
		$ openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der

    */
	
	public static PublicKey readPubKey(String filename) throws Exception {
			    
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int)f.length()];
		dis.readFully(keyBytes);
		dis.close();

		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
	
	  public static PrivateKey readPrivKey(String filename) throws Exception {
			    
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int)f.length()];
		dis.readFully(keyBytes);
		dis.close();

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		 return kf.generatePrivate(spec);
		 
		}
	
    
    public byte[] encrypt(byte[] plainText) throws Exception
    {
        byte[] encryptedText = null;

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubkey);
        encryptedText = cipher.doFinal(plainText);

        return encryptedText;
    }
    
    public byte[] decrypt(byte[] text) throws Exception
    {
        byte[] decryptedText = null;

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privkey);
        decryptedText = cipher.doFinal(text);
        
        return decryptedText;
    }
    
    public String encrypt(String text) throws Exception
    {
        String encryptedTextHexString;

        byte[] cipherText = encrypt(text.getBytes());
        encryptedTextHexString = ByteArrayToHex(cipherText);
        
        return encryptedTextHexString;
    }
    
    public String decrypt(String enc) throws Exception
    {
    	byte[] encbytes = HexToByteArray(enc);
    	byte[] decbytes = decrypt(encbytes);
    	String decryptedText = new String(decbytes);
    	//System.out.printf("%c%c%c%c\n",decbytes[0],decbytes[1],decbytes[2],decbytes[3]);
    	return decryptedText;
    }
    
    private String ByteArrayToHex(byte[] bytearray)
    {
    	String hexString = new String(Hex.encodeHex(bytearray));
    	return hexString;
    }
    
    private static byte[] HexToByteArray(String hex) throws DecoderException
    {
    	byte[] bytearray = Hex.decodeHex(hex.toCharArray());
    	return bytearray;
    }
    


    
    public static void main(String args[]) throws Exception{
    	//RSA rsa = new RSA();
    	RSA rsa = new RSA("private_key.der","public_key.der");
    	String enc = rsa.encrypt("TESTING 123");
    	System.out.println(enc);
    	String dec = rsa.decrypt(enc);
    	System.out.println(dec);
    	
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(rsa.pubkey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(rsa.privkey, RSAPrivateKeySpec.class);
        
        

    	
    	
    }
}
