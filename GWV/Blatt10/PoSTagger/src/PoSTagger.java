import java.net.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.util.Map.Entry;

public class PoSTagger {
	Hashtable<String, ArrayList<String>> tagPredicts = new Hashtable<String,ArrayList<String>>();
	Hashtable<String, String> tagForWords = new Hashtable<String,String>();
	static final String filename = "hdt-1-10000-train.tags";
	
	// private String sentence = "";
	// private String lastWord = "";
	
	
	public static void main(String[] args) {
		PoSTagger posTagger = new PoSTagger();
		posTagger.learnPredicitons();
		System.out.println("");
		// LESE SATZ AUS COMMANDLINE AUS
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
	
	
	
	
	
	
	
	
	
	
	/*
	public void generateSentence(String startWord, int sentenceLenght) {
		lastWord = startWord;
		for (int i = 0; i < sentenceLenght; i++) {
			addNextWord();
		}
		System.out.println(sentence);
	}
	
	public void generateRandomSentence() {
		String randomStart = getRandomStartWord();
		int randomNum = ThreadLocalRandom.current().nextInt(10, 15);
		generateSentence(randomStart,randomNum);
	}
	
	private String predictNextWord(String lastWord) {
		ArrayList<String> possibleWords = tagPredicts.get(lastWord);
		Map<String, Integer> map = new HashMap<String, Integer>();

		for(int i = 0; i < possibleWords.size(); i++){
		   if(map.get(possibleWords.get(i)) == null){
		      map.put(possibleWords.get(i),1);
		   }else{
		      map.put(possibleWords.get(i), map.get(possibleWords.get(i)) + 1);
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
	
	private String getStartWord(String lastWord) {
		int sum_of_probs = 0;
		if (tagPredicts.containsKey(lastWord)) {
			ArrayList<String> possibleWords = tagPredicts.get(lastWord);
			Hashtable<String, Integer> wordPredicitions = getPredicitonValues(possibleWords);
			int border = getTotalCount(wordPredicitions);
			int r = ThreadLocalRandom.current().nextInt(0, border);
			
			Set<String> keys = wordPredicitions.keySet();
			Iterator<String> itr = keys.iterator();
			
			String str = "";
			while (itr.hasNext()) { 
			     str = itr.next();
			     sum_of_probs += wordPredicitions.get(str);
			     if (sum_of_probs > r) {
		   	         return str;
		   	     }
			 }
			System.out.println(sum_of_probs);
			return str;
		}
		return null;
	}
	
	private String getRandomStartWord() {
		return getStartWord(startToken);
	}
	
	// TODO Sehr viel unnötige Berechnungen, muss optimiert werden
	private Hashtable<String, Integer> getPredicitonValues(ArrayList<String> words) {
		Hashtable<String, Integer> returnValue = new Hashtable<String, Integer>();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			int count = 0;
			for (int s = 0; s < words.size(); s++) { 
				if (word == words.get(s)) {
					count++;
				}
			}
			returnValue.put(word, count);
		}
		return returnValue;
	}
	
	private void addNextWord() {
		if (lastWord == null) {
			sentence += ". ";
			//lastWord = getRandomStartWord();
		}
		sentence += " " + lastWord;
		lastWord = predictNextWord(lastWord);
	}
	
	private int getTotalCount(Hashtable<String, Integer> words) {
		int totalCount = 0;
		Set<String> keys = words.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext()) { 
		     String str = itr.next();
		     totalCount += words.get(str);
		 }
		return totalCount;
	}
	*/
}
