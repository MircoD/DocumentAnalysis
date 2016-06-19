package test.DocAna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Filter {
	
	
	public ArrayList<Movies> moviesWithMinReviews(HashMap<String,Movies> listOfMovies, int min) {
		Iterator it1 = listOfMovies.keySet().iterator();
		ArrayList <Movies> filterd = new ArrayList<Movies>();
		
		while (it1.hasNext()) {
			String key = it1.next().toString();
			if (listOfMovies.get(key).getReviews().size() > min) {
				filterd.add(listOfMovies.get(key));
			}

		}
		return filterd;
		
	}

	public ArrayList<Movies> moviesWithMinReviews(HashMap<String,Movies> listOfMovies, int min, int max) {
		Iterator it1 = listOfMovies.keySet().iterator();
		ArrayList <Movies> filterd = new ArrayList<Movies>();
		
		while (it1.hasNext()) {
			String key = it1.next().toString();
			if (listOfMovies.get(key).getReviews().size() >= min && listOfMovies.get(key).getReviews().size()<=max) {
				filterd.add(listOfMovies.get(key));
			}

		}
		return filterd;
		
	}
	public ArrayList<Authors> authorsWithMinReviews(HashMap<String,Authors> listOfAuthors, int min) {
		Iterator it1 = listOfAuthors.keySet().iterator();
		ArrayList <Authors> filterd = new ArrayList<Authors>();
		
		while (it1.hasNext()) {
			String key = it1.next().toString();
			if (listOfAuthors.get(key).getReviews().size() >= min) {
				filterd.add(listOfAuthors.get(key));
			}

		}
		return filterd;
		
	}
	
	public ArrayList<Authors> authorsWithMaxReviews(HashMap<String,Authors> listOfAuthors, int max) {
		Iterator it1 = listOfAuthors.keySet().iterator();
		ArrayList <Authors> filterd = new ArrayList<Authors>();
		
		while (it1.hasNext()) {
			String key = it1.next().toString();
			if (listOfAuthors.get(key).getReviews().size() <= max) {
				filterd.add(listOfAuthors.get(key));
			}

		}
		return filterd;
		
	}
}
