package test.DocAna;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;

/**
 * 
 * 
 * 
 */
public class Stemmer {

	/**
	 * A method that implements a simple stemmer. For every rule it will check
	 * whether the token has the suffix and if so it will remove it and save it
	 * in the String tmp. For some rules there is an additional Variable(tmpie
	 * or tmpe) that saves irregular forms. Also for some rules there is an
	 * additional check to handle irregular words(e.g. applies ->apply). Then it
	 * gets checked if the stemmed word exists in a certain word class. The
	 * classes checked depend on the stemming rule.
	 * 
	 * @param String
	 *            [] with tokens that should be stemmed. They need to be all
	 *            lower case and cleaned of any non-character symbols
	 * @return String [] with corresponding stemmed tokens
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

			// a few exceptions that have frequent occurrences and are irregular
			if (tokens[i].matches("better|best")) {
				tokens[i] = "good";
			} else if (tokens[i].matches("worse|worst")) {
				tokens[i] = "bad";
			} else if (tokens[i].matches("does|did")) {
				tokens[i] = "do";
			} else if (tokens[i].matches("has|had")) {
				tokens[i] = "have";
			} else if (tokens[i].matches("are|am|is|were|was")) {
				tokens[i] = "be";
				// to avoid nullpointers when checking the last two chars of a
				// string
			} else if (tokens[i].length() > 2) {

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
						 
					} else if (existsNoun(dict, tmpie)
							|| existsVerb(dict, tmpie)) {
						tokens[i] = tmpie;
						 
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
						 
					}
				}

				// Suffix -man/-men;
				if (tokens[i].endsWith("men")) {
					if (tokens[i].length() != 3) {
						tokens[i] = tokens[i].substring(0,
								tokens[i].length() - 3) + "man";
					} else {
						tokens[i] = "man";
					}
				}
				
				

				// Suffix -er ; comparative, noun from verb(e.g. keeper-> keep)
				if (tokens[i].matches("[A-Za-z\\-]+er")) {
					String tmp = tokens[i].replaceAll("er(?=\\s+|$)", "");
					// in case of ending -e(e.g. computer->compute)
					String tmpe = tmp + "e";

					// check if the last two chars are the same(e.g. hotter)
					if (tmp.length() > 2
							&& tmp.charAt(tmp.length() - 1) == tmp.charAt(tmp
									.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);

						// check if last char is i(e.g. happier)
					} else if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}

					if (existsAdverb(dict, tmpe) || existsAdjective(dict, tmpe)
							|| existsVerb(dict, tmpe)) {
						tokens[i] = tmpe;
						 
					} else if (existsAdverb(dict, tmp)
							|| existsAdjective(dict, tmp)
							|| existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						 
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
						 
					}

				}

				// Suffix -ed; past tense, adjective from noun
				if (tokens[i].endsWith("ed")) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 2);
					
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}


					// check whether tmp exists as a word
					if (existsNoun(dict, tmp) || existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						 
					}
				}

				// Suffix -en; past tense, verb from adjective
				if (tokens[i].endsWith("en") & tokens[i].length() > 3) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 2);
					String tmpe = tmp + "e";

					// check if the last two chars are the same(e.g. fatten)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}

					// check whether tmp or tmpe exists as a word
					if (existsAdjective(dict, tmp) || existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						 
					} else if (existsAdjective(dict, tmpe)
							|| existsVerb(dict, tmpe)) {
						tokens[i] = tmpe;
						 
					}
				}

				// Suffix -ing; gerund, present particle
				if (tokens[i].endsWith("ing") & tokens[i].length() > 4) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 3);

					// check if the last two chars are the same(e.g. running)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						String tmpNoDouble = tmp.substring(0, tmp.length() - 1);
					}

					// in case of ending -e(e.g. making->make)
					String tmpe = tmp + "e";

					// check whether tmp exists as a word
					if (existsVerb(dict, tmp)) {
						tokens[i] = tmp;
						 
					} else if (existsVerb(dict, tmpe)) {
						tokens[i] = tmpe;
						 
					}
				}

				// Suffix -est; superlativ
				if (tokens[i].endsWith("est") & tokens[i].length() > 4) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 3);

					// check if the last two chars are the same(e.g. hottest)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}

					// check whether tmp exists as a word
					if (existsAdjective(dict, tmp) || existsAdverb(dict, tmp)) {
						tokens[i] = tmp;
						 
					}
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
		return (dict.getIndexWord(word, POS.VERB) != null);
	}

	private boolean existsAdjective(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.ADJECTIVE) != null);
	}

	private boolean existsAdverb(IDictionary dict, String word) {
		return (dict.getIndexWord(word, POS.ADVERB) != null);
	}

}