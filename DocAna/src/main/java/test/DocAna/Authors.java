package test.DocAna;

import java.util.ArrayList;

public class Authors {
	
	String authorID;
	ArrayList<Review> reviews;


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
