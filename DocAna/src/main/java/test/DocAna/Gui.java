package test.DocAna;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {
	POSTagger tagger;
	private JFileChooser chooser1;
	private JFileChooser chooser2;
	private JButton chose1;
	private JButton chose2;
	private JButton compare;


	public Gui() { 

        	
            JFrame frame = new JFrame("Document Analysis Tool");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 200);
        	JPanel layout = new JPanel();
    		layout.setLayout(new javax.swing.BoxLayout(layout,
    				javax.swing.BoxLayout.Y_AXIS));
            tagger = new POSTagger();
    		//tagger.importAndCountCorpus();
    		chooser1 = new JFileChooser();
    		chooser2 = new JFileChooser();
    		chose1 = new JButton("Review 1");
    		chose2 = new JButton("Review 2");
    		compare = new JButton("Compare");
    		ArrayList<Double> test = new ArrayList<Double>();
    		test.add(-0.25);
    		test.add(0.25);
    		test.add(-0.05);
    		test.add(-0.25);
    		test.add(0.000001);
    		test.add(-0.47);
    		GuiDraw gd1 = new GuiDraw(test);


    		chooser1.setDialogTitle("Choose the first review");
    		chooser2.setDialogTitle("Choose the second review");
    		
    		layout.add(compare);
    		layout.add(chose1);
    		layout.add(chose2);

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
    		
    		compare.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
 
    				
    				
    			}
    		});

    		frame.add(gd1);
    		//frame.add(layout);
            frame.pack();
            frame.setVisible(true);

        }
  
}


