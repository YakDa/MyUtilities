/**
 * 
 */
package poGoConverter;

import java.io.*;
import java.util.*;

/**
 * @author mingda.cai
 *
 */
public class PoGoConverter {

	static String version = "v1.0";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File fin = new File(args[0]);
		File fout = new File("./Pokemons.csv");
		Scanner in = new Scanner(System.in);
		System.out.println("PoGoConverter_" + version + "\n");
		
		try {
			FileReader fr = new FileReader(fin);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(fout); 
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			
			bw.write("ID,Name,HP,Atk,Def,Max CP");
			bw.newLine();
			while((line = br.readLine()) != null) {
				if(line.startsWith("#")) {
					bw.write(line.substring(1,line.indexOf(" ")+1)+",");
					bw.write(line.substring(line.indexOf(" "), line.lastIndexOf(" "))+",");
				}
				else if(Character.isDigit(line.charAt(0))) {
					String[] powData = line.split(" ");
					bw.write(powData[0]+",");
					bw.write(powData[1]+",");
					bw.write(powData[2]+",");
				}
				else if(line.startsWith("HP")) {
					String[] temp = line.split(" ");
					bw.write(temp[3]);
					bw.newLine();
				}
			}
			
			System.out.println("Convertion is done successfully! Press any key to exit");
			in.nextLine();
			fr.close();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
