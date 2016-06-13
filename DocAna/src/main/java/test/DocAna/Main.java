package test.DocAna;

import java.util.ArrayList;

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

		// POSTagger tagger = new POSTagger();
		// tagger.importAndCountCorpus();
		// Gui gui = new Gui(tagger);

		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		ArrayList<Movies> listOfMovies = new ArrayList<Movies>();
		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();
		
		Reader reader = new Reader();
		Similarity similarity = new Similarity();
		listOfReviews = reader
				.readAndClear("e://Downloads/docAnaTextSample.rtf");

		System.out.println("reader done");
		
		
		for (int i = 0; i < listOfReviews.size(); i++) {
			
			boolean newMovie =true;

			for (int j = 0; j < listOfMovies.size() && newMovie; j++) {
				if (listOfMovies.get(j).movieID.compareTo(listOfReviews.get(i).getProductId()) == 0) {
					Movies tmpMovie = listOfMovies.get(j);
					ArrayList<Review> tmp = tmpMovie.getReviews();
					tmp.add(listOfReviews.get(i));
					tmpMovie.setReviews(tmp);
					listOfMovies.set(j, tmpMovie);	
					newMovie=false;
				}
			}
			
			if(newMovie){				
				listOfMovies.add(new Movies(listOfReviews.get(i).getProductId(), listOfReviews.get(i) ));
			}
		}
			
		
		frequencyCountMatrix = similarity.countTermFrequency(listOfMovies);
		frequencyCountMatrixNormalized = similarity.normalize(frequencyCountMatrix);
		similarityMatrix = similarity.measureSimilarity(frequencyCountMatrixNormalized);

	}
}
