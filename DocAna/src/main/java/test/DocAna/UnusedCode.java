package test.DocAna;

import java.util.ArrayList;
import java.util.Arrays;

public class UnusedCode {


	public void createFilesOfReviews(ArrayList<Review> listOfReviews){
		Logger log = new Logger();

		long startTime =0;
		
		startTime = System.nanoTime();
		for(int i =0; i<listOfReviews.size();i++){
			Review curReview = listOfReviews.get(i);
			
			String productId = curReview.getProductId();
			String userId = curReview.getUserId();
			String profileName = curReview.getProfileName();
			int helpfulness_denom = curReview.getHelpfulness_denom();
			int helpfulness_enum = curReview.getHelpfulness_enum();
			int score = curReview.getScore();
			long time = curReview.getTime();
			String summary = curReview.getSummary();
			String text = curReview.getText();

			
			log.log(productId, "listOfReviews");
			log.log(userId, "listOfReviews");
			log.log(profileName, "listOfReviews");
			log.log(String.valueOf(helpfulness_denom), "listOfReviews");
			log.log(String.valueOf(helpfulness_enum), "listOfReviews");
			log.log(String.valueOf(score), "listOfReviews");
			log.log(String.valueOf(time), "listOfReviews");
			log.log(summary, "listOfReviews");
			log.log(text, "listOfReviews");
			log.log(" ", "listOfReviews");		
		}
	}
	
	
	public void createFilesOfReviewsFromMovies(ArrayList<Movies> listOfMovies){
		Tokenizer token = new Tokenizer();
		POSTagger tagger = new POSTagger();
		Stemmer stemm = new Stemmer();
		Logger log = new Logger();
		tagger.importAndCountCorpus();
		

		long startTime =0;		
		String tmp;
		
		for(int i =0; i<listOfMovies.size();i++){
			Movies curMovie = listOfMovies.get(i);
			
			for(int k=0; k<curMovie.getReviews().size();k++){
				Review curReview = curMovie.getReviews().get(k);
				
			String productId = curReview.getProductId();
			String userId = curReview.getUserId();
			String profileName = curReview.getProfileName();
			int helpfulness_denom = curReview.getHelpfulness_denom();
			int helpfulness_enum = curReview.getHelpfulness_enum();
			int score = curReview.getScore();
			long time = curReview.getTime();
			String summary = curReview.getSummary();
			String text = curReview.getText();
			
			startTime = System.nanoTime();
			String[] sentences = token.splitSentencesAlt(text);
			tmp = String.valueOf(((System.nanoTime() - startTime)/1000000000.0));
			System.out.println(Arrays.toString(sentences));
			System.out.println("split:" + tmp);
			
			
			log.log(productId, "listOfReviews");
			log.log(userId, "listOfReviews");
			log.log(profileName, "listOfReviews");
			log.log(String.valueOf(helpfulness_denom), "listOfReviews");
			log.log(String.valueOf(helpfulness_enum), "listOfReviews");
			log.log(String.valueOf(score), "listOfReviews");
			log.log(String.valueOf(time), "listOfReviews");
			log.log(summary, "listOfReviews");
			log.log(text, "listOfReviews");
			log.log(Arrays.toString(sentences), "listOfReviews");
			log.log(" ", "listOfReviews");
			}
		}
	}
	
	
	public void compareTwoReviews(String movie1,String movie2,POSTagger tag){
		AuthorStatistics stats = new AuthorStatistics();
		Similarity similarity = new Similarity();
		
		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();
		
		ArrayList<Movies> movies = new ArrayList<Movies>();
		Movies mov1 = new Movies(movie1);
		Movies mov2 = new Movies(movie2);
		mov1.setStats(stats.gatherStats(mov1.getReviews(), tag, "a"));
		mov2.setStats(stats.gatherStats(mov2.getReviews(), tag, "a"));
		
		movies.add(mov1);
		movies.add(mov2);
	
		frequencyCountMatrix = similarity.countTermFrequency(movies, tag, "np");
		frequencyCountMatrixNormalized = similarity.normalise(frequencyCountMatrix);
		similarityMatrix = similarity.measureSimilarity(frequencyCountMatrixNormalized);
		System.out.println();
		
	}

}
