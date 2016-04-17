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

	public Integer getHelpfulness_enumerator() {
		return helpfulness_enumerator;
	}

	public void setHelpfulness_enumerator(Integer helpfulness_enumerator) {
		this.helpfulness_enumerator = helpfulness_enumerator;
	}

	public Integer getHelpfulness_denominator() {
		return helpfulness_denominator;
	}

	public void setHelpfulness_denominator(Integer helpfulness_denominator) {
		this.helpfulness_denominator = helpfulness_denominator;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
