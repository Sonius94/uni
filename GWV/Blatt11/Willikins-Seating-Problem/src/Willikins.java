import java.util.ArrayList;
import java.util.Collections;
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
		willikins.instantiateRelations();
		willikins.solve();
	}
	
	
	/*
	 * Ways to initialise names and ratings
	 */
	public void instantiateRelations() {
		//willikins.generateRandomStart();
		startWithNameList();
	}
	
	
	/*
	 * Choose your solving algorithm
	 * time is readed while usage
	 */
	public void solve() {
		long startTime = System.nanoTime();
		// use a solving algorithm
		solveBruteForce(relations.people,relations.relations);
		long bruteTime = System.nanoTime();
		
		solveBruteForceWithHighCompare(relations.people,relations.relations);
		long bruteExtendedTime = System.nanoTime();
		
		greedyAscent(relations.people, relations.relations, permuter.generateNachbarn(relations.people));
		long greedyTime = System.nanoTime();
		
		// greedy with random restart walk
		// (startingOrder, relations, orderWithHighestRating, numbersOfRandomRestart, numbersOfRandomWalk, numbersOfRandomWalkRemaining)
		randomizedGreedyAscent(relations.people, relations.relations, relations.people, 1, 1, 1);
		long randomGreedyTime = System.nanoTime();
		
		// another optimization
		

		// divide by 1000000 to get milliseconds
		System.out.println(bruteTime - startTime);
		System.out.println(bruteExtendedTime - bruteTime);
		System.out.println(greedyTime - bruteExtendedTime);
		System.out.println(randomGreedyTime - greedyTime);
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
	
	
	/*
	 * Normal Greedy Ascent, without Random Restart and Random Walk
	 */
	public void greedyAscent(ArrayList<String> persons, Hashtable<String,Integer> relations, ArrayList<ArrayList<String>> nachbarn) {
		ArrayList<String> seatOrder = new ArrayList<String>(persons);
		int highestRating = getRating(persons, relations);
		
		//�berpr�fe Nachbarn auf bessere Ratings und setze diese als neue beste SeatingOrders fest
		for (int i = 0; i < nachbarn.size(); i++) {
			int rating = getRating(nachbarn.get(i), relations);
			if (rating > highestRating) {
				highestRating = rating;
				seatOrder = new ArrayList<String>(nachbarn.get(i));
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
	
	
	/*
	 * Greedy Ascent with Random Restart and Random Walk
	 * If the algorithm doesn't find a better seating order in the neighbors 
	 * of the current order, it uses random walk to maybe find better neighbors 
	 * after taking a step in the 'wrong' direction. After an amount of 
	 * random walks (walkValue), it uses random restart to create a whole new 
	 * order and starts all over again (amount can be modified by restartValue)
	 */
	public void randomizedGreedyAscent(ArrayList<String> currentOrder, Hashtable<String,Integer> relations, ArrayList<String> globalBestOrder, int restartValue, int originalWalkValue, int restWalkValue) {
		ArrayList<String> current = new ArrayList<String>(currentOrder);
		int currentRating = getRating(current, relations);
		ArrayList<String> high = new ArrayList<String>(globalBestOrder);
		int highRating = getRating(high, relations);
		boolean foundBetterNeighbor = false;
		ArrayList<ArrayList<String>> nachbarn = permuter.generateNachbarn(current);
		
		for (int i = 0; i < nachbarn.size(); i++) {
			int rating = getRating(nachbarn.get(i), relations);
			
			if (rating > highRating) {
				high = new ArrayList<String>(nachbarn.get(i));
				highRating = rating;
			}
			if (rating > currentRating) {
				current = new ArrayList<String>(nachbarn.get(i));
				currentRating = rating;
				foundBetterNeighbor = true;
			}
		}
		
		if (foundBetterNeighbor == true) {
			randomizedGreedyAscent(current, relations, high, restartValue, originalWalkValue, restWalkValue);
		}
		else {
			if (restartValue == 0 && restWalkValue == 0) {
				printer.printOptimum(high, highRating, "Randomized Greedy Ascent");
			}
			else if (restWalkValue != 0) {
				System.out.println("Random Walk...");
				int ranInt = ThreadLocalRandom.current().nextInt(0, nachbarn.size() + 1);
				randomizedGreedyAscent(nachbarn.get(ranInt), relations, high, restartValue, originalWalkValue, restWalkValue - 1);
			}
			else if (restartValue != 0) {
				System.out.println("Random Restart...");
				ArrayList<String> shuffled = new ArrayList<String>(current);
				Collections.shuffle(shuffled);
				randomizedGreedyAscent(shuffled, relations, high, restartValue - 1, originalWalkValue, originalWalkValue);
			}
		}
	}
	
	
	/*
	 * Der Brute-Force-Algorithmus f�r Aufgabenteil 2
	 * Jede Permutation der ArrayList an Personen wird untersucht, falls eine Permutation
	 * besser als die bisherige beste Seatingorder ist, wird sie als die neue beste festgesetzt
	 */
	public void solveBruteForceWithHighCompare(ArrayList<String> persons, Hashtable<String,Integer> relations) {
		ArrayList<String> seatOrder = new ArrayList<String>();
		ArrayList<ArrayList<String>> permutations = permuter.generatePerm(persons);
		//Setze das Gesamtrating auf den schlechtesten Wert (Anzahl der Personen * -4)
		int highestRating = permutations.size()*(-4);
		for(int i = 0; i<permutations.size();i++) {
			int currentRating = getRatingWithHighest(permutations.get(i),relations,highestRating);
			if (currentRating > highestRating) {
				highestRating = currentRating;
				seatOrder = permutations.get(i);
			}
		}
		printer.printOptimum(seatOrder, highestRating, "Brute-Force");
	}
	
	
	/*
	 * Berechnet f�r die �bergebene Reihenfolge am Tisch mit der Hashtable den Relationship
	 * Gesamtwert (s�mtliche Ratings der benachbarten Personen addiert)
	 */
	private int getRating(ArrayList<String> seatOrder,Hashtable<String,Integer> ratings) {
		int rating = 0;
		for(int i = 0; i<seatOrder.size();i++) {
			rating += getRating(seatOrder, ratings, i);
		}
		return rating;
	}
	
	
	/*
	 * Every Step this function checks if the highest rank can even be beatable
	 */
	private int getRatingWithHighest(ArrayList<String> seatOrder,Hashtable<String,Integer> ratings, int currentHightes) {
		int rating = 0;
		int iterations = seatOrder.size();
		for(int i = 0; i<iterations;i++) {
			rating += getRating(seatOrder, ratings, i);
			if((rating + (iterations * 4)) < currentHightes) {
				return rating;
			}
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
		return ratings.get(fullName);
	}
	
	
	/*
	 * This start is using a fixed name list in code
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
