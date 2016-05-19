package test.DocAna;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POSTagger {

	/** 
	 * A Method for reading in the Brown Corpus and count the appearance of each pair of word/tag.  
	 * 
	 * @return ArrayList of PosStructures containing the word, all the tags the
	 *         word appeared with(as a ArrayList) and a how often(as a
	 * 
	 *  
	 *   How it works:
	 *   First a file from the corpus is opened. The scanner reads the file line by line, breaking each line down to the word/tag pairs.
	 *   Then for all word/tag pairs the list of all previous words gets (inefficiently) searched.
	 *   If the word is not in the list, a new entry for that word is made.
	 *   If the word already is in the list the list of tags for that word is searched.
	 *   If that specific word/tag pair has not been seen before a new entry for that tag is made.
	 *   Otherwise the counter for the word/tag pair gets increased by one.
	 *         
	 *         
	 */
	public ArrayList<PosStructure> importAndCountCorpus() {

		ArrayList<PosStructure> listOfAllWords = new ArrayList<PosStructure>();

		try {

			File folder = new File("E:/Studium/Informatik/brown/");
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile()) {
					String path = "E:/Studium/Informatik/brown/"
							+ file.getName();
					System.out.println(file.getName());
					Scanner scanner = new Scanner(new File(path));
					
					while (scanner.hasNextLine()) {
						//split to to get the word/tag pairs
						String[] splittedLineOfText = scanner.nextLine().toLowerCase().split("\\s");

						
						for (int j = 0; j < splittedLineOfText.length; j++) {
							//split the word/tag pair
							//pairOfWordTag[0] = word
							//pairOfWordTag[1] = tag
							String[] pairOfWordTag = splittedLineOfText[j].split("/");

							//to remove all empty ones
							if (pairOfWordTag.length == 2) {
								int k = 0;
								boolean foundWord = false;
								
								
								//goes through the list of all previous words and checks if the word is already in the list.
								while (k < listOfAllWords.size() && foundWord == false) {
									if (0 == pairOfWordTag[0].compareTo(listOfAllWords.get(k).word)) {
										
										int l = 0;
										boolean foundTag = false;
										foundWord = true;
										
										
										//the word was found in the list
										//now the list of the tags for that word is searched
										while (l < listOfAllWords.get(k).getTagArray().size()) {
											if (0 == pairOfWordTag[1].compareTo(listOfAllWords.get(k).tagArray.get(l))) {
												//increases word/tag pair counter by one
												listOfAllWords.get(k).getCountArray().
												set(l,listOfAllWords.get(k).getCountArray().get(l) + 1);
												
												foundTag = true;
											}
											l++;
										}
										if (foundTag == false) {
											listOfAllWords.get(k).getCountArray().add(1);
											listOfAllWords.get(k).getTagArray().add(pairOfWordTag[1]);

										}

									}

								k++;

								}

								if (foundWord == false) {
									listOfAllWords.add(new PosStructure(
											pairOfWordTag[0],
											pairOfWordTag[1], 1));

								}

							}

						}

					}
					scanner.close();
				}
			}

		} catch (IOException e) {
			System.out.println("Accessing corpus failed: " + e);
		}

		return listOfAllWords;

	}

	
	/**
	 * @param A array list of PosStructures.
	 * @return A map, where the key is a a word and the value is the most used tag for that word
	 * 
	 */
	public Map<String, String> SumUpPosOfWords(
			ArrayList<PosStructure> posStructureArray) {

		Map<String, String> POSOfWords = new HashMap<String, String>();
		int highest;
		int position;
		for (int i = 0; i < posStructureArray.size(); i++) {
			highest = posStructureArray.get(i).getCountArray().get(0);
			position = 0;
			for (int j = 0; j < posStructureArray.get(i).getCountArray().size(); j++) {
				if (highest < posStructureArray.get(i).getCountArray().get(j)) {
					highest = posStructureArray.get(i).getCountArray().get(j);
					position = j;

				}
			}
			POSOfWords.put(posStructureArray.get(i).word,
					posStructureArray.get(i).tagArray.get(position));

		}

		return POSOfWords;

	}

	
	/**
	 * 
	 * @param A string[] containing tokenized words, a map of words and the tags they appear with most often
	 * @return A string[] containing the most likely pos for the word at the param string[] position
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
