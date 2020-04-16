package dataEncrytion;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class CryptoDes {

	public static String desEncryption(String src, String key) {
		
		Cipher desCipher;
		
		try {
			
			byte[] keyBytes = hexStr2Bytes(key);
			
			desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			SecretKey desKey = new SecretKeySpec(keyBytes, "DES");
			desCipher.init(Cipher.ENCRYPT_MODE, desKey);
			
			byte[] cipheredBytes = desCipher.doFinal(hexStr2Bytes(src));
			
			return bytes2HexStr(cipheredBytes).substring(0, src.length()).toUpperCase();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static byte[] hexStr2Bytes(String s) {
		
		byte[] result = new byte[s.length()/2];
		
		for(int i = 0; i < result.length; ++i) {
			int idx = i * 2;
			int j = Integer.parseInt(s.substring(idx, idx + 2), 16);
			result[i] = (byte)j;
		}
		
		return result;
	}
	
	private static String bytes2HexStr(byte[] ba) {
	   StringBuilder sb = new StringBuilder(ba.length * 2);
	   for(byte b: ba)
	      sb.append(String.format("%02x", b));
	   return sb.toString();
	}
}
