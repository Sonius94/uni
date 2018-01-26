import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class FileService {
	static final String filename = "relationList.txt";
	
	public void saveRelations(Hashtable<String,Integer> ratings) {
		for(String key: ratings.keySet()){
			saveRelation(key, ratings.get(key));
        }
	}
	
	public void saveRelation(String partners, int rating) {
		// Name cant have ;
	}
	
	public void loadRelations() {
		try (BufferedReader br = getReader()) {
			for(String line; (line = br.readLine()) != null; ) {
				String[] parts = line.split(";");
				String relationName = parts[0]; // 004
				String relation = parts[1]; // 034556
		    }
		} catch (IOException e) {
			System.out.println(e);
		} 
	}
	
	private BufferedReader getReader() throws IOException {
		URL path = FileService.class.getResource(filename);
		File file = new File(path.getFile());
		return new BufferedReader(new FileReader(file));
	}
}
