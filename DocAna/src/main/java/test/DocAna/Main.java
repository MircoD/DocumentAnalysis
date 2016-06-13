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
	
		// Gui gui = new Gui(tagger);

		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		ArrayList<Movies> listOfMovies = new ArrayList<Movies>();
		ArrayList<ArrayList<Integer>> frequencyCountMatrix;
		ArrayList<ArrayList<Double>> frequencyCountMatrixNormalized;
		ArrayList<ArrayList<Double>> similarityMatrix = new ArrayList<ArrayList<Double>>();
		String[] pos;
		
		Logger log = new Logger();
		POSTagger tagger = new POSTagger();
		Reader reader = new Reader();
		Similarity similarity = new Similarity();
		Stemmer stemmer = new Stemmer();
		
		tagger.importAndCountCorpus();
		listOfReviews = reader
				.readAndClear("c://docAnaTextSample.rtf");

		System.out.println("reader done");
		
		for (int i = 0; i < listOfReviews.size(); i++) {
			pos = tagger.assignPosToWords(listOfReviews.get(i).getText());
			Review tmp = listOfReviews.get(i);
			tmp.setText(stemmer.stem(listOfReviews.get(i).getText(), pos));	
			listOfReviews.set(i,tmp);
		}
		System.out.println("stemmer done");
		
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
		System.out.println("fq matrix done");
		
		frequencyCountMatrixNormalized = similarity.normalize(frequencyCountMatrix);
		System.out.println("fq matrix normalized done");
		
		similarityMatrix = similarity.measureSimilarity(frequencyCountMatrixNormalized);
		for(int i=0;i<similarityMatrix.size();i++){
			log.log(listOfMovies.get(i).getMovieID() + " " + similarityMatrix.get(i).toString(), "wololo");
			
		}
		

	}
}
