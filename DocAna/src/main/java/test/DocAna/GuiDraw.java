package test.DocAna;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiDraw extends JPanel{
	ArrayList<Double> ret;


	public GuiDraw(ArrayList<Double> ret) {
		 JFrame frame = new JFrame("Document Analysis Tool");
		 frame.setSize(480, 480);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
         this.ret = ret;
	}
	
	public void paint (Graphics g){
		double avgDifference=0;
	
		for(int i=0;i<ret.size();i++){
			if(ret.get(i)>=0){
				double tmp =ret.get(i)*720;
				int tmp1 = (int) tmp;
				g.setColor(Color.GREEN);
				if(tmp1==0){
					tmp1=2;
				}
				g.fillArc(i*140+1, 35, 75, 75, 90,-tmp1);
				avgDifference = avgDifference +ret.get(i);
				
			} else{
				double tmp =ret.get(i)*-720;
				int tmp1 = (int) tmp;
				g.setColor(Color.BLUE);
				if(tmp1==0){
					tmp1=2;
				}
				g.fillArc(i*140+1, 35, 75, 75, 90, -tmp1);
				avgDifference = avgDifference + (-1*ret.get(i));
			}
			
			
		}
		
		avgDifference = (avgDifference/6) * 720;
		int tmp3 = (int) avgDifference;
		if(tmp3==0){
			tmp3=2;
		}
		g.setColor(Color.RED);
		g.fillArc(350, 140, 75, 75, 90, - tmp3);
	

	}
	
}
