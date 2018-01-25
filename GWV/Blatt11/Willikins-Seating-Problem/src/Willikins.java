import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class Willikins {
	public static void main(String[] args) {
		Willikins wilikins = new Willikins();
		ArrayList<String> persons = wilikins.generateFixedPersonList();
		Hashtable<String,Integer> relations = wilikins.generateFixedRatingList(persons);
		wilikins.solveBruteForce(persons,relations);
	}
	
	/*
	 * Der Brute-Force-Algorithmus f�r Aufgabenteil 2
	 * Jede Permutation der ArrayList an Personen wird untersucht, falls eine Permutation
	 * besser als die bisherige beste Seatingorder ist, wird sie als die neue beste festgesetzt
	 */
	public void solveBruteForce(ArrayList<String> persons, Hashtable<String,Integer> relations) {
		ArrayList<String> seatOrder = new ArrayList<String>();
		ArrayList<ArrayList<String>> permutations = generatePerm(persons);
		//Setze das Gesamtrating auf den schlechtesten Wert (Anzahl der Personen * -4)
		int highestRating = permutations.size()*(-4);
		for(int i = 0; i<permutations.size();i++) {
			int currentRating = getRating(permutations.get(i),relations);
			if (currentRating > highestRating) {
				highestRating = currentRating;
				seatOrder = permutations.get(i);
			}
		}
		System.out.println("Optimum:");
		System.out.println(seatOrder);
		System.out.println(highestRating);
	}
	
	
	public void solveAndGenerate(ArrayList<String> persons) {
		// generiere Relations
	}
	
	
	public void solveWithSavedRelations(ArrayList<String> persons) {
		// Nimm die gespeicherte
	}
	
	
	/*
	 * Momentan die fest gespeicherte Liste f�r s�mtliche Tests
	 */
	public ArrayList<String> generateFixedPersonList() {
		ArrayList<String> persons = new ArrayList<String>();
		persons.add("Emil");
		persons.add("Anton");
		persons.add("Hans");
		persons.add("Kerstin");
		return persons;
	}
	
	
	/*
	public Hashtable<String,Integer> generateFixedRatingList() {
		Hashtable<String,Integer> ht = new Hashtable<String,Integer>();
		ht.put("EmilAnton", 4);
		ht.put("AntonEmil", 4);
		ht.put("EmilHans", 3);
		ht.put("HansEmil", 3);
		ht.put("AntonHans", -2);
		ht.put("HansAnton", -2);
		
		ht.put("KerstinAnton", -4);
		ht.put("AntonKerstin", -4);
		ht.put("EmilKerstin", 0);
		ht.put("KerstinEmil", 0);
		ht.put("HansKerstin", 1);
		ht.put("KerstinHans", 1);
		return ht;
	}*/
	
	
	/*
	 * Erstellt f�r jede Person in der �bergebenen ArrayList ein Relationship-Rating 
	 * zwischen -4 und 4 in einer HashTable, in der jede Relationship mit dem Key
	 * NamePerson1NamePerson2 gespeichert wird
	 */
	public Hashtable<String,Integer> generateFixedRatingList(ArrayList<String> names) {
		Hashtable<String,Integer> ratings = new Hashtable<String,Integer>();
		for (String name : names) {
			for (String secondName : names) {
				String combName = name + secondName;
				String combNameReverse = secondName + name;
				if (ratings.get(combName) == null)  {
					int randomNum = ThreadLocalRandom.current().nextInt(-4,5);
					ratings.put(combName, randomNum);
					ratings.put(combNameReverse, randomNum);
				}
			}
		}
		System.out.println(ratings.toString());
		return ratings;
	}
	
	
	/*
	 * Berechnet f�r die �bergebene Reihenfolge am Tisch mit der Hashtable den Relationship
	 * Gesamtwert (s�mtliche Ratings der benachbarten Personen addiert)
	 */
	public int getRating(ArrayList<String> seatOrder,Hashtable<String,Integer> ratings) {
		int rating = 0;
		for(int i = 0; i<seatOrder.size();i++) {
			String personA = seatOrder.get(i);
			String personB;
			if(i < seatOrder.size() - 1) {
				personB = seatOrder.get(i+1);
			} else {
				personB = seatOrder.get(0);
			}
			String fullName = personA + personB;
			rating += ratings.get(fullName);
		}
		// What to do if relation not found, currently 0 back
		return rating;
	}
	
	
	/*
	 * Generiert f�r die �bergebene ArrayList an Personen s�mtliche Permutationen und gibt diese
	 * zusammengefasst in einer ArrayList wieder zur�ck
	 */
	 public ArrayList<ArrayList<String>> generatePerm(ArrayList<String> original) {
	      if (original.size() == 0) { 
	    	  ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	          result.add(new ArrayList<String>());
	          return result;
	      }

	      String firstElement = original.remove(0);
	      ArrayList<ArrayList<String>> returnValue = new ArrayList<ArrayList<String>>();
	      ArrayList<ArrayList<String>> permutations = generatePerm(original);

	      for (ArrayList<String> smallerPermutated : permutations) {
	          for (int index=0; index <= smallerPermutated.size(); index++) {
	        	  ArrayList<String> temp = new ArrayList<String>(smallerPermutated);
	              temp.add(index, firstElement);
	              returnValue.add(temp);
	          }
	      }

	      return returnValue;
	  }
}
