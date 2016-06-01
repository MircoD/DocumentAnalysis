package test.DocAna;

import java.util.Arrays;




/**
 * Main class for the pipelining.
 * 
 */

public class Main {

	public static void main(String[] args) {
		
		
		POSTagger tagger = new POSTagger();
		
		String[] test = new String[]{"this", "is", "a", "test", "text", ".", "i", "like","to", "dance"};
		tagger.importAndCountCorpus();
		tagger.assignPosToWords(test);
		System.out.println(Arrays.toString(test));
		
		//Gui gui = new Gui();
		

		
	}
}
