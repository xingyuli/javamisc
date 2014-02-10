package org.swordess.toy.javamisc.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoDemo {

	/* symmetric encryption */
	
	public void encrypt() {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			// first we need to create a secret key
			SecretKey key = generator.generateKey();
			saveFile("key.data", key.getEncoded());
			
			// then use a cipher to encrypt
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			String text = "Hello World";
			byte[] encrypted = cipher.doFinal(text.getBytes());

			// and we can save the encrypted data to somewhere,
			// thus it could be used by the decrypter
			saveFile("encrypted.bin", encrypted);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	public void decrypt() {
		try {
			// first we need to read the secret key back
			byte[] keyData = readFile("key.data");
			SecretKeySpec keySpec = new SecretKeySpec(keyData, "DES");
			
			// then use a cipher to decrypt
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] data = readFile("encrypted.bin");
			byte[] result = cipher.doFinal(data);
			
			// and we got the original text at last
			System.out.println(new String(result));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	/* asymmetric encryption */
	
	public void asymmetricEncryption() {
		try {
			Signature signatureForSign = Signature.getInstance("SHA1withDSA");
			
			KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
			KeyPair keyPair = keyGenerator.generateKeyPair();
			
			// a private key is needed when sign
			PrivateKey privateKey = keyPair.getPrivate();
			signatureForSign.initSign(privateKey);
			byte[] data = "Hello World".getBytes();
			signatureForSign.update(data);
			byte[] signatureData = signatureForSign.sign(); // got the signature

			// the public key for verifying the signature can be transferred
			// as a file
			PublicKey publicKey = keyPair.getPublic();
			
			// send the public key to ...
			
			/* ************************************************************* */
			
			// assumed that we've already got the public key somehow
			PublicKey pubKeyGot = publicKey;
			Signature signatureForVerify = Signature.getInstance("SHA1withDSA");
			signatureForVerify.initVerify(pubKeyGot);
			signatureForVerify.update(data);
			System.out.println(signatureForVerify.verify(signatureData));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
	
	private static void saveFile(String filename, byte[] data) throws IOException {
		String filepath = CryptoDemo.class.getResource("").getPath() + filename;
		File outFile = new File(filepath);
		if (outFile.exists()) {
			outFile.delete();
		}
		outFile.createNewFile();
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outFile);
			out.write(data);
			out.flush();
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
	
	private static byte[] readFile(String filename) throws IOException {
		String filepath = CryptoDemo.class.getResource("").getPath() + filename;
		File inFile = new File(filepath);
		if (!inFile.exists()) {
			return new byte[0];
		}
		
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(inFile);
			FileChannel inChannel = inStream.getChannel();
			
			ByteBuffer buffer = ByteBuffer.allocate(32);
			while (-1 != inChannel.read(buffer));
			buffer.flip();
			return Arrays.copyOf(buffer.array(), buffer.limit());
		} finally {
			if (null != inStream) {
				inStream.close();
			}
		}
	}
	
}
