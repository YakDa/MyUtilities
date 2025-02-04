/**
 * 
 */
package inputDataGeneration;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 * @author mingda.cai
 *
 */
public class InputDataGeneration implements  ActionListener{

	String version = "v1.3";
	
	JTextField path = new JTextField("");
	JTextField number = new JTextField("");
	JTextField delimiter = new JTextField("");
	JTextField iccid = new JTextField("");
	JCheckBox luhnCalcCheck = new JCheckBox("Luhn Calc");
	JTextField imsi = new JTextField("");
	JTextField msisdn = new JTextField("");
	JButton button = new JButton();
	JButton openBtn = new JButton();
	
	public InputDataGeneration() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel pathLabel = new JLabel("Path:");
		JLabel numberLabel = new JLabel("Number:");
		JLabel delimiterLabel = new JLabel("Delimiter:");
		JLabel iccidLabel = new JLabel("ICCID col:");
		JLabel imsiLabel = new JLabel("IMSI col(Optional):");
		JLabel msisdnLabel = new JLabel("MSISDN col(Optional):");
		GridBagConstraints c = new GridBagConstraints();
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("InputDataGeneration " + version);
		frame.setSize(650, 400);
		frame.setResizable(false);
		frame.setVisible(true);
		
		path.setColumns(20);
		number.setColumns(5);
		delimiter.setColumns(5);
		iccid.setColumns(5);
		imsi.setColumns(5);
		msisdn.setColumns(5);
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridBagLayout());
		
		button.addActionListener(this);
		button.setText("Generate");
		
		openBtn.addActionListener(this);
		openBtn.setText("Open");
		
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(pathLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(path, c);
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		panel.add(openBtn, c);
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(numberLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(number, c);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(delimiterLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(delimiter,c);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(iccidLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		panel.add(iccid, c);
		c.gridx = 2;
		c.gridy = 3;
		panel.add(luhnCalcCheck);
		c.gridx = 0;
		c.gridy = 4;
		panel.add(imsiLabel, c);
		c.gridx = 1;
		c.gridy = 4;
		panel.add(imsi, c);
		c.gridx = 0;
		c.gridy = 5;
		panel.add(msisdnLabel, c);
		c.gridx = 1;
		c.gridy = 5;
		panel.add(msisdn, c);
		c.gridx = 0;
		c.gridy = 6;
		panel.add(button, c);
		

		
	}
	
	HashMap<Integer, String> dataMap = new HashMap<>();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button) {
			dataGeneration();
		}
		else if(e.getSource() == openBtn) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = jfc.showOpenDialog(null);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				path.setText(jfc.getSelectedFile().toString());
			}
		}
		
	}
	
	private void dataGeneration() {
		File fin = new File(path.getText());
		File fout = new File(path.getText()+"_Generated");
		
		try {
			Integer iccidCol = Integer.parseInt(iccid.getText());
			Integer imsiCol = (!imsi.getText().isEmpty())?Integer.parseInt(imsi.getText()):0;
			Integer msisdnCol = (!msisdn.getText().isEmpty())?Integer.parseInt(msisdn.getText()):0;
			
			FileReader fr = new FileReader(fin);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(fout); 
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			while((line = br.readLine()) != null) {
				if(line.length()>1 && Character.isDigit(line.charAt(0)) && Character.isDigit(line.charAt(1))) {
					String[] data = line.split(delimiter.getText());
					
					for(int i = 0;i < data.length; ++i) {
						dataMap.put(i+1, data[i]);
					}
					bw.write(line);
					bw.newLine();
				}
				else {
					bw.write(line);
					bw.newLine();
				}
			}
			
			for(int i = Integer.parseInt(number.getText()); i > 0; --i) {
				String strIccid = dataMap.get(iccidCol);
				String strImsi = "";
				String strMsisdn = "";
				if(!imsi.getText().isEmpty()) {
					strImsi = dataMap.get(imsiCol);
				}
				if(!msisdn.getText().isEmpty()) {
					strMsisdn = dataMap.get(msisdnCol);
				}
				String iccid = "";
				if(luhnCalcCheck.isSelected()) {
					iccid = MyUtils.appendLuhnBit(Long.toString(Long.parseLong(strIccid.substring(0, strIccid.length()-1)) + 1));
				}
				else {
					iccid = Long.toString(Long.parseLong(strIccid) + 1);
				}
				dataMap.put(iccidCol, iccid);
				if(!imsi.getText().isEmpty()) {
					dataMap.put(imsiCol, Long.toString(Long.parseLong(strImsi) + 1));
				}
				if(!msisdn.getText().isEmpty()) {
					dataMap.put(msisdnCol, Long.toString(Long.parseLong(strMsisdn) + 1));
				}
				String data = dataMap.entrySet().stream().map(entry -> entry.getValue() + "").collect(Collectors.joining(delimiter.getText()));
				bw.write(data);
				bw.newLine();
			}
			JOptionPane.showMessageDialog(null, "Generation is done!", "InputDataGeneration", JOptionPane.INFORMATION_MESSAGE);
			fr.close();
			bw.close();
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error: " + e1.toString(), "InputDataGeneration", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 * InputDataGeneration 1000 ICCID:1 IMSI:2 MSISDN:4 c:\\path
	 */
	public static void main(String[] args) {

		new InputDataGeneration();

	}

}
