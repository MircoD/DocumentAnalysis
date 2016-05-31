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
	
	
	ArrayList<String> tagNminus1 = new ArrayList<String>();
	ArrayList<String> tagNminus2 = new ArrayList<String>();;
	ArrayList<Integer> count = new ArrayList<Integer>();;
	Integer sum;

	public PosStructure(String tag1,String tag2) {
		tagNminus1.add(tag1);
		tagNminus2.add(tag2);
		count.add(1);
		sum=1;
	}


	

	/**
	 * 
	 * 
	 * @param tag1
	 * @param tag2
	 * @return true if the tagCombination is found, false if it was not found. Adds 1 to count if it found the Combination.
	 */
	public boolean containsCombination(String tag1,String tag2){
				for(int i=0;i<tagNminus1.size();i++){
			if(tagNminus1.get(i).compareTo(tag1) == 0 && tagNminus2.get(i).compareTo(tag2) == 0){
				this.count.set(i, this.count.get(i)+1);
				sum++;
				return true;
			}
		}
		return false;
		
	}

	
	public void addCombination(String tag1,String tag2){
		tagNminus1.add(tag1);
		tagNminus2.add(tag2);
		count.add(1);
		sum++;
	}




	public Integer getSum() {
		return sum;
	}


	public void setSum(Integer sum) {
		this.sum = sum;
	}
	
	
}
