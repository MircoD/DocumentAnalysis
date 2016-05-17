package excercise4;

import java.util.ArrayList;
import java.util.List;

/*
 * -->!!README!!<--
 * 
 * Unfortunately, we were not able to integrate the Kumo-Library. Apparently,
 * you need Maven for this, and we couldn't get it working. But we decided
 * to do the exercise anyway, so please see the code below.
 * 
 */
public class WordCloudGenerator {

	/*
	 * Create a circular word cloud and save it to a file.
	 * 
	 * @param words - List of words
	 * @param name - Name of the resulting file (without extensions)
	 */
	public static void createWordCloud(List<String> words, String name) {
		
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(words);
		final Dimension dimension = new Dimension(600, 600);
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(300));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("output/" + name + ".png");	
	}


	/*
	 * Filter an array of tokens by their POS.
	 * 
	 * @param tokens - array of tokens to be filtered
	 * 
	 * @param pos - the pos of tokens
	 * 
	 * @param filter - the pos to filter by (e.g. "adjective")
	 * 
	 * @return List of tokens with a pos contained in filter
	 */
	public static List<String> filterByPos(String[] tokens, String[] pos, String[] filter) {
		List<String> result = new ArrayList<String>();
		
		for (int i = 0; i < tokens.length; i++)  {
			for (int j = 0; j < filter.length; j++) {
				if (pos[i].equals(filter[j])) {
					// if pos of the token is in filter add it
					result.add(tokens[i]);
					break;
				}
			}
		}
		
		return result;
	}
}