/**
 * Each instance of this class represents a review.
 * 
 */

public class Review implements Cloneable {

	private String productId;
	private String userId;
	private String profileName;
	private String helpfulness;
	private int score;
	private String time;
	private String summary;
	private String text;

	public Review(String productId, String userId, String profileName,
			String helpfulness, int score, String time, String summary,
			String text) {
		this.productId = productId;
		this.userId = userId;
		this.profileName = profileName;
		this.helpfulness = helpfulness;
		this.score = score;
		this.time = time;
		this.summary = summary;
		this.text = text;
	}

}
