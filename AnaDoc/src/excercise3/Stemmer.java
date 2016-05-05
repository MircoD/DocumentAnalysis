package excercise3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.*;

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

	/*
	 * --------------------------------------------------------
	 * TO DO: SOME MORE RULES + IRREGULARS LIKE 'DOES' -> 'DO'
	 * --------------------------------------------------------
	 * 
	 * Already covers cases like the following (use for testing):
	 * 
	 * Stemmer stemmer = new Stemmer(); String[] tokens = { "gladly",
	 * "computer", "windy", "dusty", "stronger", "merciless", "foggy",
	 * "surely", "happily"}; tokens = stemmer.stem(tokens);
	 */
	public String[] stem(String[] tokens) {

		IDictionary dict = null;

		try {
			// construct the URL to the Wordnet dictionary directory
			String path = "C:/Users/Maurice/Desktop/Studium/6. Semester/CMDA/wn3.1.dict/dict";
			URL url = new URL("file", null, path);

			// construct the dictionary object and open it
			dict = new Dictionary(url);
			dict.open();
		} catch (IOException e) {
			System.out.println("Accessing dictionary failed: " + e);
			return tokens;
		}

		for (int i = 0; i < tokens.length; i++) {

			// Suffix -less
			if (tokens[i].matches("[A-Za-z\\-]+less")) {
				String tmp = tokens[i].replaceAll("less(?=\\s+|$)", "");
				// cases like mercy -> merciless
				if (tmp.matches("[A-Za-z\\-]+i")) {
					tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
				}
				// check whether tmp exists as a word
				if (exists(dict, tmp)) {
					tokens[i] = tmp;
					i--;
				}
			}

			// Suffix -ness
			if (tokens[i].matches("[A-Za-z\\-]+ness")) {
				String tmp = tokens[i].replaceAll("ness(?=\\s+|$)", "");
				// cases like manliness -> manly
				if (tmp.matches("[A-Za-z\\-]+i")) {
					tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
				}
				// check whether tmp exists as a word
				if (exists(dict, tmp)) {
					tokens[i] = tmp;
					i--;
				}
			}

			// Suffix -er ; Rule 1 (e.g. stronger -> strong)
			if (tokens[i].matches("[A-Za-z\\-]+er")) {
				String tmp = tokens[i].replaceAll("er(?=\\s+|$)", "");
				// check whether tmp exists as a word
				if (exists(dict, tmp)) {
					tokens[i] = tmp;
					i--;
				}
			}

			// Suffix -er ; Rule 2 (e.g. computer -> compute)
			if (tokens[i].matches("[A-Za-z\\-]+er")) {
				String tmp = tokens[i].replaceAll("er(?=\\s+|$)", "e");
				// check whether tmp exists as a word
				if (exists(dict, tmp)) {
					tokens[i] = tmp;
					i--;
				}
			}

			// Suffix -y
			if (tokens[i].matches("[A-Za-z\\-]+y")) {
				String tmp = tokens[i].replaceAll("y(?=\\s+|$)", "");
				// cases like foggy -> fog
				if (tmp.length() > 2) {
					tmp = tmp.substring(0, tmp.length() - 2)
							+ tmp.substring(tmp.length() - 2).replaceAll(
									"([A-Za-z\\-])\\1", "$1");
				}
				// check whether tmp exists as a word
				if (exists(dict, tmp)) {
					tokens[i] = tmp;
					i--;
				}
			}

			// Suffix -ly
			if (tokens[i].matches("[A-Za-z\\-]+ly")) {
				String tmp = tokens[i].replaceAll("ly(?=\\s+|$)", "");
				// cases like happily -> happy
				if (tmp.matches("[A-Za-z\\-]+i")) {
					tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
				}
				// check whether tmp exists as a word
				if (exists(dict, tmp)) {
					tokens[i] = tmp;
					i--;
				}
			}
		}

		return tokens;
	}

	/*
	 * Checks, whether the specified word can be found in the dictionary.
	 * 
	 * @param IDictionary - the dictionary to be used
	 * 
	 * @param String - the word to be checked
	 * 
	 * @return Boolean - whether the word could be found
	 */
	private boolean exists(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.NOUN) != null
				|| dict.getIndexWord(word, POS.VERB) != null
				|| dict.getIndexWord(word, POS.ADJECTIVE) != null || dict
					.getIndexWord(word, POS.ADVERB) != null);
	}
}
