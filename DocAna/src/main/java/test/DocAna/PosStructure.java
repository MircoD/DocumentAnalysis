package test.DocAna;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * This is a data structure to help organize all words and their tags. Each
 * object of this class has a String, which is a word from the brown corpus and
 * two Array Lists, with the tags the word appears with and the correspondent
 * count of the tags. This helps to look for the most frequent tag used for a
 * word.
 *
 */

public class PosStructure {

	Map<String, Integer> previousTagSingle;
	Map<String, Integer> previousTagCombination;
	Integer sum;
		
	public PosStructure(String nMinus2Tag, String nMinus1Tag) {
		this.previousTagSingle = new HashMap<String, Integer>();
		this.previousTagCombination = new HashMap<String, Integer>();
		previousTagSingle.put(nMinus1Tag, 1);
		previousTagCombination.put(nMinus2Tag +","+ nMinus1Tag, 1);
		sum = 1;
	}

	/**
	 * @param tag1
	 * @param tag2
	 * @return true if the tagCombination was found, false if it was not found.
	 *         Adds 1 to the count if  combination was found.
	 */
	public void increase(String nMinus2Tag, String nMinus1Tag) {		
				previousTagSingle.put(nMinus1Tag, previousTagSingle.get(nMinus1Tag)+1);
				previousTagCombination.put(nMinus2Tag+","+nMinus1Tag,previousTagCombination.get(nMinus2Tag+","+nMinus1Tag)+1);
				sum++;		
	}
	

	/**
	 * @param tag1
	 * @param tag2
	 * @return true if the tag combination was found, false if it was not found.
	 */
	public boolean containsCombination(String nMinus2Tag, String nMinus1Tag) {
		if (previousTagCombination.containsKey(nMinus2Tag+","+nMinus1Tag)) {	
			return true;
		} else{
			return false;
		}
	}
	
	

	/**
	 * adds the combination to the map
	 * 
	 * @param tag1
	 * @param tag2
	 * 
	 */
	public void addCombination(String nMinus2Tag, String nMinus1Tag) {
		previousTagSingle.put(nMinus1Tag, 1);
		previousTagCombination.put(nMinus2Tag+","+nMinus1Tag, 1);
		sum++;
	}

	public Map<String, Integer> getTagNminus1() {
		return previousTagSingle;
	}

	public Map<String, Integer> getTagNminus2() {
		return previousTagCombination;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	

}
