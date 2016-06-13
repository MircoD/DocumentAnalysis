package test.DocAna;

import java.util.Arrays;




/**
 * Main class for the pipelining.
 * 
 */

public class Main {

	public static void main(String[] args) {

		//POSTagger tagger = new POSTagger();
		//tagger.importAndCountCorpus();
		//Gui gui = new Gui(tagger);
		
		Reader reader = new Reader();
		reader.readAndClear("e://Downloads/docAnaTextSample.rtf");

		
	}
}
