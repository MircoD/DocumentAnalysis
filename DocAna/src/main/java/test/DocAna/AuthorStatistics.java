package test.DocAna;

import java.util.ArrayList;

public class AuthorStatistics {

	public void gatherStats(ArrayList<Authors> listOfAuthors) {
		Tokenizer token = new Tokenizer();
		POSTagger tagger = new POSTagger();
		Stemmer stemm = new Stemmer();
		Logger log = new Logger();
		ArrayList<Authors> listOfAuthorsWithStats = new ArrayList<Authors>();
		tagger.importAndCountCorpus();
		
		for (int i = 0; i < listOfAuthors.size(); i++) {
			Authors author = listOfAuthors.get(i);
			
			double sumWords = 0;
			double sumChars = 0;
			double sumfunctionWords = 0;

			for (int j = 0; j < author.getReviews().size(); j++) {
				
				String[] tokens = token.splitTokens(author.getReviews().get(j)
						.getText());
				String[] pos = tagger.assignPosToWords(tokens);
				String[] stem = stemm.stem(tokens, pos);
				for (int k = 0; k < tokens.length; k++) {

					sumWords++;
					sumChars = sumChars + tokens[k].length();
					String curpos = pos[k];

					if (curpos.compareTo("cc") == 0
							|| curpos.compareTo("at") == 0
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
			

			author.setAvgWordLength(sumChars/sumWords);
			author.setAvgfunctionWords(sumfunctionWords/sumWords);
			author.setWords(sumWords);
			String tmp1= author.getAuthorID();
			String tmp2= String.valueOf(author.getReviews().size());
			String tmp3= String.valueOf(author.getWords()/author.getReviews().size());
			String tmp4= String.valueOf(author.getAvgWordLength());
			String tmp5= String.valueOf(author.getAvgfunctionWords());
			log.log(tmp1 + " " + tmp2 + " " + tmp3 +" "+ tmp4 + " " + tmp5, "auth");

		}
		

	}
}
