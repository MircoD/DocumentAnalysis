package excercise2;

import excercise1.Logger;

public class Tokenizer {

	/**
	 * Splits a string into sentences Does not recognize whether an abbreviation
	 * is the end of a sentence.
	 * 
	 * @param String
	 *            containing the text to be split into sentences
	 * @return String Array containing the sentences
	 */
	public String[] splitSentences(String text) {

		// common abbreviations, including one character abbreviations (e.g.,
		// T.J., Mr. S.,...)
		String abbreviations = "(etc|approx|Mr|Jr|(\\s\\w)|(\\w\\.\\w\\.)|cf|ref|Attn|dept|est|Yrs)";
		// match dots not used in an abbreviation or ?s or !s followed by an
		// uppercase letter
		String regex = "(((?<!" + abbreviations
				+ ")(\\.+))|(\\?+)|(\\!+))(?=((\\s*)[A-Z])|\\n)";

		String[] sentences = text.split(regex);
		String[] result = new String[sentences.length];

		// remove unnecessary whitespaces
		for (int i = 0; i < sentences.length; i++) {
			// also remove all .,?,! for persistency since our regex already
			// consumes some of them
			result[i] = sentences[i].trim().replaceAll("\\.|\\?|\\!", "");
		}

		return result;
	}

	/**
	 * @param String which is split into tokens
	 * @return String [] containing the tokens
	 * 
	 * 
	 * First the text is turned to lower case.
	 * Then negation abbreviations are "cleaned" by removing the ' in the middle.
	 * Then all non-character are turned into character to prevent token in quotes, with full stops, etc.
	 * As a last step all multiple whitespaces are reduced to one and the text gets split at the whitespaces.
	 * 
	 */
	public String[] splitTokens(String text) {
		text = text.toLowerCase().replace("don't", "dont")
				.replace("can't", "cant").replace("wouldn't", "wouldnt")
				.replace("shouldn't", "shouldnt").replace("won't", "wont")
				.replace("haven't", "havent").replace("hasn't", "hasnt")
				.replace("mustn't", "mustnt").replace("doesn't", "doesnt")
				.replace("aren't", "arent").replace("isn't", "isnt")
				.replaceAll("\\W", " ").replaceAll("\\s+", " ");
		return text.split("\\s");

	}
}
