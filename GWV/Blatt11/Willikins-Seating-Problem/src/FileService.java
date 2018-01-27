import java.io.*;
import java.util.*;

/*
 * FileService is used to save relations into a file and load them again
 */
public class FileService {
	static final String filename = "relationList.txt";
	
	/*
	 * Function to add additional ratings to saved file
	 * if relationName is already saved it will save it extra
	 */
	public void addRelations(Hashtable<String,Integer> ratings) {
		for(String key: ratings.keySet()){
			addRelation(key, ratings.get(key));
        }
	}
	
	/*
	 * load all relations that are saved in file
	 * if a relations is saved more than once the newest rating will be returned
	 * this relation list will saved new to the file so that double values will be deleted.
	 */
	public Hashtable<String,Integer> loadRelations() {
		Hashtable<String,Integer> relations = new Hashtable<String,Integer>();
		try (BufferedReader br = getReader()) {
			for(String line; (line = br.readLine()) != null; ) {
				String[] parts = line.split(";");
				String relationName = parts[0];
				Integer rating = Integer.parseInt(parts[1]);
				relations.put(relationName, rating);
		    }
			clearAndSave(relations);
		} catch (IOException e) {
			System.out.println(e);
		} 
		return relations;
	}
	
	public void saveNewRelationList(Hashtable<String,Integer> ratings) {
		clearRelationList();
		for(String key: ratings.keySet()){
			addRelation(key, ratings.get(key));
        }
	}
	
	private void addRelation(String partners, int rating) {
		if(partners.contains(";") || partners.isEmpty() || rating < -4 || rating > 4) {
			return; // Name cant have ; otherwise loadRelations will gone wrong
		}
		String newRelationLine = getRelationString(partners, rating);
		appendRelationToList(newRelationLine);
	}
	
	private BufferedReader getReader() throws IOException {
		return new BufferedReader(new FileReader(filename));
	}
	
	private void clearAndSave(Hashtable<String,Integer> relations) {
		clearRelationList();
		addRelations(relations);
	}
	
	private void clearRelationList() {
		try {
			PrintWriter output;
			output = new PrintWriter(filename);
			output.write("");
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getRelationString(String partners, int rating) {
		StringBuilder sb = new StringBuilder();
		sb.append(partners);
		sb.append(";");
		sb.append(rating);
		return sb.toString();
	}
	
	private void appendRelationToList(String newRelationLine) {
		try {
			Writer output;
			output = new BufferedWriter(new FileWriter(filename, true));
			output.append(newRelationLine);
			output.append(System.lineSeparator());
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
