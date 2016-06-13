package test.DocAna;

import java.util.Arrays;




/*
 B002LBKDYE
B004WO6BPS
B009NQKPUW
B000VBJEFK
7883704540
B0028OA3EY
B0028OA3EO
B008PZZND6
B006TTC57C
B002VL2PTU
B001NFNFMQ
B000067JG3
B000067JG4
B000MMMTAK
B003DBEX6K
B001TAFCBC
B0039UTDFG
B000KKQNRO
B0002Y69NQ
B005CA4SJW
 */
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
