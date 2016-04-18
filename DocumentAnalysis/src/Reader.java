import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads the .rtf and creates the Review objects.
 * 
 */
public class Reader {

	public static void main(String[] args) {

		ArrayList<Review> list = new ArrayList<Review>();
		String prod = new String();
		String user = new String();
		String profil = new String();
		int help_denom = 0;
		int help_enum = 0;
		int score = 0;
		String time = new String();
		String summary = new String();
		String text = new String();

		try {
			Scanner scanner = new Scanner(new File("E:/Downloads/docAnaTextSample.rtf"));
			while (scanner.hasNext()) {

				if (scanner.hasNext("product/productId:")) {
					scanner.skip("product/productId:");
					prod = removeLastChar(scanner.nextLine());
				}
				if (scanner.hasNext("review/userId:")) {
					scanner.skip("review/userId:");
					user = removeLastChar(scanner.nextLine());
				}
				if (scanner.hasNext("review/profileName:")) {
					scanner.skip("review/profileName:");
					profil = removeLastChar(scanner.nextLine());
				}
				if (scanner.hasNext("review/helpfulness:")) {
					scanner.skip("review/helpfulness:");
					String temp = removeFirstChar(removeLastChar(scanner.nextLine()));
					System.out.println(temp);
				}
				if (scanner.hasNext("review/score:")) {
					scanner.skip("review/score:");
					score = (int) Double
							.parseDouble(removeLastChar(scanner.nextLine()));
				}
				if (scanner.hasNext("review/time:")) {
					scanner.skip("review/time:");
					time = removeLastChar(scanner.nextLine());
				}
				if (scanner.hasNext("review/summary:")) {
					scanner.skip("review/summary:");
					summary = removeLastChar(scanner.nextLine());
				}
				if (scanner.hasNext("review/text:")) {
					scanner.skip("review/text:");
					text = removeLastChar(scanner.nextLine());
				}
				scanner.nextLine();

			}

			list.add(new Review(prod, user, profil, help_denom, help_enum, score, time, summary,
					text));
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}
	
	private static String removeFirstChar(String str) {
		return str.substring(1, str.length());
	}


}
