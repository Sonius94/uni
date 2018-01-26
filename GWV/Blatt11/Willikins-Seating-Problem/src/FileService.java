import java.io.*;
import java.util.*;

public class FileService {
	static final String filename = "relationList.txt";
	
	public void saveNewRelationList(Hashtable<String,Integer> ratings) {
		clearRelationList();
		for(String key: ratings.keySet()){
			addRelation(key, ratings.get(key));
        }
	}
	
	public void addRelations(Hashtable<String,Integer> ratings) {
		for(String key: ratings.keySet()){
			addRelation(key, ratings.get(key));
        }
	}
	
	public void addRelation(String partners, int rating) {
		if(partners.contains(";") || partners.isEmpty() || rating < -4 || rating > 4) {
			return; // Name cant have ; otherwise loadRelations will gone wrong
		}
		String newRelationLine = getRelationString(partners, rating);
		appendRelationToList(newRelationLine);
	}
	
	public Hashtable<String,Integer>  loadRelations() {
		Hashtable<String,Integer> relations = new Hashtable<String,Integer>();
		try (BufferedReader br = getReader()) {
			for(String line; (line = br.readLine()) != null; ) {
				String[] parts = line.split(";");
				String relationName = parts[0];
				Integer rating = Integer.parseInt(parts[1]);
				relations.put(relationName, rating);
		    }
		} catch (IOException e) {
			System.out.println(e);
		} 
		return relations;
	}
	
	private BufferedReader getReader() throws IOException {
		return new BufferedReader(new FileReader(filename));
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
