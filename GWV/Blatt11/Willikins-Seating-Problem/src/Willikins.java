import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class Willikins {
	PrintService printer;
	Permuter permuter;
	
	public Willikins() {
		printer = new PrintService();
		permuter = new Permuter();
	}
	
	public static void main(String[] args) {
		Willikins willikins = new Willikins();
		// generate / get person List
		ArrayList<String> persons = willikins.generateFixedPersonList();
		// generate / get relations
		Hashtable<String,Integer> relations = willikins.generateFixedRatingList(persons);
		// use a solving algorithm
		willikins.solveBruteForce(persons,relations);
		willikins.greedyAscent(persons, relations, willikins.permuter.generateNachbarn(persons));
	}
	
	
	/*
	 * Der Brute-Force-Algorithmus fï¿½r Aufgabenteil 2
	 * Jede Permutation der ArrayList an Personen wird untersucht, falls eine Permutation
	 * besser als die bisherige beste Seatingorder ist, wird sie als die neue beste festgesetzt
	 */
	public void solveBruteForce(ArrayList<String> persons, Hashtable<String,Integer> relations) {
		ArrayList<String> seatOrder = new ArrayList<String>();
		ArrayList<ArrayList<String>> permutations = permuter.generatePerm(persons);
		//Setze das Gesamtrating auf den schlechtesten Wert (Anzahl der Personen * -4)
		int highestRating = permutations.size()*(-4);
		for(int i = 0; i<permutations.size();i++) {
			int currentRating = getRating(permutations.get(i),relations);
			if (currentRating > highestRating) {
				highestRating = currentRating;
				seatOrder = permutations.get(i);
			}
		}
		printer.printOptimum(seatOrder, highestRating, "Brute-Force");
	}
	
	
	public void greedyAscent(ArrayList<String> persons, Hashtable<String,Integer> relations, ArrayList<ArrayList<String>> nachbarn) {
		ArrayList<String> seatOrder = new ArrayList<String>(persons);
		ArrayList<ArrayList<String>> permutations = permuter.generateNachbarn(persons);
		int highestRating = getRating(persons, relations);
		
		//Überprüfe Nachbarn auf bessere Ratings und setze diese als neue beste SeatingOrders fest
		for (int i = 0; i < permutations.size(); i++) {
			int rating = getRating(permutations.get(i), relations);
			if (rating > highestRating) {
				highestRating = rating;
				seatOrder = new ArrayList<String>(permutations.get(i));
			}
		}
		
		//Falls keine bessere Order gefunden wurde, beende Algorithmus und printe beste Order
		if (seatOrder.equals(persons)) {
			printer.printOptimum(seatOrder, highestRating, "Greedy Ascent");
		}
		//Sonst starte Algorithmus mit neuer bester Order und neu berechneten Nachbarn
		else {
			greedyAscent(seatOrder, relations, permuter.generateNachbarn(seatOrder));
		}
	}
	
	
	public void solveAndGenerate(ArrayList<String> persons) {
		// generiere Relations
	}
	
	
	public void solveWithSavedRelations(ArrayList<String> persons) {
		// Nimm die gespeicherte
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
	 * Momentan die fest gespeicherte Liste fï¿½r sï¿½mtliche Tests
	 */
	public ArrayList<String> generateFixedPersonList() {
		ArrayList<String> persons = new ArrayList<String>();
		persons.add("Emil");
		persons.add("Anton");
		persons.add("Hans");
		persons.add("Kerstin");
		persons.add("Herbert");
		persons.add("Erich");
		persons.add("Brigitta");
		persons.add("Kai");
//		persons.add("Julie");
//		persons.add("Susanne");
//		persons.add("Ingeburg");
//		persons.add("Raphael");
//		persons.add("Leonore");
		return persons;
	}
	
	
	/*
	 * Erstellt fï¿½r jede Person in der ï¿½bergebenen ArrayList ein Relationship-Rating 
	 * zwischen -4 und 4 in einer HashTable, in der jede Relationship mit dem Key
	 * NamePerson1NamePerson2 gespeichert wird
	 */
	public Hashtable<String,Integer> generateFixedRatingList(ArrayList<String> names) {
		Hashtable<String,Integer> ratings = new Hashtable<String,Integer>();
		for (String name : names) {
			for (String secondName : names) {
				String combName = name + secondName;
				String combNameReverse = secondName + name;
				if (ratings.get(combName) == null && name != secondName)  {
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
	 * Berechnet fï¿½r die ï¿½bergebene Reihenfolge am Tisch mit der Hashtable den Relationship
	 * Gesamtwert (sï¿½mtliche Ratings der benachbarten Personen addiert)
	 */
	public int getRating(ArrayList<String> seatOrder,Hashtable<String,Integer> ratings) {
		int rating = 0;
		for(int i = 0; i<seatOrder.size();i++) {
			rating += getRating(seatOrder, ratings, i);
		}
		return rating;
	}
	
	
	/*
	 * Gibt den Relation-Wert zwischen zwei Personen wieder
	 * @return das rating fÃ¼r Person i in Beziehung zum nÃ¤chsten Partner
	 */
	private int getRating(ArrayList<String> seatOrder, Hashtable<String,Integer> ratings, int index) {
		String personA = seatOrder.get(index);
		String personB;
		if(index < seatOrder.size() - 1) {
			personB = seatOrder.get(index+1);
		} else {
			personB = seatOrder.get(0);
		}
		String fullName = personA + personB;
		// TODO What to do if relation not found, currently 0 back
		return ratings.get(fullName);
	}
}
