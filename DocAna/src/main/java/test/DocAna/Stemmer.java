package test.DocAna;

import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;

/**
 * todo:
 * add -ion
 * add -n past tense verbs(known)
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
	public String[] stem(String[] tokens, String[] pos) {

		IDictionary dict = null;
		String [] stemmd = new String[tokens.length];

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
				stemmd [i] = "good";
			} else if (tokens[i].matches("worse|worst")) {
				stemmd[i] = "bad";
			} else if (tokens[i].matches("does|did")) {
				stemmd[i] = "do";
			} else if (tokens[i].matches("has|had")) {
				stemmd[i] = "have";
			} else if (tokens[i].matches("are|am|is|were|was")) {
				stemmd[i] = "be";
				// to avoid nullpointers when checking the last two chars of a
				// string
			} else if (tokens[i].length() > 2) {

				// Suffix -s; plural, third person
				if (tokens[i].endsWith("s")
						&& (pos[i].compareTo("nps") == 0
								|| pos[i].compareTo("nns") == 0 
								|| pos[i].compareTo("nps$") == 0 
								|| pos[i].compareTo("ppls") == 0 
								|| pos[i].compareTo("vbz") == 0)) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 1);
					String tmpie = tmp;

					// for exceptions e.g. applies
					if (tmp.endsWith("ie")) {
						tmpie = tmp.substring(0, tokens[i].length() - 2);
						tmpie = tmp + "y";
					}
						
					if (existsNoun(dict, tmpie)
							|| existsVerb(dict, tmpie)) {
						stemmd [i] = tmpie;

					} else {
						stemmd [i]=tmp;
					}
				}

				// Suffix -ness; noun from adjective/adverb
				else if (tokens[i].matches("[A-Za-z\\-]+ness")) {
					String tmp = tokens[i].replaceAll("ness(?=\\s+|$)", "");
					String tmpe = tmp + "e";

					// cases like manliness -> manly
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}

					// check whether tmp exists as a word
					if (existsAdjective(dict, tmpe) || existsAdverb(dict, tmpe)) {
						stemmd[i] = tmpe;

					} else {
						stemmd[i]=tmp;
					}
				}

				// Suffix -man/-men;
				else if (tokens[i].endsWith("men")) {
					if (tokens[i].length() != 3) {
						stemmd[i] = tokens[i].substring(0,
								tokens[i].length() - 3) + "man";
					} else {
						stemmd[i] = "man";
					}
				}

				// Suffix -er ; comparative, noun from verb(e.g. keeper-> keep)
				else if (tokens[i].matches("[A-Za-z\\-]+er")
						&& (pos[i].compareTo("jjr") == 0
						|| pos[i].compareTo("rbr") == 0
						|| pos[i].compareTo("nn") == 0 
						|| pos[i].compareTo("nn$") == 0 
						|| pos[i].compareTo("np") == 0
						|| pos[i].compareTo("np$") == 0)) {
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
						stemmd[i] = tmpe;

					} else {
						stemmd[i]=tmp;
					}
				}
				// Suffix -ly; adverb from adjective, adverb from noun
				else if (tokens[i].matches("[A-Za-z\\-]+ly") && pos[i].compareTo("rb") == 0) {
					String tmp = tokens[i].replaceAll("ly(?=\\s+|$)", "");

					// cases like happily -> happy
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}
					
					stemmd[i]=tmp;
				}


				
				// Suffix -y; adverb from adjective/noun
				else if (tokens[i].matches("[A-Za-z\\-]+y") && pos[i].compareTo("rb") == 0) {
					String tmp = tokens[i].replaceAll("y(?=\\s+|$)", "");

					// check if the last two chars are the same(e.g. foggy)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}

					stemmd[i]=tmp;
				}

			
				// Suffix -ed; past tense, adjective from noun
				else if (tokens[i].endsWith("ed") 
						&& (pos[i].compareTo("vbd") == 0 
						|| pos[i].compareTo("vbn") == 0 
						|| pos[i].compareTo("jj") == 0)) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 2);
					String tmpe = tmp + "e";
					if (tmp.matches("[A-Za-z\\-]+i")) {
						tmp = tmp.replaceAll("i(?=\\s+|$)", "y");
					}

					// check whether tmp exists as a word
					if(existsNoun(dict, tmpe) || existsVerb(dict, tmpe)){
						stemmd[i] = tmpe;
					} else {
						stemmd[i]=tmp;
					}
				}

				
				
				// Suffix -en; past tense, verb from adjective
				else if (tokens[i].endsWith("en") && tokens[i].length() > 3) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 2);
					String tmpe = tmp + "e";

					// check if the last two chars are the same(e.g. fatten)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}

					// check whether tmp or tmpe exists as a word
					if (existsAdjective(dict, tmpe)
							|| existsVerb(dict, tmpe)) {
						stemmd[i] = tmpe;

					} else {
						stemmd[i]=tmp;
					}
				}

				// Suffix -ing; gerund, present particle
				else if (tokens[i].endsWith("ing") 
						&& tokens[i].length() > 4 
						&& pos[i].compareTo("vbg") == 0) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 3);

					// check if the last two chars are the same(e.g. running)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						String tmpNoDouble = tmp.substring(0, tmp.length() - 1);
					}

					// in case of ending -e(e.g. making->make)
					String tmpe = tmp + "e";

					// check whether tmp exists as a word
					if (existsVerb(dict, tmpe)) {
						stemmd[i] = tmpe;

					} else {
						stemmd[i]=tmp;
					}
				}

				// Suffix -est; superlativ
				else if (tokens[i].endsWith("est")
						&& tokens[i].length() > 4 
						&& (pos[i].compareTo("rbt") == 0 
						||pos[i].compareTo("jjt") == 0)) {
					String tmp = tokens[i].substring(0, tokens[i].length() - 3);

					// check if the last two chars are the same(e.g. hottest)
					if (tmp.charAt(tmp.length() - 1) == tmp
							.charAt(tmp.length() - 2)) {
						tmp = tmp.substring(0, tmp.length() - 1);
					}
					stemmd[i]=tmp;	
				} else{
					stemmd[i]=tokens[i];
				}
			
				

			}
		}
		return stemmd;
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