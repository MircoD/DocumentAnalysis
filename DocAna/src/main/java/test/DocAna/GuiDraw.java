package test.DocAna;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiDraw {
	private JFrame window;
	POSTagger tagger;
	private JFileChooser chooser1;
	private JFileChooser chooser2;
	private JButton chose1;
	private JButton chose2;
	private JButton start;
	UnusedCode us;

	public GuiDraw() {
		UnusedCode us = new UnusedCode();
		tagger = new POSTagger();
		tagger.importAndCountCorpus();
		window = new JFrame();
		chooser1 = new JFileChooser();
		chooser2 = new JFileChooser();
		chose1 = new JButton("Review 1");
		chose2 = new JButton("Review 2");
		start = new JButton("Compare");

		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		window.setSize(600, 200);
		window.setTitle("Document Analysis Tool");

		JPanel layout = new JPanel();
		layout.setLayout(new javax.swing.BoxLayout(layout,
				javax.swing.BoxLayout.Y_AXIS));

		chooser1.setDialogTitle("Choose the first review");
		chooser2.setDialogTitle("Choose the second review");

		chose1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int chosen = chooser1.showOpenDialog(null);
				if (chosen == JFileChooser.APPROVE_OPTION) {
					String path = chooser1.getSelectedFile().getAbsolutePath();				
				} else {
					// do nothing
				}

			}
		});

		chose2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int chosen = chooser2.showOpenDialog(null);
				if (chosen == JFileChooser.APPROVE_OPTION) {
					String path = chooser2.getSelectedFile().getAbsolutePath();
				} else {
					// do nothing
				}

			}
		});
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	

			}
		});

		layout.add(start);
		layout.add(chose1);
		layout.add(chose2);

		window.add(layout);
		window.setVisible(true);

	}

	

	
}
