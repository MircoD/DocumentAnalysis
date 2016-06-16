package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;

/*
 B002LBKDYE
 B004WO6BPS
 B009NQKPUW
 B000VBJEFK
 7883704540
 B0028OA3EY
 B0028OA3EO
 B008PZZND6
 B006TTC57C
 B002VL2PTU
 B001NFNFMQ
 B000067JG3
 B000067JG4
 B000MMMTAK
 B003DBEX6K
 B001TAFCBC
 B0039UTDFG
 B000KKQNRO
 B0002Y69NQ
 B005CA4SJW
 */
/**
 * Main class for the pipelining.
 * 
 */

public class Main {

	public static void main(String[] args) {

		// Gui gui = new Gui(tagger);

		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		HashMap<String, Movies> listOfMoviesFull = new HashMap<String, Movies>();
		ArrayList<Movies> listOfMoviesFilterd = new ArrayList<Movies>();
		HashMap<String, Authors> listOfAuthors = new HashMap<String, Authors>();
		ArrayList<Authors> listOfAuthorsFilterd = new ArrayList<Authors>();
		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();

		Reader reader = new Reader();
		Similarity similarity = new Similarity();
		Logger log = new Logger();
		Filter filter = new Filter();
		AuthorStatistics stats = new AuthorStatistics();

		long startTime = System.nanoTime();
		String tmpTime;
		listOfReviews = reader.readAndClear("c://docAnaTextSample.rtf");

		String tmp;
		tmp = String.valueOf(((System.nanoTime() - startTime)/1000000000.0));
		System.out.println("reading file:" + tmp);
		startTime = System.nanoTime();

		// adds the reviews to the listOfMovies and listOfAuthors
		for (int i = 0; i < listOfReviews.size(); i++) {

			if (listOfMoviesFull.containsKey(listOfReviews.get(i).getProductId())) {
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
		
		listOfMoviesFilterd = filter.moviesWithMinReviews(listOfMoviesFull, 30);
		listOfAuthorsFilterd = filter.authorsWithMinReviews(listOfAuthors, 210);
		listOfAuthorsFilterd = stats.gatherStats(listOfAuthorsFilterd);
		//System.out.println("create lists :" + ((System.nanoTime() - startTime)/1000000000.0));

		//startTime = System.nanoTime();
		//frequencyCountMatrix = similarity.countTermFrequency(listOfMoviesFilterd);		
		//System.out.println("fq matrix :" + ((System.nanoTime() - startTime)/1000000000.0));
		
		
		//startTime = System.nanoTime();
		//frequencyCountMatrixNormalized = similarity
		//		.normalize(frequencyCountMatrix);	
		//System.out.println("normal :" + ((System.nanoTime() - startTime)/1000000000.0));
		
		//startTime = System.nanoTime();
		//similarityMatrix = similarity
			//	.measureSimilarity(frequencyCountMatrixNormalized);
		
		//System.out.println("matrix :" + ((System.nanoTime() - startTime)/1000000000.0));
	
		for(int i=0;i<listOfAuthorsFilterd.size();i++){
			Authors author = listOfAuthorsFilterd.get(i);
			
			String tmp1= author.getAuthorID();
			String tmp2= String.valueOf(author.getReviews().size());
			String tmp3= String.valueOf(author.getWords()/author.getReviews().size());
			String tmp4= String.valueOf(author.getAvgWordLength());
			String tmp5= String.valueOf(author.getAvgfunctionWords());
			log.log(tmp1 + " " + tmp2 + " " + tmp3 +" "+ tmp4 + " " + tmp5, "auth");
		}
			
		
	}
	
	
}
