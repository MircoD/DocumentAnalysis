package test.DocAna;

import java.util.ArrayList;
import java.util.List;

public class Sentiment {

	public static List<Boolean> getMoviesSentiment(List<Movies> movies, List<String> positive, List<String> negative) {
		List<Boolean> result = new ArrayList<Boolean>();
		
		for (Movies movie : movies) {
			result.add(getMovieSentiment(movie, positive, negative));
		}
		
		return result;
	}
	

	public static ArrayList<ArrayList<Boolean>> getReviewsSentiment(List<Movies> movies, List<String> positive, List<String> negative) {
		ArrayList<ArrayList<Boolean>> result = new ArrayList<ArrayList<Boolean>>();
		
		for (Movies movie : movies) {
			ArrayList<Boolean> movieResult = new ArrayList<Boolean>();
			for (Review review : movie.getReviews()) {
				movieResult.add(getReviewSentiment(review, positive, negative));
			}
			result.add(movieResult);
		}
		
		return result;
	}
	
	
	private static boolean getReviewSentiment(Review review, List<String> positive, List<String> negative) {
		Tokenizer token = new Tokenizer();
		int positiveCount = 0;
		int negativeCount = 0;
		
		String[] tokens = token.splitTokens(review.getText());
		for (String word : tokens) {
			if (positive.contains(word)) {
				positiveCount++;
			}
			if (negative.contains(word)) {
				negativeCount++;
			}
		}
		
		if (positiveCount > negativeCount) {
			return true;
		} else {
			return false;
		}
		
		
	}
	
	
	private static boolean getMovieSentiment(Movies movie, List<String> positive, List<String> negative) {
		Tokenizer token = new Tokenizer();
		int positiveCount = 0;
		int negativeCount = 0;
		
		for (Review review : movie.getReviews()) {
			String[] tokens = token.splitTokens(review.getText());
			for (String word : tokens) {
				if (positive.contains(word)) {
					positiveCount++;
				}
				if (negative.contains(word)) {
					negativeCount++;
				}
			}
		}
		
		if (positiveCount > negativeCount) {
			return true;
		} else {
			return false;
		}
	}
}
