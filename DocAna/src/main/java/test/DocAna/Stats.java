package test.DocAna;

import java.util.ArrayList;

public class Stats {
	double avgSentenceLength;
	double avgCapitalLetter;
	double avgWordLength;
	double avgWordsPerReview;
	double avgfunctionWords;

	public Stats(double avgSentenceLength, double avgCapitalLetter,
			double avgWordLength, double avgWordsPerReview,
			double avgfunctionWords) {
		this.avgSentenceLength = avgSentenceLength-1/(1005.0);
		this.avgCapitalLetter = avgCapitalLetter/1.01052632;
		this.avgWordLength = avgWordLength-2.882353/(6.0-2.882353);
		this.avgWordsPerReview = avgWordsPerReview-11/4410;
		this.avgfunctionWords = avgfunctionWords/0.5714286;
	}

	public double getAvgSentenceLength() {
		return avgSentenceLength;
	}

	public void setAvgSentenceLength(double avgSentenceLength) {
		this.avgSentenceLength = avgSentenceLength;
	}

	public double getAvgCapitalLetter() {
		return avgCapitalLetter;
	}

	public void setAvgCapitalLetter(double avgCapitalLetter) {
		this.avgCapitalLetter = avgCapitalLetter;
	}

	public double getAvgWordLength() {
		return avgWordLength;
	}

	public void setAvgWordLength(double avgWordLength) {
		this.avgWordLength = avgWordLength;
	}


	public double getAvgfunctionWords() {
		return avgfunctionWords;
	}

	public void setAvgfunctionWords(double avgfunctionWords) {
		this.avgfunctionWords = avgfunctionWords;
	}

	public double getAvgWordsPerReview() {
		return avgWordsPerReview;
	}

	public void setAvgWordsPerReview(double avgWordsPerReview) {
		this.avgWordsPerReview = avgWordsPerReview;
	}


	
}
