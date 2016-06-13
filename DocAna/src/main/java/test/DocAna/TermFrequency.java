package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TermFrequency {

	public void count(ArrayList<Movies> movies){
		
		Logger log = new Logger();
		ArrayList<ArrayList<Integer>> frequencyCountMatrix = new ArrayList<ArrayList<Integer>>();
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		
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
		
		int amountWords =words.size();
		System.out.println(amountWords);
			
		Iterator itr = words.keySet().iterator();
		while(itr.hasNext()){
			String tmp123 = itr.next().toString();
			log.log(tmp123 + " "+ words.get(tmp123) , "words");
			
		}
		
		for(int i=0; i< movies.size();i++){
			System.out.println(frequencyCountMatrix.get(i).size());
			ArrayList<Review> currentListOfReview = movies.get(i).getReviews();
			String tmp = movies.get(i).movieID;
			for(int j=0;j<amountWords;j++){
			log.log(frequencyCountMatrix.get(i).get(j).toString(), tmp);
			}
		}
		
	}
}
