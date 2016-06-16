package test.DocAna;

import java.util.ArrayList;

public class Authors {
	
	String authorID;
	ArrayList<Review> reviews;
	double avgSentenceLength;
	double avgCapitalLetter;
	double avgWordLength;
	double words;
	double avgfunctionWords;
	ArrayList <Double> tfidf;	

	public double getAvgSentenceLength() {
		return avgSentenceLength;
	}


	public void setAvgSentenceLength(double avgSentenceLength) {
		this.avgSentenceLength = avgSentenceLength;
	}


	public double getAvgWordLength() {
		return avgWordLength;
	}


	public void setAvgWordLength(double avgWordLength) {
		this.avgWordLength = avgWordLength;
	}


	public double getWords() {
		return words;
	}


	public void setWords(double words) {
		this.words = words;
	}


	public double getAvgfunctionWords() {
		return avgfunctionWords;
	}


	public void setAvgfunctionWords(double avgfunctionWords) {
		this.avgfunctionWords = avgfunctionWords;
	}


	public Authors (String authorID, Review review){
		this.authorID = authorID;
		this.reviews = new ArrayList<Review>();
		reviews.add(review);
	}


	public String getAuthorID() {
		return authorID;
	}


	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}


	public ArrayList<Review> getReviews() {
		return reviews;
	}


	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

}
