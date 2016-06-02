package test.DocAna;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class POSTagger {
	Map<String, HashMap<String, PosStructure>> mapOfAllWords;
	Map<String, Integer> frequenzyTags;
	Map<String,Integer> frequenzy2Tags;
	Map<String,Integer> frequenzy3Tags;
	Integer sumAllTags;
	

	public POSTagger(){
		this.mapOfAllWords = new HashMap<String, HashMap<String, PosStructure>>();
		this.frequenzyTags = new HashMap<String,Integer>();
		this.frequenzy2Tags = new HashMap<String,Integer>();
		this.frequenzy3Tags = new HashMap<String,Integer>();
		sumAllTags=0;
	}
	
	
	
	
	/**
	 * A Method for reading in the Brown Corpus and count the appearance of each word/tag
	 * pair and the two previous words.
	 * 
	 * @param the String[] to which the pos should be assigned
	 * @return String[] with the pos for a word on the position of that word from the original String[]
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
	public void  importAndCountCorpus() {
		System.out.println("reading in corpus...");
		

		try {

			File folder = new File(
					"C:/Users/Maurice/Desktop/Studium/6. Semester/CMDA/brown");
			File[] listOfFiles = folder.listFiles();
			
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String path = "C:/Users/Maurice/Desktop/Studium/6. Semester/CMDA/brown/"
							+ file.getName();
					Scanner scanner = new Scanner(new File(path));
					

					String nMinus1Tag = ".";
					String nMinus2Tag = ".";
					
					while (scanner.hasNextLine()) {
						// split to to get the word/tag pairs
						String[] splittedLineOfText = scanner.nextLine()
								.toLowerCase().split("\\s");


						for (int j = 0; j < splittedLineOfText.length; j++) {
							
							// split the word/tag pair
							String[] pairOfWordTag = splittedLineOfText[j]
									.split("/");
						
							
							// to remove all empty ones
							if (pairOfWordTag.length == 2) {
								String word = pairOfWordTag[0];
								String tag = pairOfWordTag[1];
								//remove the secondary part (-xyz/+xyz) of the tag
								if(word.compareTo("--")!=0){	
									tag = tag.split("-")[0];
									tag = tag.split("\\+")[0];
								} 

								// checks if the word already appeared.
								//if not it gets added to the map.
								if (mapOfAllWords.containsKey(word)) {
									
									//checks if the tag for the word is in the word/tag map
									//if not in the map it gets added to it
									if(mapOfAllWords.get(word).containsKey(tag)){
										
										//checks if the tag combination of the n-1 and n-2 tags
										//for the word/tag combination exists.
										//if it exists the counter for the tag combination gets increased by one.
										//otherwise it gets added to the list of combinations.
										if(mapOfAllWords.get(word).get(tag).containsCombination(nMinus2Tag, nMinus1Tag)){
											mapOfAllWords.get(word).get(tag).increase(nMinus2Tag, nMinus1Tag);
											
								
										} else{
												mapOfAllWords.get(word).get(tag).addCombination(nMinus2Tag, nMinus1Tag);
											
										}
										
									} else {
											mapOfAllWords.get(word).put(tag,new PosStructure(nMinus2Tag,nMinus1Tag));
											
									}
									
								} else{
									
										HashMap<String, PosStructure> tmpMap = new HashMap<String, PosStructure>();
										tmpMap.put(tag,new PosStructure(nMinus2Tag,nMinus1Tag));
										mapOfAllWords.put(word,tmpMap);
								}

								if(frequenzyTags.containsKey(tag)){
									frequenzyTags.put(tag,frequenzyTags.get(tag) +1);	
								} else {
									frequenzyTags.put(tag,1);
								}
								
								if(frequenzy2Tags.containsKey(nMinus1Tag+","+tag)){
									frequenzy2Tags.put(nMinus1Tag+","+tag,frequenzy2Tags.get(nMinus1Tag+","+tag) +1);	
								} else {
									frequenzy2Tags.put(nMinus1Tag+","+tag,1);	
								}
								
								if(frequenzy3Tags.containsKey(nMinus2Tag+","+nMinus1Tag+","+tag)){
									frequenzy3Tags.put(nMinus2Tag+","+nMinus1Tag+","+tag,frequenzy3Tags.get(nMinus2Tag+","+nMinus1Tag+","+tag) +1);	
								} else {
									frequenzy2Tags.put(nMinus2Tag+","+nMinus1Tag+","+tag,1);			
								}
								
								nMinus2Tag = nMinus1Tag;
								nMinus1Tag = tag;
								sumAllTags++;
							}

						}

					}
					scanner.close();
				}
			}
			
			System.out.println("corpus successfully read");
		} catch (IOException e) {
			System.out.println("Accessing corpus failed: " + e);
		}


	}


	
	
	/**
	 * 
	 * @param string[] containing tokenized words
	 * @param a map created with importAndCountCorpus() from this class
	 * @return A string[] containing the most likely pos for the word at the
	 *         param string[] position
	 * 
	 */
	public String[] assignPosToWords(String[] text) {
		String[]tagged = new String[text.length];
		String nMinus1Tag = ".";
		String nMinus2Tag = ".";	

		
		for(int i=0; i<text.length; i++){
			double highestProbabilityTrigram = 0;
			String highestTagTrigram="np";
			
			double highestProbabilityBigram = 0;
			String highestTagBigram="np";
			
			int highestProbabilityUnigram=0;
			String highestTagUnigram="np";
			
			boolean foundTrigram = false;
			boolean foundBigram = false;
			
			int countCurrentWord=0;
			
			if(mapOfAllWords.containsKey(text[i])){
				
				HashMap<String, PosStructure> tmpMap = mapOfAllWords.get(text[i]);
				
				Iterator itr1 = tmpMap.keySet().iterator();
				while(itr1.hasNext()){
					String tmp = itr1.next().toString();
					countCurrentWord = countCurrentWord+tmpMap.get(tmp).getSum();
				}

				Iterator itr2 = tmpMap.keySet().iterator();
				
				while(itr2.hasNext()){
					String currentTag = itr2.next().toString();
					PosStructure currentPos = tmpMap.get(currentTag);
					
					/*
					//trigram
					if(currentPos.containsCombination(nMinus1Tag, nMinus2Tag)){
						double probTemp1 = frequenzy3Tags.get(nMinus2Tag+","+nMinus1Tag+","+currentTag)/frequenzy2Tags.get(nMinus2Tag+","+nMinus1Tag);
						double probTemp2 = (currentPos.getSum()/countCurrentWord)/(frequenzyTags.get(currentTag)/sumAllTags);
						double currentProb=probTemp1*probTemp2;
						
						if(currentProb>highestProbabilityTrigram){
								highestTagTrigram= currentTag;
								highestProbabilityTrigram = currentProb;
								foundTrigram=true;
						}
						
					//bigram
					} else */ if(currentPos.getTagNminus1().containsKey(nMinus1Tag)){
						
				
						double tmp11 = frequenzy2Tags.get(nMinus1Tag+","+currentTag);
						double tmp12 = frequenzyTags.get(nMinus1Tag);
	
						double tmp21 = currentPos.getSum();
						double tmp22 = frequenzyTags.get(currentTag);
						
						double probTemp1 = tmp11/tmp12;
						double probTemp2 =  tmp21/tmp22;				
						double currentProb=probTemp1*probTemp2;
						
						if(currentProb>highestProbabilityBigram){
							highestTagBigram =currentTag;
							highestProbabilityBigram = currentProb;
							highestProbabilityUnigram = currentPos.getSum();
							foundBigram=true;
						}
						
					//unigram
					} else if(currentPos.getSum()>highestProbabilityUnigram){
						
							highestTagUnigram = currentTag;
							highestProbabilityUnigram = currentPos.getSum();
					}
				}
				
				
				if(foundBigram){
					tagged[i] = highestTagBigram;
				} else {
					tagged[i] = highestTagUnigram;
				}
				
				
			} else{
				tagged[i]="np";		
			}

			nMinus2Tag = nMinus1Tag;
			nMinus1Tag = tagged[i];

			
			
		}
		
		return tagged;
	}

}
