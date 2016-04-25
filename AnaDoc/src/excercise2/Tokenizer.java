package excercise2;


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

	public String[] splitTokens(String text) {
		text = text.replaceAll("\\W", " ").replaceAll("\\s+"," ");
		return text.split("\\s");

	}
}
