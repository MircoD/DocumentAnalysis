package test.DocAna;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class POSTagger {
	Map<String, HashMap<String, PosStructure>> mapOfAllWords;
	Map<String, Integer> frequenzyTags;
	Map<String,Integer> frequenzy2Tags;
	Map<String,Integer> frequenzy3Tags;
	int sumAllTags;
	

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
								//remove the secondary part (-xyz/+xyz) of the tag
								if(pairOfWordTag[0].compareTo("--")!=0){	
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
										if(mapOfAllWords.get(pairOfWordTag[0]).get(pairOfWordTag[1]).containsCombinationWithIncrease(nMinus1Tag, nMinus2Tag)){
											
											
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

								if(frequenzyTags.containsKey(pairOfWordTag[1])){
									frequenzyTags.put(pairOfWordTag[1],frequenzyTags.get(pairOfWordTag[1]) +1);	
								} else {
									frequenzyTags.put(pairOfWordTag[1],1);
								}
								
								if(frequenzy2Tags.containsKey(nMinus1Tag+","+pairOfWordTag[1])){
									System.out.println(frequenzy2Tags.containsKey(nMinus1Tag+","+pairOfWordTag[1]));
									int tmp = frequenzyTags.get(nMinus1Tag+","+pairOfWordTag[1]) +1;
									frequenzy2Tags.put(nMinus1Tag+","+pairOfWordTag[1],tmp);	
								} else {
									System.out.println("added   "+nMinus1Tag+","+pairOfWordTag[1]);
									frequenzy2Tags.put(nMinus1Tag+","+pairOfWordTag[1],1);			
								}
								
								if(frequenzy3Tags.containsKey(nMinus2Tag+","+nMinus1Tag+","+pairOfWordTag[1])){
									frequenzy2Tags.put(nMinus2Tag+","+nMinus1Tag+","+pairOfWordTag[1],frequenzyTags.get(nMinus2Tag+","+nMinus1Tag+","+pairOfWordTag[1]) +1);	
								} else {
									frequenzy2Tags.put(nMinus2Tag+","+nMinus1Tag+","+pairOfWordTag[1],1);			
								}
								
								nMinus2Tag = nMinus1Tag;
								nMinus1Tag = pairOfWordTag[1];
								sumAllTags++;
							}

						}

					}
					scanner.close();
				}
			}

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
		String nMinus1Tag = "null";
		String nMinus2Tag = "null";	

		
		for(int i=0; i<text.length; i++){
			float highestProbabilityTrigram = 0;
			String highestTagTrigram="np";
			
			float highestProbabilityBigram = 0;
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
					countCurrentWord = countCurrentWord+tmpMap.get(itr1.next().toString()).getSum();
				}
				
				Iterator itr2 = tmpMap.keySet().iterator();
				
				while(itr2.hasNext()){
					String currentTag = itr2.next().toString();
					PosStructure currentPos = tmpMap.get(currentTag);
					
					//unigram
					if(currentPos.getSum()>highestProbabilityUnigram){
						highestTagUnigram = currentTag;
						highestProbabilityUnigram = currentPos.getSum();
					}
					
					
					//trigram
					if(currentPos.containsCombination(nMinus1Tag, nMinus2Tag)){
						float probTemp1 = frequenzy3Tags.get(nMinus2Tag+","+nMinus1Tag+","+currentTag)/frequenzy2Tags.get(nMinus2Tag+","+nMinus1Tag);
						float probTemp2 = (currentPos.getSum()/countCurrentWord)/(frequenzyTags.get(currentTag)/sumAllTags);
						float currentProb=probTemp1*probTemp2;
						
						if(currentProb>highestProbabilityTrigram){
								highestTagTrigram= currentTag;
								highestProbabilityTrigram = currentProb;
								foundTrigram=true;
						}
					} else if(currentPos.getTagNminus1().containsKey(nMinus1Tag)){
						float probTemp1 = frequenzy2Tags.get(nMinus1Tag+","+currentTag)/frequenzyTags.get(nMinus1Tag);
						float probTemp2 = (currentPos.getSum()/countCurrentWord)/(frequenzyTags.get(currentTag)/sumAllTags);
						float currentProb=probTemp1*probTemp2;
					
						if(currentProb>highestProbabilityBigram){
							highestTagBigram =currentTag;
							highestProbabilityBigram = currentProb;
							foundBigram=true;
						}
						
					} else if(currentPos.getSum()>highestProbabilityUnigram){
							highestTagUnigram = currentTag;
							highestProbabilityUnigram = currentPos.getSum();
					}
				}
				
				
				if(foundTrigram){
					tagged[i] = highestTagTrigram;
				} else if(foundBigram){
					tagged[i] = highestTagBigram;
				} else {
					tagged[i] = highestTagUnigram;
				}
				
				
			} else{
				tagged[i]="np";		
			}

			nMinus2Tag = nMinus1Tag;
			if(i==0){
				nMinus1Tag = "null";
			} else{
				nMinus1Tag = tagged[i-1];
			}
			
			
		}
		

		return tagged;
	}

}
