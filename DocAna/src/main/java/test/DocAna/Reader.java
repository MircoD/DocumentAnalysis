package test.DocAna;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Reads the .rtf and creates the Review objects. Reads the file line by line
 * and saves the data behind the meta-information in the correspondent variable.
 * After gathering all information of one review a new Review objects is created
 * and added to the list. The summary and the review text are cleaned before
 * getting added to the Review object.
 * 
 * 
 */
public class Reader {

	public ArrayList<Review> readAndClear(String filePath) {
		

		Logger log = new Logger();
		ArrayList<Review> listOfReviews = new ArrayList<Review>();
		Map<String, Integer> countAuthors = new HashMap<String, Integer>();
		Map<String, Integer> countMovies = new HashMap<String, Integer>();
		String prod = new String();
		String user = new String();
		String profil = new String();
		int help_enum = 0;
		int help_denom = 0;
		int score = 0;
		long time = 0;
		String summary = new String();
		String text = new String();
		int a = 0;

		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.skip("(?s).{350}"); // skip all formating at the beginning

			while (scanner.hasNextLine()) {
				a++;
				if (scanner.hasNext("product/productId:")) {
					scanner.skip("product/productId:");
					prod = removeFirstChar(removeLastChar(scanner.nextLine()));
				}
				if (scanner.hasNext("review/userId:")) {
					scanner.skip("review/userId:");
					user = removeFirstChar(removeLastChar(scanner.nextLine()));
				}
				if (scanner.hasNext("review/profileName:")) {
					scanner.skip("review/profileName:");
					profil = removeFirstChar(removeLastChar(scanner.nextLine()));
				}
				if (scanner.hasNext("review/helpfulness:")) {
					scanner.skip("review/helpfulness:");
					String temp = removeFirstChar(removeLastChar(scanner
							.nextLine()));
					String[] tempSplit = temp.split("/");
					help_enum = Integer.parseInt(tempSplit[0]);
					help_denom = Integer.parseInt(tempSplit[1]);
				}
				if (scanner.hasNext("review/score:")) {
					scanner.skip("review/score:");
					score = (int) Double
							.parseDouble(removeFirstChar(removeLastChar(scanner
									.nextLine())));
				}
				if (scanner.hasNext("review/time:")) {
					scanner.skip("review/time:");
					time = Long
							.parseLong(removeFirstChar(removeLastChar(scanner
									.nextLine())));
				}
				if (scanner.hasNext("review/summary:")) {
					scanner.skip("review/summary:");
					summary = removeFirstChar(removeLastChar(scanner.nextLine()));
					summary = summary.replace("<a href=\"", "")
							.replace("</a>", "").replace("\">", " ")
							.replace("<br />", "")
							.replace("<span class=\"tiny\"", "")
							.replace("<span class=\"tiny", "")
							.replace("</span>", "").replace("<p>", "")
							.replace("&lt;", "").replace("/i&gt;", "")
							.replace("SPOILER>>;", "").replace("shrug>", "")
							.replace("sigh>>", "").replace("href=\"", "")
							.replace("&#60", "").replace("&#34", "")
							.replace("&quot;", "");

				}
				if (scanner.hasNext("review/text:")) {
					scanner.skip("review/text:");
					text = removeFirstChar(removeLastChar(scanner.nextLine()));
					text = text.replace("<a href=\"", "").replace("</a>", "")
							.replace("\">", " ").replace("<br />", "")
							.replace("<span class=\"tiny\"", "")
							.replace("<span class=\"tiny", "")
							.replace("</span>", "").replace("<p>", "")
							.replace("&lt;", "").replace("/i&gt;", "")
							.replace("SPOILER>>;", "").replace("shrug>", "")
							.replace("sigh>>", "").replace("href=\"", "")
							.replace("&#60", "").replace("&#34", "")
							.replace("&quot;", "");
				}

				scanner.nextLine();

				/*
				if(countAuthors.containsKey(user)){
					countAuthors.put(user, countAuthors.get(user)+1);
					
				} else{
					countAuthors.put(user,1);
				}
				if(countMovies.containsKey(prod)){
					countMovies.put(prod, countMovies.get(prod)+1);
					
				} else{
					countMovies.put(prod,1);
				}
				
				*/
				
				if(prod.compareTo("B002LBKDYE") == 0 ||
						prod.compareTo("B004WO6BPS") == 0 ||
						prod.compareTo("B009NQKPUW") == 0 ||
						prod.compareTo("B000VBJEFK") == 0 ||
						prod.compareTo("7883704540") == 0 ||
						prod.compareTo("B0028OA3EY") == 0 ||
						prod.compareTo("B0028OA3EO") == 0 ||
						prod.compareTo("B008PZZND6") == 0 ||
						prod.compareTo("B006TTC57C") == 0 || 
						prod.compareTo("B002VL2PTU") == 0 ||
						prod.compareTo("B001NFNFMQ") == 0 ||
						prod.compareTo("B000067JG3") == 0 ||
						prod.compareTo("B000067JG4") == 0 ||
						prod.compareTo("B000MMMTAK") == 0 ||
						prod.compareTo("B003DBEX6K") == 0 ||
						prod.compareTo("B001TAFCBC") == 0 ||
						prod.compareTo("B0039UTDFG") == 0 ||
						prod.compareTo("B000KKQNRO") == 0 || 
						prod.compareTo("B0002Y69NQ") == 0 || 
						prod.compareTo("B005CA4SJW") == 0){
				listOfReviews.add(new Review(prod, user, profil, help_denom,
						help_enum, score, time, summary, text));
				}
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}

		/*	
	 Iterator it1 = countAuthors.keySet().iterator(); 
	 Iterator it2 = countMovies.keySet().iterator();
	 
	 while(it1.hasNext()){
		 String tmp = it1.next().toString();
		 int tmp1 = countAuthors.get(tmp);
		 
		 if(tmp1 >10){
			 log.log(tmp.toString() + " " + countAuthors.get(tmp), "authors");
			 }
	 }
	 
	 while(it2.hasNext()){
		 String tmp = it2.next().toString();
		 int tmp1 = countMovies.get(tmp);
		 
		 if(tmp1 >10){
		 log.log(tmp.toString() + " " + countMovies.get(tmp), "movies");
		 }
	 }
	 
	 */
		
		return listOfReviews;

	}

	// Method for removing the first whitespace
	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	// Method for removing the \ at the of the lines
	private static String removeFirstChar(String str) {
		return str.substring(1, str.length());
	}

}
