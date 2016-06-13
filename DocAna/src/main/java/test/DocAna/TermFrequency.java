package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;

public class TermFrequency {
	HashMap<String, Integer> words;
	ArrayList<ArrayList<Integer>> frequencies;
	
	public void count(ArrayList<Movies> movies){
		
		for(int i =0; i < movies.size();i++){
			ArrayList<Review> currentListOfReview = movies.get(i).getReviews();
			
			for(int j=0;j<currentListOfReview.size();j++){
				String[] currentListOfTokens =currentListOfReview.get(j).getText();
				for(int k=0; k<currentListOfTokens.length;k++){
					String currentToken =  currentListOfTokens[k];
					
					if(words.containsKey(currentListOfTokens[k])){
						int index= words.get(currentToken);
						ArrayList<Integer> tmp = frequencies.get(i);
						tmp.set(index, tmp.get(index)+1); 
						frequencies.set(i, tmp);
					}
					else{
						for(int l =0; l < movies.size();l++){
							ArrayList<Integer> tmp1 = frequencies.get(l);
							tmp1.add(0);
							frequencies.set(l, tmp1);
						}
						
						ArrayList<Integer> tmp = frequencies.get(i);
						tmp.set(tmp.size()-1, 1);
						words.put(currentToken, frequencies.get(0).size());
						
						
					}
					
				}
			}
			
		}
		
		
	}
}
