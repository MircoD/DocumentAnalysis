package test.DocAna;

import java.util.ArrayList;

/**
 * Each instance of this class represents a review.
 * 
 */

public class Review {

	private String productId;
	private String userId;
	private String profileName;
	private int helpfulness_denom; // helpfulness denominator
	private int helpfulness_enum; // helpfulness enumerator
	private int score;
	private long time;
	private String summary;
	private String text;
	private String[] pos;

	public Review(String productId, String userId, String profileName,
			int helpfulness_denom, int helpfulness_enum, int score, long time,
			String summary, String text) {
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

	public Review(String text){
		this.text = text;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
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

	public int getHelpfulness_denom() {
		return helpfulness_denom;
	}

	public void setHelpfulness_denom(int helpfulness_denom) {
		this.helpfulness_denom = helpfulness_denom;
	}

	public int getHelpfulness_enum() {
		return helpfulness_enum;
	}

	public void setHelpfulness_enum(int helpfulness_enum) {
		this.helpfulness_enum = helpfulness_enum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public String[] getPos() {
		return pos;
	}

	public void setPos(String[] pos) {
		this.pos = pos;
	}

}
