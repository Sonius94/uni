import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class PoSTagger {
	Hashtable<String, ArrayList<String>> tagPredicts = new Hashtable<String,ArrayList<String>>();
	Hashtable<String, String> tagForWords = new Hashtable<String,String>();
	static final String filename = "hdt-1-10000-train.tags";
	
	public static void main(String[] args) {
		PoSTagger posTagger = new PoSTagger();
		posTagger.learnPredicitons();
		/*
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("die");
		testList.add("Aktie");
		testList.add("des");
		testList.add("Todes");
		System.out.println(posTagger.generateTaggedString(testList));
		*/
		// LESE SATZ AUS COMMANDLINE AUS
	}
	
	public void learnPredicitons() {
		try (BufferedReader br = getReader()) {
			ArrayList<String> sentence = new ArrayList<>();
			for(String line; (line = br.readLine()) != null; ) {
		        if (line.length() == 0) {
		        		trainMarkov(sentence);
		        		sentence = new ArrayList<>();
		        } else {
		        		sentence.add(line);
		        }
		    }
		} catch (IOException e) {
			System.out.println(e);
		}   
	}
	
	public String generateTaggedString(ArrayList<String> stringList) {
		StringBuilder result = new StringBuilder();
		String currentTag = "";
		for (int i = 0; i < stringList.size(); i++) {
			result.append(stringList.get(i));
			result.append("\\");
			if (i == 0) {
				currentTag = tagForWords.get(stringList.get(i));
			} else {
				currentTag = predictNextTag(currentTag); // get Tag with highest prediction
			}
			result.append(currentTag); 
			result.append(" ");
		}
		return result.toString();
	}
	
	public void trainMarkov(ArrayList<String> sentence) {
		for (int i = 0; i < sentence.size(); i++) {
			String word = sentence.get(i).split("\t")[0];
			String tag = sentence.get(i).split("\t")[1];
			saveTagForWord(word, tag);
			if (i != sentence.size()-1 ) {
				String followingTag = sentence.get(i+1).split("\t")[1];
				addTagToToken(followingTag,tag);
			}
		}
	}
	
	private void saveTagForWord(String word, String tag) {
		String tagForWord = tagForWords.get(word);
		if (tagForWord == null) {
			tagForWords.put(word, tag);
		}
	}
	
	private BufferedReader getReader() throws IOException {
		URL path = PoSTagger.class.getResource(filename);
		File file = new File(path.getFile());
		return new BufferedReader(new FileReader(file));
	}
	
	private void addTagToToken(String extraValue, String key) {
		ArrayList<String> followerTags = tagPredicts.get(key);
		if (followerTags == null) {
			followerTags = new ArrayList<String>();
		}
		followerTags.add(extraValue);
		tagPredicts.put(key, followerTags);
	}
	
	private String predictNextTag(String lastTag) {
		ArrayList<String> possibleTags = tagPredicts.get(lastTag);
		Map<String, Integer> map = new HashMap<String, Integer>();

		for(int i = 0; i < possibleTags.size(); i++){
		   if(map.get(possibleTags.get(i)) == null){
		      map.put(possibleTags.get(i),1);
		   }else{
		      map.put(possibleTags.get(i), map.get(possibleTags.get(i)) + 1);
		   }
		}
		int largest = 0;
		String stringOfLargest = null;
		for (Entry<String, Integer> entry : map.entrySet()) {
		   String key = entry.getKey();
		   int value = entry.getValue();
		   if( value > largest){
		      largest = value;
		      stringOfLargest = key;
		   }
		}
		return stringOfLargest;
	}
}
