package excercise3;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class Stemmer {

	public void testDictionary() throws IOException {
		// construct the URL to the Wordnet dictionary directory
		String path = "C:/Users/Maurice/Desktop/Studium/6. Semester/CMDA/wn3.1.dict/dict";
		URL url = new URL("file", null, path);

		// construct the dictionary object and open it
		IDictionary dict = new Dictionary(url);
		dict.open();

		// look up first sense of the word "dog "
		IIndexWord idxWord = dict.getIndexWord("dog", POS.VERB);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		System.out.println("Id = " + wordID);
		System.out.println("Lemma = " + word.getLemma());
		System.out.println("Gloss = " + word.getSynset().getGloss());

	}

	public String[] stem(String[] tokens) {

		for (int i = 0; i < tokens.length; i++) {

			// Suffix -s
			if (tokens[i].matches("[A-Z,a-z]+s")) {
				tokens[i] = tokens[i].replaceAll("s(?=\\s+|$)", "");
				i--;
			}

			// Suffix -er
			if (tokens[i].matches("[A-Z,a-z]+er")) {
				tokens[i] = tokens[i].replaceAll("er(?=\\s+|$)", "e");
				i--;
			}

			// etc., additionally add dictionary queries

		}


		return tokens;
	}

}
