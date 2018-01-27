import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class Willikins {
	PrintService printer;
	Permuter permuter;
	Relations relations;
	
	public Willikins() {
		printer = new PrintService();
		permuter = new Permuter();
		relations = new Relations();
	}
	
	public static void main(String[] args) {
		Willikins willikins = new Willikins();
		
		// Fill relations with data
		//willikins.generateRandomStart();
		willikins.startWithNameList();
		
		// use a solving algorithm
		//willikins.solveBruteForce(willikins.relations.people,willikins.relations.relations);
		willikins.greedyAscent(willikins.relations.people, willikins.relations.relations, willikins.permuter.generateNachbarn(willikins.relations.people));
		// greedy with random restart walk
		// another optimization
	}
	
	
	/*
	 * Der Brute-Force-Algorithmus f�r Aufgabenteil 2
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
		
		//�berpr�fe Nachbarn auf bessere Ratings und setze diese als neue beste SeatingOrders fest
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
	 * Berechnet f�r die �bergebene Reihenfolge am Tisch mit der Hashtable den Relationship
	 * Gesamtwert (s�mtliche Ratings der benachbarten Personen addiert)
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
	 * @return das rating für Person i in Beziehung zum nächsten Partner
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
	
	/*
	 * This start ais using a fixed name list in code
	 * the names will all get a random rating
	 * rating will not be saved to file
	 */
	private void generateRandomStart() {
		relations.generateRandomStart();
	}
	
	/*
	 * This start uses a list of names
	 * The ratings between the names will be loaded by file
	 */
	private void startWithNameList() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("Josie");
		names.add("Mia");
		names.add("Ben");
		names.add("Luca");
		names.add("Paul");
		
		// 5 Names
		
		names.add("Jonas");
		names.add("Finn");
		names.add("Emma");
		names.add("Hannah");
		names.add("Sofia");
		
		// 10 Names
		/*
		names.add("Anna");
		names.add("Lea");
		names.add("Emilia");
		names.add("Marie");
		names.add("Lena");
		names.add("Leonie");
		names.add("Emily");
		names.add("Leon");
		names.add("Leo");
		names.add("Luis");
		
		// 20 Names
		
		names.add("Lukas");
		names.add("Max");
		names.add("Maximilian");
		names.add("Felix");
		names.add("Noah");
		names.add("Lina");
		names.add("Amelie");
		names.add("Sophie");
		names.add("Lilly");
		names.add("Luisa");
		names.add("Johanna");
		names.add("Laura");
		names.add("Elias");
		names.add("Julian");
		names.add("Tim");
		names.add("Tom");
		names.add("Moritz");
		names.add("Henry");
		names.add("Henri");
		names.add("Niklas");
		names.add("Niclas");
		names.add("Nikolai");
		names.add("Nikolaus");
		names.add("Glumanda");
		names.add("Glutexo");
		names.add("Glurak");
		names.add("Schiggy");
		names.add("Schillok");
		names.add("Turtok");
		names.add("Bisasam");
		
		// 50 Names
		
		names.add("Bisaknosp");
		names.add("Bisaflor");
		names.add("Taubsi");
		names.add("Tauboga");
		names.add("Tauboss");
		names.add("Rattfratz");
		names.add("Rattikarl");
		names.add("Habitak");
		names.add("Ibitak");
		names.add("Rauby");
		names.add("Safcon");
		names.add("Smettbo");
		names.add("Hornliu");
		names.add("Kokuna");
		names.add("Bibor");
		names.add("Rettan");
		names.add("Arbok");
		names.add("Pikcahu");
		names.add("Menki");
		names.add("Rasaff");
		names.add("Pummeluff");
		names.add("Knuddeluff");
		names.add("Smogon");
		names.add("Smogmog");
		names.add("Sleima");
		names.add("Sleimok");
		names.add("Evoli");
		names.add("Blitza");
		names.add("Aquana");
		names.add("Flamara");
		names.add("Pantimos");
		names.add("Rossana");
		names.add("Porygon");
		names.add("Tauros");
		names.add("Porenta");
		names.add("Dratini");
		names.add("Dragonir");
		names.add("Dragoran");
		names.add("Arktos");
		names.add("Zapdos");
		names.add("Lavados");
		names.add("Mewtu");
		names.add("Mew");
		names.add("Raichu");
		names.add("Rihorn");
		names.add("Rizeros");
		names.add("Kapador");
		names.add("Garados");
		names.add("Seeper");
		names.add("Sterndu");
		
		// 100 Names
		
		names.add("Starmie");
		names.add("Muschas");
		names.add("Austos");
		names.add("Aerodactyl");
		names.add("Kabuto");
		names.add("Kabutops");
		names.add("Amonitas");
		names.add("Amoroso");
		names.add("Kleinstein");
		names.add("Geowaz");
		names.add("Georok");
		names.add("Sandan");
		names.add("Sandamer");
		names.add("Vulpix");
		names.add("Vulnona");
		names.add("Digda");
		names.add("Digdri");
		names.add("Dodu");
		names.add("Dodri");
		names.add("Mauzi");
		names.add("Snobilikat");
		names.add("Tragosso");
		names.add("Knogga");
		names.add("Owei");
		names.add("Kokowei");
		*/
		// 124 Names
		
		relations.loadStartByNames(names);
	}
}
