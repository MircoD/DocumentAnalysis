package excercise1;

import java.util.ArrayList;
import java.util.Arrays;

import excercise2.Tokenizer;

public class Main {
	
	public static void main(String[] args) {
		
		Logger log = new Logger();
		Reader reader = new Reader();
		Tokenizer tokenizer = new Tokenizer();
		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		listOfReviews = reader.readAndClear("E:/Downloads/docAnaTextSample.rtf");
		for(int i = 0; i<listOfReviews.size();i++){
			String temp = listOfReviews.get(i).getText();
			log.log(Arrays.toString(tokenizer.splitTokens(temp)), "testTokenizer");			
		}
		
	}

}
