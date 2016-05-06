package excercise3;

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
		IIndexWord idxWord = dict.getIndexWord("foggy", POS.ADJECTIVE);
		IWordID wordID = idxWord.getWordIDs().get(0);
		IWord word = dict.getWord(wordID);
		System.out.println("Id = " + wordID);
		System.out.println("Lemma = " + word.getLemma());
		System.out.println("Gloss = " + word.getSynset().getGloss());

	}

	/*
	 * -------------------------------------------------------- TO DO: SOME MORE
	 * RULES + IRREGULARS LIKE 'DOES' -> 'DO'
	 * --------------------------------------------------------
	 * 
	 * Already covers cases like the following (use for testing):
	 * 
	 * Stemmer stemmer = new Stemmer(); String[] tokens = { "gladly",
	 * "computer", "windy", "dusty", "stronger", "merciless", "foggy", "surely",
	 * "happily"}; tokens = stemmer.stem(tokens);
	 */
	public String[] stem(String[] tokens) {

		IDictionary dict = null;
		boolean change = false;

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

		/*
		 * This loop goes through the String [] and applies
		 */
		for (int i = 0; i < tokens.length; i++) {

			// a few exceptions that make sense
			if (tokens[i].matches("[better]|[best]")) {
				tokens[i] = "good";
			} else if (tokens[i].matches("[worse]|[worst]")) {
				tokens[i] = "bad";
			} else if (tokens[i].matches("[does]|[did]")) {
				tokens[i] = "do";
			} else if (tokens[i].matches("[has]|[had]")) {
				tokens[i] = "have";

				// to avoid nullpointers when checking the last two chars of a
				// string
			} else if (tokens[i].length() > 1) {

				// Suffix -s; plural, third person
				if (tokens[i].endsWith("s")) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 1);
					String tmpie = tmp;
					// for exceptions e.g. applies
					if (tmp.endsWith("ie")) {
						tmpie = tmp.substring(0, tokens[i].length() - 2);
						tmpie = tmp + "y";
					}

					// check whether tmp or tmpie exists as a word
					if (existsNoun(dict, tmp) || existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					} else if (existsNoun(dict, tmpie)
							|| existsVerb(dict, tmpie)) {
						tokens[i] = tmpie;
						change = true;
					}
				}

				// Suffix -ness; noun from adjective/adverb
				if (tokens[i].matches("[A-Za-z\\-]+ness")) {
					String tmp = tokens[i].replaceAll("ness(?=\\s+|$)", "");
					// cases like manliness -> manly
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}
					// check whether tmp exists as a word
					if (existsAdjective(dict, tmp) || existsAdverb(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					}
				}

				// Suffix -less; adjective from noun
				if (tokens[i].matches("[A-Za-z\\-]+less")) {
					String tmp = tokens[i].replaceAll("less(?=\\s+|$)", "");
					// cases like mercy -> merciless
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}
					// check whether tmp exists as a word
					if (existsNoun(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					}
				}

				// Suffix -er ; comparative, noun from verb(e.g. keeper-> keep)
				if (tokens[i].matches("[A-Za-z\\-]+er")) {
					String tmp = tokens[i].replaceAll("er(?=\\s+|$)", "");
					// in case of ending -e(e.g. computer->compute)
					String tmpe = tmp + "e";

					// check if the last two chars are the same(e.g. hotter)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
						// check if last char is i(e.g. happier)
					} else if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}
					// check whether tmpe or tmp exists as a word
					if (existsAdverb(dict, tmpe) || existsAdjective(dict, tmpe)
							|| existsVerb(dict, tmpe)) {
						tokens[i] = tmp;
						change = true;
					} else if (existsAdverb(dict, tmp)
							|| existsAdjective(dict, tmp)
							|| existsVerb(dict, tmp)) {
						tokens[i] = tmpe;
						change = true;
					}
				}

				// Suffix -y; adverb from adjective/noun
				if (tokens[i].matches("[A-Za-z\\-]+y")) {
					String tmp = tokens[i].replaceAll("y(?=\\s+|$)", "");
					// check if the last two chars are the same(e.g. foggy)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}
					// check whether tmp exists as a word
					if (existsAdjective(dict, tmp) || existsNoun(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					}
				}

				// Suffix -ly; adverb from adjective, adverb from noun
				if (tokens[i].matches("[A-Za-z\\-]+ly")) {
					String tmp = tokens[i].replaceAll("ly(?=\\s+|$)", "");
					// cases like happily -> happy
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}
					// check whether tmp exists as a word
					if (existsAdjective(dict, tmp) || existsNoun(dict, tmp)) {
						tokens[i] = tmp;
						change = true;

					}

				}

				// Suffix -ed; past tense, adjective from noun
				if (tokens[i].endsWith("ed")) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 2);

					// check whether tmp exists as a word
					if (existsNoun(dict, tmp) || existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					}
				}

				// Suffix -ing; gerund, present particle
				if (tokens[i].endsWith("ing")) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 3);
					// check if the last two chars are the same(e.g. running)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}
					// in case of ending -e(e.g. making->make)
					String tmpe = tmp + "e";
					// check whether tmp exists as a word
					if (existsVerb(dict, tmpe)) {
						tokens[i] = tmp;
						change = true;
					} else if (existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					}
				}

				// Suffix -est; superlativ
				if (tokens[i].endsWith("ed")) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 3);
					// check if the last two chars are the same(e.g. hottest)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}

					// check whether tmp exists as a word
					if (existsAdjective(dict, tmp) || existsAdverb(dict, tmp)) {
						tokens[i] = tmp;
						change = true;
					}
				}

				// some words might need to more than once, so if a stemming was
				// used it will check the token again
				if (change = true) {
					i--;
				}
			}
		}
		return tokens;
	}

	/*
	 * Checks, whether the specified word of the specified POS can be found in
	 * the dictionary.
	 * 
	 * @param IDictionary - the dictionary to be used
	 * 
	 * @param String - the word to be checked
	 * 
	 * @return Boolean - whether the word could be found
	 */
	private boolean existsNoun(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.NOUN) != null);
	}

	private boolean existsVerb(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.VERB) != null
				|| dict.getIndexWord(word, POS.ADJECTIVE) != null || dict
					.getIndexWord(word, POS.ADVERB) != null);
	}

	private boolean existsAdjective(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.ADJECTIVE) != null);
	}

	private boolean existsAdverb(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.ADVERB) != null);
	}

}