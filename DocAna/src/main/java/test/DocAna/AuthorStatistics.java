package test.DocAna;

import java.util.ArrayList;

public class AuthorStatistics {
	
	Tokenizer token = new Tokenizer();
	Stemmer stemm = new Stemmer();
	


	public Stats gatherStats(ArrayList<Review> listOfReviews, POSTagger tagger) {
		Logger log = new Logger();
		String[] tokens;
		String[] pos;
		String[] stem;
		String[] sentences;

		double sumWords = 0;
		double sumSentences = 0;
		double sumChars = 0;
		double sumfunctionWords = 0;
		double sumCapitalLetter =0;

		for (int j = 0; j < listOfReviews.size(); j++) {
			
			

			tokens = token.splitTokens(listOfReviews.get(j).getText());
			pos = tagger.assignPosToWords(tokens);
			stem = stemm.stem(tokens, pos);
			sentences = token.splitSentencesAlt(listOfReviews.get(j).getText());
			
			
			
			double tmp=listOfReviews.get(j).getText().split("[A-Z]").length;
			if(tmp ==1){
			} else if(tmp ==2){
				sumCapitalLetter++;
				
			} else{
				sumCapitalLetter = tmp + sumCapitalLetter;
			}
			sumWords = tokens.length + sumWords;
			sumSentences = sumSentences + sentences.length;
			for (int k = 0; k < tokens.length; k++) {

				sumChars = sumChars + tokens[k].length();
				String curpos = pos[k];

				if (curpos.compareTo("cc") == 0 || curpos.compareTo("at") == 0
						|| curpos.compareTo("pn$") == 0
						|| curpos.compareTo("pp$") == 0
						|| curpos.compareTo("pp$$") == 0
						|| curpos.compareTo("ppl") == 0
						|| curpos.compareTo("ppls") == 0
						|| curpos.compareTo("ppo") == 0
						|| curpos.compareTo("pps") == 0
						|| curpos.compareTo("ppss") == 0
						|| curpos.compareTo("prp") == 0
						|| curpos.compareTo("prp$") == 0
						|| curpos.compareTo("in") == 0
						|| curpos.compareTo("pn") == 0
						|| curpos.compareTo("dtx") == 0
						|| curpos.compareTo("cs") == 0
						|| stem[k].compareTo("be") == 0
						|| stem[k].compareTo("have") == 0
						|| curpos.compareTo("md") == 0
						|| curpos.compareTo("uh") == 0
						|| curpos.compareTo("rp") == 0) {
					sumfunctionWords++;

				}

			}
		}

		double avgSentenceLength = sumWords / sumSentences;
		double avgCapitalLetter = sumCapitalLetter/sumChars;
		double avgWordLength = sumChars / sumWords;
		double avgWordsPerReview = sumWords / listOfReviews.size();
		double avgfunctionWords = sumfunctionWords / sumWords;


		return new Stats(avgSentenceLength, avgCapitalLetter, avgWordLength,
				avgWordsPerReview, avgfunctionWords);
	}
}
