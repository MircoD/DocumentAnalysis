package test.DocAna;

import java.util.ArrayList;
import java.util.List;

public class Sentiment {

	private List<String> positive;
	private List<String> negative;
	private List<Double> posWeights;
	private List<Double> negWeights;
	private double butWeight;
	
	public Sentiment(List<String> positive, List<String> negative,
			List<Double> posWeights, List<Double> negWeights, double butWeight) {
		this.positive = positive;
		this.negative = negative;
		this.posWeights = posWeights;
		this.negWeights = negWeights;
		this.butWeight = butWeight;
	}
	
	public List<Boolean> getMoviesSentiment(List<Movies> movies, POSTagger tagger, boolean improved) {
		
		List<Boolean> result = new ArrayList<Boolean>();
		
		for (Movies movie : movies) {
			if (improved) {
				result.add(getMovieSentimentImproved(movie, tagger));
			} else {
				result.add(getMovieSentiment(movie, tagger));
			}
		}
		
		return result;
	}
	

	public ArrayList<ArrayList<Boolean>> getReviewsSentiment(List<Movies> movies, POSTagger tagger, boolean improved) {
		
		ArrayList<ArrayList<Boolean>> result = new ArrayList<ArrayList<Boolean>>();
		
		for (Movies movie : movies) {
			ArrayList<Boolean> movieResult = new ArrayList<Boolean>();
			for (Review review : movie.getReviews()) {
				if (improved) {
					movieResult.add(getReviewSentimentImproved(review, tagger));
				} else {
					movieResult.add(getReviewSentiment(review, tagger));
				}
			}
			result.add(movieResult);
		}
		
		return result;
	}
	
	
	private boolean getReviewSentiment(Review review, POSTagger tagger) {
		Tokenizer token = new Tokenizer();
		Stemmer stem = new Stemmer();
		int positiveCount = 0;
		int negativeCount = 0;
		
		String[] tokens = token.splitTokens(review.getText());
		String[] pos = tagger.assignPosToWords(tokens);
		String[] stemmed = stem.stem(tokens, pos);
		for (String word : stemmed) {
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
	
	
	private boolean getMovieSentiment(Movies movie, POSTagger tagger) {
		Tokenizer token = new Tokenizer();
		Stemmer stem = new Stemmer();
		int positiveCount = 0;
		int negativeCount = 0;
		
		for (Review review : movie.getReviews()) {
			String[] tokens = token.splitTokens(review.getText());
			String[] pos = tagger.assignPosToWords(tokens);
			String[] stemmed = stem.stem(tokens, pos);
			for (String word : stemmed) {
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
	
	
	private boolean getReviewSentimentImproved(Review review, POSTagger tagger) {
		
		// TODO
		return false;
	}


	private boolean getMovieSentimentImproved(Movies movie, POSTagger tagger) {
		Tokenizer token = new Tokenizer();
		Stemmer stem = new Stemmer();
		int positiveCount = 0;
		int negativeCount = 0;
		
		for (Review review : movie.getReviews()) {
			String[] tokens = token.splitTokens(review.getText());
			String[] pos = tagger.assignPosToWords(tokens);
			String[] stemmed = stem.stem(tokens, pos);
			for (int i = 0; i < stemmed.length; i++) {
				int posIndex = positive.indexOf(stemmed[i]);
				int negIndex = negative.indexOf(stemmed[i]);
				boolean negation = isNegated(stemmed, i);
				//boolean but = hasBut(stemmed, i);
				
				if (posIndex > -1) {
					if (negation) {
						negativeCount += posWeights.get(posIndex);
					} else {
						positiveCount += posWeights.get(posIndex);
					}
				}
				if (negIndex > -1) {
					if (negation) {
						positiveCount += negWeights.get(negIndex);
					} else {
						negativeCount += negWeights.get(negIndex);
					}
				}
			}
		}
		
		if (positiveCount > negativeCount) {
			return true;
		} else {
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
}
