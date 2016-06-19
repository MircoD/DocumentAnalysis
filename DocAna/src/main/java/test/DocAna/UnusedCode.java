package test.DocAna;

import java.util.ArrayList;
import java.util.Arrays;

public class UnusedCode {
	
	public void createFilesOfReviews(ArrayList<Review> listOfReviews){

		Tokenizer token = new Tokenizer();
		POSTagger tagger = new POSTagger();
		Stemmer stemm = new Stemmer();
		Logger log = new Logger();
		long startTime =0;
		
		tagger.importAndCountCorpus();
		
		startTime = System.nanoTime();
		int k=0;
		int j=0;
		for(int i =0; i<listOfReviews.size();i++){
			Review curReview = listOfReviews.get(i);
			
			String productId = curReview.getProductId();
			String userId = curReview.getUserId();
			String profileName = curReview.getProfileName();
			int helpfulness_denom = curReview.getHelpfulness_denom();
			int helpfulness_enum = curReview.getHelpfulness_enum();
			int score = curReview.getScore();
			long time = curReview.getTime();
			String summary = curReview.getSummary();
			String text = curReview.getText();

			
			log.log(productId, "listOfReviews"+String.valueOf(j));
			log.log(userId, "listOfReviews"+String.valueOf(j));
			log.log(profileName, "listOfReviews"+String.valueOf(j));
			log.log(String.valueOf(helpfulness_denom), "listOfReviews"+String.valueOf(j));
			log.log(String.valueOf(helpfulness_enum), "listOfReviews"+String.valueOf(j));
			log.log(String.valueOf(score), "listOfReviews"+String.valueOf(j));
			log.log(String.valueOf(time), "listOfReviews"+String.valueOf(j));
			log.log(summary, "listOfReviews"+String.valueOf(j));
			log.log(text, "listOfReviews"+String.valueOf(j));
			log.log(" ", "listOfReviews"+String.valueOf(j));		
		}
	}
	
	
	
	

}
