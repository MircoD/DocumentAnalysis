package test.DocAna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	
	
	public ArrayList<Double> compareTwoReviews(String movie1,String movie2,POSTagger tag){
		AuthorStatistics stats = new AuthorStatistics();
		Similarity similarity = new Similarity();
		
		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> ret = new ArrayList<Double>();

		
		ArrayList<Movies> movies = new ArrayList<Movies>();
		Movies mov1 = new Movies(movie1);
		Movies mov2 = new Movies(movie2);
		mov1.setStats(stats.gatherStats(mov1.getReviews(), tag));
		mov2.setStats(stats.gatherStats(mov2.getReviews(), tag));
		
		movies.add(mov1);
		movies.add(mov2);
	
		frequencyCountMatrix = similarity.countTermFrequency(movies, tag, "np");
		frequencyCountMatrixNormalized = similarity.normalise(frequencyCountMatrix);
		similarityMatrix = similarity.measureSimilarity(frequencyCountMatrixNormalized);
		Stats stat1 = mov1.getStats();
		Stats stat2 = mov2.getStats();
		ret.add(stat1.getAvgSentenceLength()-stat2.getAvgSentenceLength());
		ret.add(stat1.getAvgCapitalLetter()-stat2.getAvgCapitalLetter());
		ret.add(stat1.getAvgWordLength()-stat2.getAvgWordLength());
		ret.add(stat1.getAvgWordsPerReview()-stat2.getAvgWordsPerReview());
		ret.add(stat1.getAvgfunctionWords()-stat2.getAvgfunctionWords());
		ret.add(similarityMatrix.get(0).get(1)-0.5);
		
		return ret;
	
	}

	public void oldMain(){
		
		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		HashMap<String, Movies> listOfMoviesFull = new HashMap<String, Movies>();
		ArrayList<Movies> listOfMoviesFilterd = new ArrayList<Movies>();
		HashMap<String, Authors> listOfAuthors = new HashMap<String, Authors>();
		ArrayList<Authors> listOfAuthorsFilterd = new ArrayList<Authors>();

		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();

		Tokenizer token = new Tokenizer();
		Reader reader = new Reader();
		Similarity similarity = new Similarity();
		Logger log = new Logger();
		Stemmer stemm = new Stemmer();
		Filter filter = new Filter();
		POSTagger tag = new POSTagger();
		AuthorStatistics stats = new AuthorStatistics();
	
		long startTime = System.nanoTime();
		listOfReviews = reader.readReviews("c://listOfReviews.txt");
		tag.importAndCountCorpus();

		String tmp;
		tmp = String.valueOf(((System.nanoTime() - startTime) / 1000000000.0));
		System.out.println("reading file:" + tmp);

		startTime = System.nanoTime();
		// adds the reviews to the listOfMovies and listOfAuthors
		for (int i = 0; i < listOfReviews.size(); i++) {

			if (listOfMoviesFull.containsKey(listOfReviews.get(i)
					.getProductId())) {
				listOfMoviesFull.get(listOfReviews.get(i).getProductId()).reviews
						.add(listOfReviews.get(i));

			} else {
				listOfMoviesFull.put(listOfReviews.get(i).getProductId(),
						new Movies(listOfReviews.get(i).getProductId(),
								listOfReviews.get(i)));
			}

			if (listOfAuthors.containsKey(listOfReviews.get(i).getUserId())) {
				listOfAuthors.get(listOfReviews.get(i).getUserId()).reviews
						.add(listOfReviews.get(i));

			} else {
				listOfAuthors.put(listOfReviews.get(i).getUserId(),
						new Authors(listOfReviews.get(i).getUserId(),
								listOfReviews.get(i)));
			}
		}


		System.out.println(listOfMoviesFull.size());

		listOfMoviesFilterd = filter.moviesWithMinReviews(listOfMoviesFull, 50,
				900);
		HashMap<String,Integer> wordlist = new HashMap<String, Integer>();
		HashMap<String,Integer> fivestar = new HashMap<String, Integer>();
		HashMap<String,Integer> onestar = new HashMap<String, Integer>();
		int fivestars=0;
		int onestars=0;
		
		System.out.println("author/movie list:"
				+ ((System.nanoTime() - startTime) / 1000000000.0) + " "
				+ listOfMoviesFilterd.size());
		startTime = System.nanoTime();
		
		
		for(int i=0;i<listOfMoviesFilterd.size();i++){
			
			for(int j=0;j<listOfMoviesFilterd.get(i).getReviews().size();j++){

				if(listOfMoviesFilterd.get(i).getReviews().get(j).getScore() == 2 || listOfMoviesFilterd.get(i).getReviews().get(j).getScore() == 1 ){
					onestars++;
				String[] tokenz =token.splitTokens(listOfMoviesFilterd.get(i).getReviews().get(j).getText());
				String[] pos = tag.assignPosToWords(tokenz);
				String[] stemma = stemm.stem(tokenz, pos);
				
				for(int k=0;k<stemma.length;k++){
					if(onestar.containsKey(stemma[k])){
						
						int tmpi = onestar.get(stemma[k]) +1;
						onestar.put(stemma[k], tmpi);
						
					}else{
						onestar.put(stemma[k], 1);
					}
					
					if(!fivestar.containsKey(stemma[k])){
						fivestar.put(stemma[k], 0);
					}

				}
				}
				
				if(listOfMoviesFilterd.get(i).getReviews().get(j).getScore() == 4 || listOfMoviesFilterd.get(i).getReviews().get(j).getScore() == 5 ){
					fivestars++;
				String[] tokenz =token.splitTokens(listOfMoviesFilterd.get(i).getReviews().get(j).getText());
				String[] pos = tag.assignPosToWords(tokenz);
				String[] stemma = stemm.stem(tokenz, pos);
				
				for(int k=0;k<stemma.length;k++){
					if(fivestar.containsKey(stemma[k])){
						
						int tmpi = fivestar.get(stemma[k]) +1;
						fivestar.put(stemma[k], tmpi);
						
					}else{
						fivestar.put(stemma[k], 1);
					}
					
					if(!onestar.containsKey(stemma[k])){
						onestar.put(stemma[k], 0);
					}

				}
				}
				
			}
			System.out.println("mov: "
					+ ((System.nanoTime() - startTime) / 1000000000.0) + " " + i);
			startTime = System.nanoTime();
			
		}
		
		
		for(String tmpk : fivestar.keySet()){
			double one = (double)onestar.get(tmpk) / (double)onestars;
			double five = (double)fivestar.get(tmpk) / (double)fivestars;
			double fo = five - one;
			
			log.log(tmpk + " " + five + " " + one + " " + fo + " " + five/one + " " + one/five, "wordlist");
		}

		
		
	}
	public void newMain(){
		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		HashMap<String, Movies> listOfMoviesFull = new HashMap<String, Movies>();
		ArrayList<Movies> listOfMoviesFilterd = new ArrayList<Movies>();
		HashMap<String, Authors> listOfAuthors = new HashMap<String, Authors>();
		ArrayList<Authors> listOfAuthorsFilterd = new ArrayList<Authors>();

		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();

		Tokenizer token = new Tokenizer();
		Reader reader = new Reader();
		Similarity similarity = new Similarity();
		Logger log = new Logger();
		Stemmer stemm = new Stemmer();
		Filter filter = new Filter();
		POSTagger tag = new POSTagger();
		AuthorStatistics stats = new AuthorStatistics();
	
		long startTime = System.nanoTime();
		listOfReviews = reader.readReviews("c://listOfReviews.txt");
		tag.importAndCountCorpus();

		String tmp;
		tmp = String.valueOf(((System.nanoTime() - startTime) / 1000000000.0));
		System.out.println("reading file:" + tmp);

		startTime = System.nanoTime();
		// adds the reviews to the listOfMovies and listOfAuthors
		for (int i = 0; i < listOfReviews.size(); i++) {

			if (listOfMoviesFull.containsKey(listOfReviews.get(i)
					.getProductId())) {
				listOfMoviesFull.get(listOfReviews.get(i).getProductId()).reviews
						.add(listOfReviews.get(i));

			} else {
				listOfMoviesFull.put(listOfReviews.get(i).getProductId(),
						new Movies(listOfReviews.get(i).getProductId(),
								listOfReviews.get(i)));
			}

			if (listOfAuthors.containsKey(listOfReviews.get(i).getUserId())) {
				listOfAuthors.get(listOfReviews.get(i).getUserId()).reviews
						.add(listOfReviews.get(i));

			} else {
				listOfAuthors.put(listOfReviews.get(i).getUserId(),
						new Authors(listOfReviews.get(i).getUserId(),
								listOfReviews.get(i)));
			}
		}


		System.out.println(listOfMoviesFull.size());

		listOfMoviesFilterd = filter.moviesWithMinReviews(listOfMoviesFull, 50,
				900);

		System.out.println("author/movie list:"
				+ ((System.nanoTime() - startTime) / 1000000000.0) + " "
				+ listOfMoviesFilterd.size());
		startTime = System.nanoTime();
		
		List<String> positive = new ArrayList<String>();
		List<Double> posWeight = new ArrayList<Double>();
		List<String> negative = new ArrayList<String>();
		List<Double> negWeight = new ArrayList<Double>();

		positive.add("excellent");
		posWeight.add(1.0);
		negative.add("bad");
		negWeight.add(1.0);
		
		Sentiment sent = new Sentiment(positive, negative, posWeight, negWeight, 1.0);
		
		List<Boolean> res1 = sent.getMoviesSentiment(listOfMoviesFilterd, false);
		List<Boolean> res1Improved = sent.getMoviesSentiment(listOfMoviesFilterd, true);
		List<ArrayList<Boolean>> res2 = sent.getReviewsSentiment(listOfMoviesFilterd, false);
		List<ArrayList<Boolean>> res2Improved = sent.getReviewsSentiment(listOfMoviesFilterd, true);
		System.out.println(res1.toString());
		
		
	}
	
}
