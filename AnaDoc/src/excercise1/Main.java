package excercise1;

import java.util.ArrayList;

import excercise2.Tokenizer;

/**
 * Main class for the pipelining.
 * 
 */

public class Main {

	public static void main(String[] args) {

		Logger log = new Logger();
		Reader reader = new Reader();
		Tokenizer tokenizer = new Tokenizer();
		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		
		
		listOfReviews = reader
				.readAndClear("E:/Downloads/docAnaTextSample.rtf");
		
		//for printing out the single words. There was an if in the Reader class to only add the correct reviews.
		System.out.println(listOfReviews.size());
		for (int i = 0; i < listOfReviews.size(); i++) {

			String temp = listOfReviews.get(i).getText();
			String[] temp2 = tokenizer.splitTokens(temp);
			for (int j = 0; j < temp2.length; j++) {
				log.log(temp2[j], "wordle");
			}

		}

	}

}
