package test.DocAna;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui {

	POSTagger tagger;
	Logger log = new Logger();
	private JFrame window = new JFrame();
	private JPanel boxes = new JPanel();
	private JCheckBox checkBoxAll = new JCheckBox("Select all");
	private JCheckBox checkBoxSentences = new JCheckBox("Sentences");
	private JCheckBox checkBoxTokens = new JCheckBox("Tokens");
	private JCheckBox checkBoxPos = new JCheckBox("POS-Tags");
	private JCheckBox checkBoxStems = new JCheckBox("Stems");
	private JButton start = new JButton("Start...");
	private JFileChooser chooser = new JFileChooser();
	private JLabel label3 = new JLabel("");

	public Gui(POSTagger tag) {
		this.tagger = tag;
	
		window.setSize(600, 200);
		window.setTitle("Document Analysis Tool");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		JPanel layout = new JPanel();
		layout.setLayout(new javax.swing.BoxLayout(layout,
				javax.swing.BoxLayout.Y_AXIS));

		JLabel label1 = new JLabel("Please select the desired output:");
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		label1.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		boxes.setBackground(Color.WHITE);
		boxes.setAlignmentX(Component.CENTER_ALIGNMENT);

		checkBoxAll.setBackground(Color.BLUE);
		checkBoxAll.setForeground(Color.WHITE);
		checkBoxAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean selected = checkBoxAll.isSelected();
				checkBoxSentences.setEnabled(!selected);
				checkBoxSentences.setSelected(selected);
				checkBoxTokens.setEnabled(!selected);
				checkBoxTokens.setSelected(selected);
				checkBoxPos.setEnabled(!selected);
				checkBoxPos.setSelected(selected);
				checkBoxStems.setEnabled(!selected);
				checkBoxStems.setSelected(selected);
			}
		});

		boxes.add(checkBoxAll);
		boxes.add(checkBoxSentences);
		boxes.add(checkBoxTokens);
		boxes.add(checkBoxPos);
		boxes.add(checkBoxStems);

		JLabel label2 = new JLabel("");
		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		label2.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

		label2.setAlignmentX(Component.CENTER_ALIGNMENT);
		label2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		label3.setAlignmentX(Component.CENTER_ALIGNMENT);
		label3.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

		chooser.setDialogTitle("Please select the input file");
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (checkBoxSentences.isSelected()
						|| checkBoxTokens.isSelected()
						|| checkBoxPos.isSelected()
						|| checkBoxStems.isSelected()) {

					int chosen = chooser.showOpenDialog(null);
					if (chosen == JFileChooser.APPROVE_OPTION) {
						String path = chooser.getSelectedFile()
								.getAbsolutePath();
						analyseFile(path, checkBoxSentences.isSelected(),
								checkBoxTokens.isSelected(),
								checkBoxPos.isSelected(),
								checkBoxStems.isSelected());
						
					} else {
						// do nothing
					}
				} else {
					label3.setForeground(Color.RED);
					label3.setText("No output selected!");
				}
			}
		});

		layout.add(label1);
		layout.add(boxes);
		layout.add(label2);
		layout.add(start);
		layout.add(label3);

		window.add(layout);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);

	}

	private void analyseFile(String path, boolean sentences, boolean tokens,
			boolean pos, boolean stems) {
		label3.setForeground(Color.BLACK);
		label3.setText("Generating output...");
		
		String text = readFile(path);
		if (text == null) {
			label3.setForeground(Color.RED);
			label3.setText("Error: Could not read file.");
			return;
		}
		
		Tokenizer tokenizer = new Tokenizer();
		Stemmer stemm = new Stemmer();
		boolean failed = false;
		
		if (sentences) {
			String[] result = tokenizer.splitSentences(text);
			output("sentences.txt", result);
			label3.setForeground(Color.BLACK);
			label3.setText("Output sentences: Done.");
		}
		
		if (tokens) {
			log.log("tokens tag stemm", "output");
			String[] result = tokenizer.splitTokens(text);
			label3.setForeground(Color.BLACK);
			label3.setText("Output tokens: Done.");
			
			if (pos&&stems) {
				String [] result3 = tagger.assignPosToWords(result);
				String[] result2 = stemm.stem(result, result3);
				for(int i=0;i<result.length;i++){
					log.log(result[i] +" " + result3[i] + " " + result2[i], "output");
				}
				
				label3.setForeground(Color.BLACK);
				label3.setText("Output POS-tags: Done.");
				label3.setForeground(Color.BLACK);
				label3.setText("Output stems: Done.");
			} else if(!stems){
				label3.setForeground(Color.BLACK);
				label3.setText("It didn't work. Stemming needs POS first.");
				
			}
			
			
			
		} else if(pos || stems){
			label3.setForeground(Color.RED);
			label3.setText("Error: Stemming and POS-Tagging both need tokenization first.");
			failed = true;
		}
		
		if (!failed) {
			label3.setForeground(Color.BLACK);
			label3.setText("Output successfully completed!");
		}

	}

	
	private String readFile(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        br.close();
	        return sb.toString();
	    } catch (IOException e) {
	    	return null;
	    } catch (Exception e) {
	    	return null;
	    }
	}
	
	private void output(String filename, String[] data) {
		try {
			File file = new File(filename);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
			for (int i = 0; i < data.length; i++) {
				writer.write(data[i]);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// todo
		}
	}
}
