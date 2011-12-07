package SocialServer;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import org.apache.commons.codec.*;
import org.apache.commons.codec.binary.Hex;

public class RSA {
	private KeyPair keypair;
	private PublicKey pubkey;
	private PrivateKey privkey;
	private BigInteger n = new BigInteger("aae484cf460ce30ed5ae443d9634aa431c2a31d558db4724e0104ed63304289784e214b0b90d1a09e4b69cb44c5618c2368ce995531d9d3171469f416f928a95",16);
	private BigInteger d = new BigInteger("447f96907276a5e88353f7bc6be1ae417a1f9b9bf5a2e7306bb37db633fabdbdde2088b51d60de2cf32eb45145573bce7d897d9db150de262565fe53b38d95c1",16);
	private BigInteger e = new BigInteger("10001",16);//exponent
	
	
	RSA(){}
	
	RSA(int keybits) throws Exception{
		
		//Generate new key pair
        KeyPairGenerator kpg = null;
		kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(keybits);
        
        keypair = kpg.generateKeyPair();
        pubkey = keypair.getPublic();
        privkey = keypair.getPrivate();      
        
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(pubkey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(privkey, RSAPrivateKeySpec.class);
        
        n = pub.getModulus();
        e = pub.getPublicExponent();
        d = priv.getPrivateExponent();
        
        System.out.printf("PubKey=%x\n",n);
        System.out.printf("PriKey=%x\n",d);
        System.out.printf("Exp=%x\n",e);
	}
	
	RSA(String PrivateKeyFile, String PublicKeyFile) throws Exception{
		pubkey = readPubKey(PublicKeyFile);
		privkey = readPrivKey(PrivateKeyFile);
		
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = (RSAPublicKeySpec) fact.getKeySpec(pubkey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = (RSAPrivateKeySpec) fact.getKeySpec(privkey, RSAPrivateKeySpec.class);
        
        n = pub.getModulus();
        e = pub.getPublicExponent();
        d = priv.getPrivateExponent();
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
    


    public String bencrypt(String inputText) {
        String hexString = new String(Hex.encodeHex(inputText.getBytes()));
        BigInteger message = new BigInteger(hexString,16);
        BigInteger encrypted = message.modPow(e, n);
        String encryptedHexString = encrypted.toString(16);
        return encryptedHexString;
    }

    public String bdecrypt(String encryptedHexString) throws DecoderException {
    	BigInteger encrypted = new BigInteger(encryptedHexString,16);
        BigInteger decrypted = encrypted.modPow(d, n);
        String decryptedHexString = decrypted.toString(16);
        byte[] ouputBytes = Hex.decodeHex(decryptedHexString.toCharArray()); 
        String output = new String(ouputBytes);
        return output;
    }
    
    public String bdecrypt(String encryptedHexString,String modulus,String exponent) throws DecoderException {
    	BigInteger encrypted = new BigInteger(encryptedHexString,16);
    	BigInteger n = new BigInteger(modulus,16);
    	BigInteger d = new BigInteger(exponent,16);
        BigInteger decrypted = encrypted.modPow(d, n);
        String decryptedHexString = decrypted.toString(16);
        byte[] ouputBytes = Hex.decodeHex(decryptedHexString.toCharArray()); 
        String output = new String(ouputBytes);
        return output;
    }
    
    public String getn(){ return n.toString(16); }
    public String gete(){ return e.toString(16); }
    public String getd(){ return d.toString(16); }
    
    public String bencrypt(String inputText, String modulus,String exponent) throws DecoderException {
    	BigInteger n = new BigInteger(modulus,16);
    	BigInteger e = new BigInteger(exponent,16);
    	String inputHexString = new String(Hex.encodeHex(inputText.getBytes()));
    	BigInteger m = new BigInteger(inputHexString,16);
    	
        BigInteger out = m.modPow(e, n);
        
        String outHexString = out.toString(16);
        return outHexString;
    }


    public static void main(String args[]) throws Exception{
    	RSA rsa = new RSA();
    	//RSA rsa = new RSA("private_key.der","public_key.der");
    	//String enc = rsa.encrypt("TESTING 123");
    	//System.out.println(enc);
    	//String dec = rsa.decrypt(enc);
    	//System.out.println(dec);
    	
        System.out.printf("PubKey=%x\n",rsa.n);
        System.out.printf("PriKey=%x\n",rsa.d);
        System.out.printf("Exp=%x\n",rsa.e);
        
        String encrypted = rsa.bencrypt("Hello");
        System.out.println(encrypted);
        System.out.println(rsa.bdecrypt(encrypted));
        
        
        //Encrypt using public key, Decrypt using private key
        encrypted = rsa.bencrypt("Hello",rsa.getn(),rsa.gete());
        System.out.println(encrypted);
        System.out.println(rsa.bdecrypt(encrypted,rsa.getn(),rsa.getd()));        
        
        //Encrypt using private key, Decrypt using public key
        //encrypted = rsa.bencrypt("TEST 123",rsa.getn(),rsa.getd());
        //System.out.println(encrypted);
        //System.out.println(rsa.bdecrypt(encrypted,rsa.getn(),rsa.gete()));
    }

}
