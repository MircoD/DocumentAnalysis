package excercise2;

import java.util.Arrays;

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
	 * @param String
	 *            which is split into tokens
	 * @return String [] containing the tokens
	 * 
	 * 
	 *         First the text is turned to lower case. Then all contractions get
	 *         turned into the longform and the genitiv's gets removed. As a
	 *         last step all multiple whitespaces are reduced to one and the
	 *         text gets split at the whitespaces.
	 * 
	 */
	public String[] splitTokens(String text) {

		text = text.toLowerCase().replace("i'm", "i am")
				.replace("he's", "he is").replace("she's", "she is")
				.replace("it's", "it is").replace("'re", " are")
				.replace("wasn't", "was not").replace("weren't", "were not")
				.replace("don't", "do not").replace("doesn't", "does not")
				.replace("didn't", "did not").replace("didn't", "did not")
				.replace("hasn't", "has not").replace("hadn't", "had not")
				.replace("haven't", "have not").replace("'ve", " have")
				.replace("can't", "can not").replace("couldn't", "could not")
				.replace("musn't", "must not").replace("shan't", "shall not")
				.replace("shouldn't", "should not").replace("'ll", " will")
				.replace("wouldn't", "would not").replace("'d", " would")
				.replace("let's", "let us").replace("'s", "")
				.replaceAll("\\W", " ").replaceAll("\\s+", " ");

		return text.split("\\s");
	}
}
