package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Similarity {
	private HashMap<String, Integer> words;
	private ArrayList<ArrayList<Integer>> frequencyCountMatrix;
	
	public Similarity(){
		this.words = new HashMap<String, Integer>();
		this.frequencyCountMatrix = new ArrayList<ArrayList<Integer>>();
	}

	
	//counts TermFrequency
	public ArrayList<ArrayList<Integer>> countTermFrequency(HashMap<String,Movies> movies){
		Tokenizer token = new Tokenizer();
		POSTagger tagger = new POSTagger();
		Stemmer stemm = new Stemmer();
		Logger log = new Logger();
		
		
		tagger.importAndCountCorpus();
		frequencyCountMatrix = new ArrayList<ArrayList<Integer>>();
		words = new HashMap<String, Integer>();
		
		for(int i=0;i<movies.size();i++){
			frequencyCountMatrix.add(new ArrayList<Integer>());
		}
		
	
		
		Iterator it1 = movies.keySet().iterator();
		int i =0;
		long startTime = System.nanoTime();
		String tmpTime;
		while(it1.hasNext()){
			long startTime2 = System.nanoTime();
			ArrayList<Review> currentListOfReview = movies.get(it1.next()).reviews;
			
			//loop through all reviews of a movie
			for(int j=0;j<currentListOfReview.size();j++){
				
				startTime = System.nanoTime();
				String[] tokens = token.splitTokens(currentListOfReview.get(i).getText());
				String[] pos = tagger.assignPosToWords(tokens);
				String[] currentListOfTokens = stemm.stem(tokens, pos);
				tmpTime = String.valueOf(System.nanoTime() - startTime);
				log.log(tmpTime, "stemm");
				
				
				//loop through all words of a review
				startTime = System.nanoTime();
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
				tmpTime = String.valueOf(System.nanoTime() - startTime);
				log.log(tmpTime, "loopTime");
			}
			i++;
			System.out.println(i);
			
		}
		
			
		return frequencyCountMatrix;
	}
	
	
	
	
	public ArrayList<ArrayList<Double>> measureSimilarity(ArrayList<ArrayList<Double>> countMatrixNormalized){
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();
		
		for(int i=0;i<countMatrixNormalized.size();i++){
			ArrayList<Double> rowi = new ArrayList<Double>();
			
			for(int j =0; j<countMatrixNormalized.size();j++){
				double sumAB=0;
				double sumA=0;
				double sumB=0;
				
				for(int k =0;k<countMatrixNormalized.get(i).size();k++){
					
					double a = countMatrixNormalized.get(i).get(k);
					double b = countMatrixNormalized.get(j).get(k);
					
					sumAB = sumAB + a*b;
					sumA = sumA + a*a;
					sumB = sumB + b*b;
				}
				
				rowi.add(sumAB/((Math.sqrt(sumA))*(Math.sqrt(sumB))));
			}
			
			similarityMatrix.add(rowi);
		}
		
		return similarityMatrix;
		
	}
	
	
	/*
	 * Finds the highest term frequency in the document at index
	 */
	public int maxFrequency(ArrayList<ArrayList<Integer>> frequencyCountMatrix, int index) {
		int max = -1;
		for (int i = 0; i < frequencyCountMatrix.size(); i++) {
			int tmp = frequencyCountMatrix.get(index).get(i);
			if (tmp > max) {
				max = tmp;
			}
		}
		return max;
	}
	
	/*
	 * Normalizes the Frequency Count Matrix using df idf.
	 */
	public ArrayList<ArrayList<Double>> normalize(ArrayList<ArrayList<Integer>> frequencyCountMatrix) {
		ArrayList<ArrayList<Double>> normalizedMatrix = new ArrayList<ArrayList<Double>>();
		int numberOfDocuments = frequencyCountMatrix.size();
		int numberOfWords = frequencyCountMatrix.get(0).size();
		
		// initialise matrix to be the same size as the original one
		for (int i = 0; i < numberOfDocuments; i++) {
			ArrayList<Double> counts = new ArrayList<Double>();
			for (int j = 0; j < numberOfWords; j++) {
				counts.add(0.0);
			}
			normalizedMatrix.add(counts);
		}
			
		// set normalized values
		for (Map.Entry<String, Integer> entry : words.entrySet())
		{
			int index = entry.getValue();
			String word = entry.getKey();
		    
		    for (int i = 0; i < numberOfDocuments; i++) {
		    	double oldValue = (double) frequencyCountMatrix.get(i).get(index);
		    	double max = (double) maxFrequency(frequencyCountMatrix, i);
		    	double normalized = oldValue / max;
		    	
		    	ArrayList<Double> tmpCounts = normalizedMatrix.get(i);
		    	tmpCounts.set(index, normalized);
		    	normalizedMatrix.set(i, tmpCounts);
		    }
		}
			
		return normalizedMatrix;
	}
	
	

}
