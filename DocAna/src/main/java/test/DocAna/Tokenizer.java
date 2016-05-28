package test.DocAna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		// common abbreviations, including one character abbreviations
		String abbreviations = "(\\s(etc|approx|Mr|Mrs|Jr|\\w|((\\w\\.){1,5}\\w)|cf|ref|"
				+ "Attn|dept|Dept|Univ|Prof|Dr|est|Yrs|Yr|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|"
				+ "Oct|Nov|Dec|def|prob))";

		String splitRegex = "((?<!" + abbreviations + ")(\\.+)(?!(\\d+)))|(\\?+)|(\\!+)";
		
		String[] sentences = text.split(splitRegex);
		
		for (int i = 0; i < sentences.length; i++) {
			sentences[i] = sentences[i].trim();
		}

		return sentences;
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
				.replace("wouldn't", "would not").replace("'d", " would").replace("there's", "there is")
				.replace("let's", "let us").replace("'s", "")
				.replaceAll("\\W", " ").replaceAll("\\s+", " ");

		return text.split("\\s");
	}
}
