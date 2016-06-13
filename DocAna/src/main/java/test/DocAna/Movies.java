package test.DocAna;

import java.util.ArrayList;

public class Movies {
	
	String movieID;
	ArrayList<Review> reviews;

	public Movies (String movieID, Review review){
		this.movieID = movieID;
		this.reviews = new ArrayList<Review>();
		reviews.add(review);
	}

	public String getMovieID() {
		return movieID;
	}

	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	
}
