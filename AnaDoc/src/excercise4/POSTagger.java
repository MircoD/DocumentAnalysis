package excercise4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class POSTagger {

	
	
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @return ArrayList <PosStructure> containing the word, all the tags the word appeared with(as a ArrayList) and a how often(as a ArrayList).
	 */
	public ArrayList<PosStructure> importAndCountCorpus() {

		ArrayList<PosStructure> posStructureArray = new ArrayList<PosStructure>();

		try {

			for (int i = 0; i < 1; i++) {
				String path = "E:/Studium/Informatik/brown/ca01";

				Scanner scanner = new Scanner(new File(path));
				while (scanner.hasNextLine()) {

					String[] splittedLineOfTaggedText = scanner.nextLine().toLowerCase().split("\\s");

					//splits word/tag into String[{word},{tag}] and looks if the word/tag 
					for (int j = 0; j < splittedLineOfTaggedText.length; j++) {

						String[] listOfTaggedWords = splittedLineOfTaggedText[j].split("/");

						if (listOfTaggedWords.length == 2) {
							int k = 0;
							boolean foundWord = false;

							while (k < posStructureArray.size() && foundWord == false) {
								
								
								if (0 == listOfTaggedWords[0].compareTo(posStructureArray.get(k).word)) {
									int l = 0;
									boolean foundTag = false;
									foundWord = true;
									while (l < posStructureArray.get(k).getTagArray().size()) {

										if (0 == listOfTaggedWords[1].compareTo(posStructureArray.get(k).tagArray.get(l))) {
											posStructureArray.get(k).getCountArray().set(l, posStructureArray.get(k).getCountArray().get(l)+1);
											foundTag = true;
											System.out.println(listOfTaggedWords[0] + "  " + listOfTaggedWords[1]);
										}

										l++;

									}
									
									if (foundTag == false) {
										posStructureArray.get(k).getCountArray().add(1);
										posStructureArray.get(k).getTagArray().add(listOfTaggedWords[1]);
										System.out.println(listOfTaggedWords[0] + "  " + listOfTaggedWords[1]);
										
									}
									
									
								}
								
								k++;

							}
							
							if (foundWord == false) {
								posStructureArray.add(new PosStructure(listOfTaggedWords[0], listOfTaggedWords[1],1));
								System.out.println(listOfTaggedWords[0] + "  " + listOfTaggedWords[1]);
								
							}
							
							

						}

					}

				}
				scanner.close();
			}

		} catch (IOException e) {
			System.out.println("Accessing corpus failed: " + e);
		}

		return posStructureArray;

	}

	
	
	public Map<String, String> SumUpWordPos(ArrayList<PosStructure> posStructureArray){
		for(int i=0; i < posStructureArray.size(); i++){
			
		}
		
	}
	

}
