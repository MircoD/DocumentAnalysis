package test.DocAna;

import java.util.ArrayList;

public class Movies {
	
	String movieID;
	ArrayList<Review> reviews;
	Stats stats;


	public Movies (String movieID, Review review){
		this.movieID = movieID;
		this.reviews = new ArrayList<Review>();
		reviews.add(review);
	}
	
	public Movies(String text){
		Review rev = new Review(text);
		this.reviews = new ArrayList<Review>();
		reviews.add(rev);
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

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	
	
}
