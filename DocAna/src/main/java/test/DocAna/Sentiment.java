package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sentiment {

	private HashMap<String, Double> positiveWords;
	private HashMap<String, Double> negativeWords;
	private double butWeight;
	private double positiveCount = 0;
	private double negativeCount = 0;
	
	public Sentiment(HashMap<String, Double> positiveWords,
			HashMap<String, Double> negativeWords) {
		this.positiveWords = positiveWords;
		this.negativeWords = negativeWords;
		this.butWeight = 1.0;
	}

	public Sentiment(HashMap<String, Double> positiveWords,
			HashMap<String, Double> negativeWords, double butWeight) {
		this.positiveWords = positiveWords;
		this.negativeWords = negativeWords;
		this.butWeight = butWeight;
	}

	/**
	 * Returns sentiment of movies, aggregating all reviews for one movie, hence long reviews might have more influence.
	 * Simply counts the words if improved=false, otherwise considers weights and negations.
	 * 
	 * @param movies - List of movies to be analyzed
	 * @param improved - which version to use
	 * @return Sentiment of the movies
	 */
	public List<Boolean> getMoviesSentiment(List<Movies> movies, boolean improved) {
		
		List<Boolean> result = new ArrayList<Boolean>();
		
		for (Movies movie : movies) {
			if (improved) {
				result.add(getMovieSentimentImproved(movie));
			} else {
				result.add(getMovieSentiment(movie));
			}
		}
		
		return result;
	}
	
	/**
	 * Returns sentiment of movies, by determining the sentiment of the single reviews before.
	 * Simply counts the words if improved=false, otherwise considers weights and negations.
	 * 
	 * @param movies - List of movies to be analyzed
	 * @param improved - which version to use
	 * @return Sentiment of the movies
	 */
	public List<Boolean> getMoviesSentiment2(List<Movies> movies, boolean improved) {
		ArrayList<Boolean> result = new ArrayList<Boolean>();
		ArrayList<ArrayList<Boolean>> intermediate = getReviewsSentiment(movies, improved);
		
		for (ArrayList<Boolean> reviewList : intermediate) {
			int positiveCount = 0;
			int negativeCount = 0;
			for (Boolean sentiment : reviewList) {
				if (sentiment) {
					positiveCount++;
				} else {
					negativeCount++;
				}
			}
			if (positiveCount > negativeCount) {
				result.add(true);
			} else {
				result.add(false);
			}
			
		}
		
		return result;
	}
	
	/**
	 * Returns sentiment of the single reviews of each movie.
	 * Simply counts the words if improved=false, otherwise considers weights and negations.
	 * 
	 * @param movies - List of movies to be analyzed
	 * @param improved - which version to use
	 * @return Sentiment of the movies
	 */
	public ArrayList<ArrayList<Boolean>> getReviewsSentiment(List<Movies> movies, boolean improved) {
		
		ArrayList<ArrayList<Boolean>> result = new ArrayList<ArrayList<Boolean>>();
		
		for (Movies movie : movies) {
			ArrayList<Boolean> movieResult = new ArrayList<Boolean>();
			for (Review review : movie.getReviews()) {
				if (improved) {
					movieResult.add(getReviewSentimentImproved(review));
				} else {
					movieResult.add(getReviewSentiment(review));
				}
			}
			result.add(movieResult);
		}
		
		return result;
	}
	
	private boolean getReviewSentiment(Review review) {
		Tokenizer token = new Tokenizer();
		String[] tokens = token.splitTokens(review.getText());
		//String[] pos = tagger.assignPosToWords(tokens);
		//String[] stemmed = stem.stem(tokens, pos);
		for (String word : tokens) {
			adjustWeights(word);
		}
		
		if (positiveCount > negativeCount) {
			positiveCount = 0;
			negativeCount = 0;
			return true;
		} else {
			positiveCount = 0;
			negativeCount = 0;
			return false;
		}
	}
	
	private boolean getMovieSentiment(Movies movie) {
		Tokenizer token = new Tokenizer();
		
		for (Review review : movie.getReviews()) {
			String[] tokens = token.splitTokens(review.getText());
			//String[] pos = tagger.assignPosToWords(tokens);
			//String[] stemmed = stem.stem(tokens, pos);
			for (String word : tokens) {
				adjustWeights(word);
			}
		}
		
		if (positiveCount > negativeCount) {
			positiveCount = 0;
			negativeCount = 0;
			return true;
		} else {
			positiveCount = 0;
			negativeCount = 0;
			return false;
		}
	}
	
	private boolean getReviewSentimentImproved(Review review) {
		Tokenizer token = new Tokenizer();
		String[] tokens = token.splitTokens(review.getText());
		//String[] pos = tagger.assignPosToWords(tokens);
		//String[] stemmed = stem.stem(tokens, pos);
		for (int i = 0; i < tokens.length; i++) {
			adjustWeightsImproved(tokens, tokens[i], i);
		}
		
		if (positiveCount > negativeCount) {
			positiveCount = 0;
			negativeCount = 0;
			return true;
		} else {
			positiveCount = 0;
			negativeCount = 0;
			return false;
		}
	}

	private boolean getMovieSentimentImproved(Movies movie) {
		Tokenizer token = new Tokenizer();
		
		for (Review review : movie.getReviews()) {
			String[] tokens = token.splitTokens(review.getText());
			//String[] pos = tagger.assignPosToWords(tokens);
			//String[] stemmed = stem.stem(tokens, pos);
			for (int i = 0; i < tokens.length; i++) {
				adjustWeightsImproved(tokens, tokens[i], i);
			}
		}
		
		if (positiveCount > negativeCount) {
			positiveCount = 0;
			negativeCount = 0;
			return true;
		} else {
			positiveCount = 0;
			negativeCount = 0;
			return false;
		}
	}
	
	private boolean isNegated(String[] stems, int index) {
		if (index == 0) {
			return false;
		} else if (index == 1) {
			return stems[index-1].toLowerCase().equals("not");
		} else {
			return (stems[index-1].toLowerCase().equals("not") || stems[index-2].toLowerCase().equals("not"));
		}
	}
	
	private boolean hasBut(String[] stems, int index) {
		int length = stems.length;
		int watchAhead = 4; // how many characters to look ahead
		
		for (int i = index + 1; i < index + watchAhead + 1; i++) {
			if (i > length) {
				break;
			} else {
				if (stems[i].toLowerCase().equals("but")) {
					return true;
				}
			}
		}
		return false;
	}
		
	private void adjustWeights(String word) {		
		if (positiveWords.containsKey(word)) {
			positiveCount++;
		}
		if (negativeWords.containsKey(word)) {
			negativeCount++;
		}
	}
	
	private void adjustWeightsImproved(String[] stemmed, String word, int index) {
		boolean negation = isNegated(stemmed, index);
		//boolean but = hasBut(stemmed, i);
		
		if (positiveWords.containsKey(word)) {
			if (negation) {
				negativeCount += positiveWords.get(word);
			} else {
				positiveCount += positiveWords.get(word);
			}
		}
		if (negativeWords.containsKey(word)) {
			if (negation) {
				positiveCount += negativeWords.get(word);
			} else {
				negativeCount += negativeWords.get(word);
			}
		}
	}

	public HashMap<String, Double> getPositiveWords() {
		return positiveWords;
	}

	public void setPositiveWords(HashMap<String, Double> positiveWords) {
		this.positiveWords = positiveWords;
	}

	public HashMap<String, Double> getNegativeWords() {
		return negativeWords;
	}

	public void setNegativeWords(HashMap<String, Double> negativeWords) {
		this.negativeWords = negativeWords;
	}

	public double getButWeight() {
		return butWeight;
	}

	public void setButWeight(double butWeight) {
		this.butWeight = butWeight;
	}
}
