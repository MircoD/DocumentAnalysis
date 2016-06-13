package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TermFrequency {

	private HashMap<String, Integer> words;
	private ArrayList<ArrayList<Integer>> frequencyCountMatrix;
	
	public ArrayList<ArrayList<Integer>> count(ArrayList<Movies> movies){
		
		Logger log = new Logger();
		frequencyCountMatrix = new ArrayList<ArrayList<Integer>>();
		words = new HashMap<String, Integer>();
		
		for(int i=0;i<movies.size();i++){
			frequencyCountMatrix.add(new ArrayList<Integer>());
		}
		
		//loop through all movies
		for(int i =0; i < movies.size();i++){
			ArrayList<Review> currentListOfReview = movies.get(i).getReviews();
			
			//loop through all reviews of a movie
			for(int j=0;j<currentListOfReview.size();j++){
				String[] currentListOfTokens =currentListOfReview.get(j).getText();
				
				//loop through all words of a review
				for(int k=0; k<currentListOfTokens.length;k++){
					String currentToken =  currentListOfTokens[k];

					if(words.containsKey(currentToken)){
						int index= words.get(currentToken);
						ArrayList<Integer> tmp = frequencyCountMatrix.get(i);
						tmp.set(index, tmp.get(index)+1); 
						frequencyCountMatrix.set(i, tmp);
					}
					else {
						for(int l =0; l < movies.size();l++){
							frequencyCountMatrix.get(l).add(0);
						}
						
						ArrayList<Integer> tmp = frequencyCountMatrix.get(i);
						tmp.set(tmp.size()-1, 1);
						words.put(currentToken, frequencyCountMatrix.get(0).size()-1);							
					}
					
				}
			}
			
		}
		return frequencyCountMatrix;
	}
	
	/*
	 * Counts the frequency (in -all- documents) of each word.
	 */
	public HashMap<String, Integer> countAll() {
		HashMap<String, Integer> totalFrequency = new HashMap<String, Integer>();
		
		for (Map.Entry<String, Integer> entry : words.entrySet())
		{
			String word = entry.getKey();
		    int countIndex = entry.getValue();
		    int count = 0;
		    
		    // add all the counts from each document
		    for (int i = 0; i < frequencyCountMatrix.size(); i++) {
		    	count += frequencyCountMatrix.get(i).get(countIndex);
		    }
		    
		    totalFrequency.put(word, count);
		}
		
		return totalFrequency;
	}
}
