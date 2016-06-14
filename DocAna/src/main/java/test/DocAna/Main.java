package test.DocAna;

import java.util.ArrayList;
import java.util.List;

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
		ArrayList<ArrayList<String[]>> posList = new ArrayList<ArrayList<String[]>>();
		String[] pos;
		String[] filter ={"jj","rb"};
		
		WordCloudGenerator wordCloud = new WordCloudGenerator();
		Logger log = new Logger();
		POSTagger tagger = new POSTagger();
		Reader reader = new Reader();
		Similarity similarity = new Similarity();
		Stemmer stemmer = new Stemmer();

		tagger.importAndCountCorpus();
		listOfReviews = reader
				.readAndClear("c://docAnaTextSample.rtf");

		System.out.println("reader done");
	
		
		//adds the reviews to the listOfMovies
		for (int i = 0; i < listOfReviews.size(); i++) {
			pos = tagger.assignPosToWords(listOfReviews.get(i).getText());
			Review tmpRev = listOfReviews.get(i);
			tmpRev.setText(stemmer.stem(listOfReviews.get(i).getText(), pos));
			tmpRev.setPos(pos);
			listOfReviews.set(i,tmpRev);
			
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
				
		System.out.println("stemmer done");
		
		String[] tmpText = new String[0];
		String[] tmpPos = new String[0];
		for(int i=0;i<listOfMovies.get(1).getReviews().size();i++){
			Review tmpRev = listOfMovies.get(1).getReviews().get(i);
			tmpText = log.concat(tmpText, tmpRev.getText());	
			tmpPos = log.concat(tmpPos, tmpRev.getText());		
		}
		
		List<String> tmpList = wordCloud.filterByPos(tmpText, tmpPos, filter);
		wordCloud.createWordCloud(tmpList,"1");
		
		
		frequencyCountMatrix = similarity.countTermFrequency(listOfMovies);
		System.out.println("fq matrix done");
		
		frequencyCountMatrixNormalized = similarity.normalize(frequencyCountMatrix);
		System.out.println("fq matrix normalized done");
		
		similarityMatrix = similarity.measureSimilarity(frequencyCountMatrixNormalized);
		

	}
}
