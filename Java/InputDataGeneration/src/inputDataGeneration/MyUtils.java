package inputDataGeneration;

public class MyUtils {

	public static String appendLuhnBit(String sInput) {
		Long lSum = (long)0;
		for(int i = sInput.length() - 1; i >= 0; --i) {
			int iDoubleNum = Character.getNumericValue(sInput.charAt(i)) * ((sInput.length() - i) % 2 + 1);
			
			if(iDoubleNum > 9) {
				iDoubleNum = iDoubleNum - 9;
			}
			lSum = lSum + iDoubleNum;
		}
		
		lSum = lSum * 9;
		return sInput + lSum % 10;
	}
}
