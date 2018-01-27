import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class Relations {
	ArrayList<String> people;
	Hashtable<String,Integer> relations;
	FileService fs;
	
	public Relations() {
		relations = new Hashtable<String,Integer>();
		fs = new FileService();
	}
	
	/*
	 * generates a relation list of names fixed in code
	 * adds a rating to fixed name list
	 * names and ratings doesnt get saved in file!
	 */
	public void generateRandomStart() {
		people = generateFixedPersonList();
		relations = generateRandomRatingList(people);
	}
	
	/*
	 * Loads saved Relations from file
	 * if Relations containes a name pairing its added to local relations
	 * if not a new rating is generated, saved and added to local relations
	 */
	public void loadStartByNames(ArrayList<String> names) {
		people = names;
		Hashtable<String,Integer> savedRelations = fs.loadRelations();
		for (String name : names) {
			for (String secondName : names) {
				Integer rating = savedRelations.get(name + secondName);
				if (rating == null) {
					ArrayList<String> newListToRate = new ArrayList<String>();
					newListToRate.add(name);
					newListToRate.add(secondName);
					Hashtable<String,Integer> newRelation = generateRandomRatingList(newListToRate);
					addRelationToFile(newRelation);
					relations.putAll(newRelation);
				} else {
					relations.put(name + secondName, rating);
					relations.put(secondName + name, rating);
				}
			}
		}
	}
	
	private void addRelationToFile(Hashtable<String,Integer> relations) {
		fs.addRelations(relations);
	}
	
	/*
	 * Erstellt f�r jede Person in der �bergebenen ArrayList ein Relationship-Rating 
	 * zwischen -4 und 4 in einer HashTable, in der jede Relationship mit dem Key
	 * NamePerson1NamePerson2 gespeichert wird
	 */
	private Hashtable<String,Integer> generateRandomRatingList(ArrayList<String> names) {
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
	 * Momentan die fest gespeicherte Liste f�r s�mtliche Tests
	 */
	private ArrayList<String> generateFixedPersonList() {
		ArrayList<String> persons = new ArrayList<String>();
		persons.add("Emil");
		persons.add("Anton");
		persons.add("Hans");
		persons.add("Kerstin");
		persons.add("Herbert");
		persons.add("Erich");
		persons.add("Brigitta");
		persons.add("Kai");
		return persons;
	}
}
