/**
 * Each instance of this class represents a review.
 * 
 */

public class Review {

	private String productId;
	private String userId;
	private String profileName;
	private Integer helpfulness_enumerator;
	private Integer helpfulness_denominator;
	private Integer score;
	private long time;
	private String summary;
	private String text;

	public Review(String productId, String userId, String profileName,
			Integer helpfulness_enumerator, Integer helpfulness_denominator,
			Integer score, Integer time, String summary, String text) {
		this.productId = productId;
		this.userId = userId;
		this.profileName = profileName;
		this.helpfulness_enumerator = helpfulness_enumerator;
		this.helpfulness_denominator = helpfulness_denominator;
		this.score = score;
		this.time = time;
		this.summary = summary;
		this.text = text;
	}


}
