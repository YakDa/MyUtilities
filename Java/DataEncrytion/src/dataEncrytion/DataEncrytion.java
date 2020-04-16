/**
 * 
 */
package dataEncrytion;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author mingda.cai
 *
 */
public class DataEncrytion implements ActionListener{

	String version = "v1.0";
	JTextField path = new JTextField();
	JTextField field = new JTextField();
	JTextField key = new JTextField();
	JButton openBtn = new JButton("Open");
	JButton encBtn = new JButton("Encrypt");
	
	public DataEncrytion() {
		JFrame frame = new JFrame("DataEncryption_" + version);
		JPanel panel = new JPanel();
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 220);
		frame.setResizable(false);
		frame.setVisible(true);
		
		path.setColumns(20);
		field.setColumns(20);
		key.setColumns(20);
		
		openBtn.addActionListener(this);
		encBtn.addActionListener(this);
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		JLabel pathLabel = new JLabel("Path:");
		c.gridx = 0;c.gridy = 0;
		panel.add(pathLabel, c);
		c.gridx = 1;c.gridy = 0;
		panel.add(path, c);
		c.gridx = 2;c.gridy = 0;
		panel.add(openBtn, c);
		
		JLabel fieldLabel = new JLabel("Fields:");
		c.gridx = 0;c.gridy = 1;
		panel.add(fieldLabel, c);
		c.gridx = 1;c.gridy = 1;
		panel.add(field, c);
		
		JLabel keyLabel = new JLabel("Key:");
		c.gridx = 0;c.gridy = 2;
		panel.add(keyLabel, c);
		c.gridx = 1;c.gridy = 2;
		panel.add(key, c);
		
		c.gridx = 1;c.gridy = 3;
		panel.add(encBtn, c);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openBtn) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = jfc.showOpenDialog(null);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				path.setText(jfc.getSelectedFile().toString());
			}
		}
		else if(e.getSource() == encBtn) {
			encryptFields(field.getText());
		}
		
	}
	
	HashMap<String, Integer> fieldMap = new HashMap<>();
	
	private void encryptFields(String text) {
		
		String[] fieldToEncrypt = text.split("/");
		File fin = new File(path.getText());
		File fout = new File(path.getText()+"_Encrypted");
		
		FileReader fr;
		try {
			fr = new FileReader(fin);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(fout); 
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			while((line = br.readLine()) != null) {
				if(line.startsWith("var_out:")) {
					String[] tempFields = line.substring(8).split("/");
					for(int i = 0; i < tempFields.length; ++i) {
						fieldMap.put(tempFields[i], i);
					}
					bw.write(line);
					bw.newLine();
				}
				else if(line.length()>1 && Character.isDigit(line.charAt(0)) && Character.isDigit(line.charAt(1))) {
					String[] data = line.split(" ");
					
					for(String s: fieldToEncrypt) {
						Integer idx = fieldMap.get(s);
						data[idx] = CryptoDes.desEncryption(data[idx], key.getText());
					}
					
					line = String.join(" ", data);
					
					bw.write(line);
					bw.newLine();
				}
				else {
					bw.write(line);
					bw.newLine();
				}
			}
			
			JOptionPane.showMessageDialog(null, "Encryption is done!", "DataEncryption", JOptionPane.INFORMATION_MESSAGE);
			fr.close();
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e.toString(), "DataEncryption", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new DataEncrytion();
	}

}
