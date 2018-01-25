import java.util.Hashtable;

public class FileService {
	public void saveRelations(Hashtable<String,Integer> ratings) {
		for(String key: ratings.keySet()){
			saveRelation(key, ratings.get(key));
        }
	}
	
	public void saveRelation(String partners, int rating) {
		
	}
	
	public void loadRelations() {
		
	}
}
