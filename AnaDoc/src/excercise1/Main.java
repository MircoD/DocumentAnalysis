package excercise1;

import java.util.ArrayList;
import java.util.Map;

import excercise2.Tokenizer;
import excercise3.Stemmer;
import excercise4.POSTagger;
import excercise4.PosStructure;

/**
 * Main class for the pipelining.
 * 
 */

public class Main {

	public static void main(String[] args) {

		Logger log = new Logger();
		Reader reader = new Reader();
		Tokenizer tokenizer = new Tokenizer();
		Stemmer stemm = new Stemmer();
		POSTagger tagger = new POSTagger();
		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		ArrayList<PosStructure> listOfPosCount = new ArrayList<PosStructure>();
		Map <String, String> map;

		listOfPosCount = tagger.importAndCountCorpus();
		map = tagger.SumUpPosOfWords(listOfPosCount);
		listOfReviews = reader.readAndClear("E:/Downloads/docAnaTextSample.rtf");
		 
		 
		 
		// for printing out the single words. There was an if in the Reader
		// class to only add the correct reviews.

		
		System.out.println(listOfReviews.size());
		for (int i = 0; i < listOfReviews.size(); i++) {
			String[] temp = tokenizer.splitTokens(listOfReviews.get(i).getText());
			log.log(tagger.AssignPosToWords(temp, map).toString(), "test");		
		}

	}

}
