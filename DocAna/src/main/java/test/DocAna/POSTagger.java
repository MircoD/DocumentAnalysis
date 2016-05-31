package test.DocAna;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POSTagger {

	/**
	 * A Method for reading in the Brown Corpus and count the appearance of each
	 * pair of word/tag.
	 * 
	 * @return ArrayList of PosStructures containing the word, all the tags the
	 *         word appeared with(as a ArrayList) and a how often(as a
	 * 
	 * 
	 *         How it works: First a file from the corpus is opened. The scanner
	 *         reads the file line by line, breaking each line down to the
	 *         word/tag pairs. Then for all word/tag pairs the map of all
	 *         previous words gets  searched. If the word is not
	 *         in the map, a new entry for that word is made. If the word
	 *         already is in the list the map of tags for that word is
	 *         searched. If that specific word/tag pair has not been seen before
	 *         a new entry for that tag is made.
	 *         If 
	 * 
	 * 
	 */
	public Map<String, HashMap<String, PosStructure>>  importAndCountCorpus() {

		Map<String, HashMap<String, PosStructure>> mapOfAllWords = new HashMap<String, HashMap<String, PosStructure>>();
		ArrayList <String> test = new ArrayList<String>();

		try {

			File folder = new File(
					"E:/Studium/Informatik/DocumentAnalysis/brown");
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile()) {
					String path = "E:/Studium/Informatik/DocumentAnalysis/brown/"
							+ file.getName();
					System.out.println(file.getName());
					Scanner scanner = new Scanner(new File(path));
					

					String nMinus1Tag = "null";
					String nMinus2Tag = "null";	

					while (scanner.hasNextLine()) {
						// split to to get the word/tag pairs
						String[] splittedLineOfText = scanner.nextLine()
								.toLowerCase().split("\\s");


						for (int j = 0; j < splittedLineOfText.length; j++) {
							// split the word/tag pair
							// pairOfWordTag[0] = word
							// pairOfWordTag[1] = tag
							String[] pairOfWordTag = splittedLineOfText[j]
									.split("/");

							
							// to remove all empty ones
							if (pairOfWordTag.length == 2) {
								//remove the secondary part (-xyz) of the tag
								if(pairOfWordTag[0].compareTo("--")==0){
									
								} else {
									pairOfWordTag[1] = pairOfWordTag[1].split("-")[0];
									pairOfWordTag[1] = pairOfWordTag[1].split("\\+")[0];
								}
									
								// checks if the word already appeared.
								//if not it gets added to the map.
								if (mapOfAllWords.containsKey(pairOfWordTag[0])) {
									
									//checks if the tag for the word is in the word/tag map
									//if not in the map it gets added to it
									if(mapOfAllWords.get(pairOfWordTag[0]).containsKey(pairOfWordTag[1])){
										
										//checks if the tag combination of the n-1 and n-2 tags
										//for the word/tag combination exists.
										//if it exists the counter for the tag combination gets increased by one.
										//otherwise it gets added to the list of combinations.
										if(mapOfAllWords.get(pairOfWordTag[0]).get(pairOfWordTag[1]).containsCombination(nMinus1Tag, nMinus2Tag)){
											
											
										} else{
											
											mapOfAllWords.get(pairOfWordTag[0]).get(pairOfWordTag[1]).addCombination(nMinus1Tag, nMinus2Tag);;
										}
										
									} else {
										mapOfAllWords.get(pairOfWordTag[0]).put(pairOfWordTag[1],new PosStructure(nMinus1Tag,nMinus2Tag));

									}
									
								} else{
									HashMap<String, PosStructure> tmpMap = new HashMap<String, PosStructure>();
									tmpMap.put(pairOfWordTag[1],new PosStructure(nMinus1Tag,nMinus2Tag));
									mapOfAllWords.put(pairOfWordTag[0],tmpMap);
								
								}

								nMinus2Tag = nMinus1Tag;
								nMinus1Tag = pairOfWordTag[1];
							}

						}

					}
					scanner.close();
				}
			}

		} catch (IOException e) {
			System.out.println("Accessing corpus failed: " + e);
		}

		System.out.println(test.toString());
		return null;

	}

	/**
	 * @param A
	 *            array list of PosStructures.
	 * @return A map, where the key is a a word and the value is the most used
	 *         tag for that word
	 * 
	 */

	
	

	/**
	 * 
	 * @param A
	 *            string[] containing tokenized words, a map of words and the
	 *            tags they appear with most often
	 * @return A string[] containing the most likely pos for the word at the
	 *         param string[] position
	 * 
	 */
	public String[] AssignPosToWords(String[] text, Map<String, String> map) {

		for (int i = 0; i < text.length; i++) {
			if (map.containsKey(text[i])) {
				text[i] = map.get(text[i]);
			} else {
				text[i] = "np";
			}

		}

		return text;
	}

}
