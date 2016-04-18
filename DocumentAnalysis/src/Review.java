/**
 * Each instance of this class represents a review.
 * 
 */

public class Review implements Cloneable {

	private String productId;
	private String userId;
	private String profileName;
	private int helpfulness_denom;
	private int helpfulness_enum;
	private int score;
	private String time;
	private String summary;
	private String text;

	public Review(String productId, String userId, String profileName,
			int helpfulness_denom,int helpfulness_enum,int score, String time, String summary,
			String text) {
		this.productId = productId;
		this.userId = userId;
		this.profileName = profileName;
		this.helpfulness_denom = helpfulness_denom;
		this.helpfulness_enum = helpfulness_enum;
		this.score = score;
		this.time = time;
		this.summary = summary;
		this.text = text;
	}

}
