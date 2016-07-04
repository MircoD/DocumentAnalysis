package test.DocAna;

import java.util.ArrayList;
import java.util.List;

public class Sentiment {

	private List<String> positive;
	private List<String> negative;
	private List<Double> posWeights;
	private List<Double> negWeights;
	private double butWeight;
	private double positiveCount = 0;
	private double negativeCount = 0;

	public Sentiment(List<String> positive, List<String> negative) {
		this.positive = positive;
		this.negative = negative;
	}
	
	public Sentiment(List<String> positive, List<String> negative,
			List<Double> posWeights, List<Double> negWeights, double butWeight) {
		this.positive = positive;
		this.negative = negative;
		this.posWeights = posWeights;
		this.negWeights = negWeights;
		this.butWeight = butWeight;
	}
	
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
		Stemmer stem = new Stemmer();
		
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
		Stemmer stem = new Stemmer();
		
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
		Stemmer stem = new Stemmer();
		
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
		Stemmer stem = new Stemmer();
		
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
		if (positive.contains(word)) {
			positiveCount++;
		}
		if (negative.contains(word)) {
			negativeCount++;
		}
	}
	
	private void adjustWeightsImproved(String[] stemmed, String word, int index) {
		int posIndex = positive.indexOf(word);
		int negIndex = negative.indexOf(word);
		boolean negation = isNegated(stemmed, index);
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

	public List<String> getPositive() {
		return positive;
	}

	public void setPositive(List<String> positive) {
		this.positive = positive;
	}

	public List<String> getNegative() {
		return negative;
	}

	public void setNegative(List<String> negative) {
		this.negative = negative;
	}

	public List<Double> getPosWeights() {
		return posWeights;
	}

	public void setPosWeights(List<Double> posWeights) {
		this.posWeights = posWeights;
	}

	public List<Double> getNegWeights() {
		return negWeights;
	}

	public void setNegWeights(List<Double> negWeights) {
		this.negWeights = negWeights;
	}

	public double getButWeight() {
		return butWeight;
	}

	public void setButWeight(double butWeight) {
		this.butWeight = butWeight;
	}

	public double getPositiveCount() {
		return positiveCount;
	}

	public void setPositiveCount(double positiveCount) {
		this.positiveCount = positiveCount;
	}

	public double getNegativeCount() {
		return negativeCount;
	}

	public void setNegativeCount(double negativeCount) {
		this.negativeCount = negativeCount;
	}
}
