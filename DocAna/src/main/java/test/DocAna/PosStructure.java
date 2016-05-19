package test.DocAna;


import java.util.ArrayList;

/**
 *
 * This is a data structure to help organize all words and their tags. Each
 * object of this class has a String, which is a word from the brown corpus and
 * two Array Lists, with the tags the word appears with and the correspondent
 * count of the tags. This helps to look for the most frequent tag used for
 * a word.
 *
 */

public class PosStructure {
	String word;
	ArrayList<String> tagArray = new ArrayList<String>();
	ArrayList<Integer> countArray = new ArrayList<Integer>();

	public PosStructure(String word, String tag, int count) {
		this.word = word;
		tagArray.add(tag);
		countArray.add(count);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public ArrayList<String> getTagArray() {
		return tagArray;
	}

	public void setTagArray(ArrayList<String> tagArray) {
		this.tagArray = tagArray;
	}

	public ArrayList<Integer> getCountArray() {
		return countArray;
	}

	public void setCountArray(ArrayList<Integer> countArray) {
		this.countArray = countArray;
	}

}
