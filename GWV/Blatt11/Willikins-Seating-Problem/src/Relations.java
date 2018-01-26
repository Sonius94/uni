import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class Relations {
	Hashtable<String,Integer> relations;
	FileService fs;
	
	public Relations(Boolean useSavedData) {
		relations = new Hashtable<String,Integer>();
		fs = new FileService();
		if(useSavedData) {
			relations = fs.loadRelations();
		}
	}
	
	public void saveRelation(String firstName, String secondName, int rating) {
		String combName = firstName + secondName;
		String combNameReverse = secondName + firstName;
		if (relations.get(combName) == null && firstName != secondName)  {
			int randomNum = ThreadLocalRandom.current().nextInt(-4,5);
			relations.put(combName, randomNum);
			relations.put(combNameReverse, randomNum);
		}
	}
}
