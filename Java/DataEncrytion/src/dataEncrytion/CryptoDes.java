package dataEncrytion;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;


public class CryptoDes {

	public static String desEncryption(String src, String key, boolean toEncrypt) {
		
		Cipher desCipher;
		
		try {
			
			byte[] keyBytes = hexStr2Bytes(key);
			
			desCipher = Cipher.getInstance("DES/ECB/NoPadding");
			SecretKey desKey = new SecretKeySpec(keyBytes, "DES");
			if(toEncrypt == true) {
				desCipher.init(Cipher.ENCRYPT_MODE, desKey);
			}
			else {
				desCipher.init(Cipher.DECRYPT_MODE, desKey);
			}
			
			//byte[] result = desCipher.doFinal(hexStr2Bytes(src));
			byte[] baSrc = hexStr2Bytes(src);
			byte[] result = desCipher.doFinal(baSrc);;
			
			return bytes2HexStr(result).substring(0, src.length()).toUpperCase();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "DataEncryption", JOptionPane.ERROR_MESSAGE);
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "DataEncryption", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "DataEncryption", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "DataEncryption", JOptionPane.ERROR_MESSAGE);
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "DataEncryption", JOptionPane.ERROR_MESSAGE);
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
