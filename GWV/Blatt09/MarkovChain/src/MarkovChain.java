import java.util.Hashtable; 
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Set;
import java.util.Iterator;

public class MarkovChain {
	Hashtable<String, ArrayList<String>> wordPredicts = new Hashtable<String,ArrayList<String>>();
	static final String filename = "ggcc-one-word-per-line.txt";
	static String startToken = "_startTokenWord";
	static String endToken = "_endTokenWord";
	static String startWord = "ich";
	static int sentenceLength = 10;
	
	private String sentence = "";
	private String lastWord = "";
	
	
	public static void main(String[] args) {
		MarkovChain markov = new MarkovChain();
		markov.learnPredicitons();
		//markov.generateSentence(startWord,sentenceLength);
		markov.generateRandomSentence();
	}

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
	
	/*
	 * inspired by veryphatic on github
	 */
	public void trainMarkov(ArrayList<String> comment) {
		for (int i = 0; i < comment.size(); i++) {
			if (i == 0 && comment.size() > 1) {
				addWordToToken(comment.get(i),startToken);
				addWordToToken(comment.get(i+1),comment.get(i));
			} else if (i == comment.size()-1) {
				addWordToToken(comment.get(i),endToken); // isnt used after anymore?
			} else {
				addWordToToken(comment.get(i+1), comment.get(i));
			}
		}
	}
	
	public void learnPredicitons() {
		try (BufferedReader br = getReader()) {
			ArrayList<String> comment = new ArrayList<>();
			for(String line; (line = br.readLine()) != null; ) {
		        if (line.length() == 0) {
		        		trainMarkov(comment);
		        		comment = new ArrayList<>();
		        } else {
		        		comment.add(line);
		        }
		    }
		} catch (IOException e) {
			System.out.println(e);
		}   
	}
	
	private String predictNextWord(String lastWord) {
		
		int sum_of_probs = 0;
		if (wordPredicts.containsKey(lastWord)) {
			ArrayList<String> possibleWords = wordPredicts.get(lastWord);
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
		return predictNextWord(startToken);
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
	
	private void addWordToToken(String extraValue, String key) {
		ArrayList<String> words = wordPredicts.get(key);
		if (words == null) {
			words = new ArrayList<String>();
		}
		words.add(extraValue);
		wordPredicts.put(key, words);
	}
	
	private void addNextWord() {
		if (lastWord == null) {
			sentence += ". ";
			lastWord = getRandomStartWord();
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
	
	private BufferedReader getReader() throws IOException {
		URL path = MarkovChain.class.getResource(filename);
		File file = new File(path.getFile());
		return new BufferedReader(new FileReader(file));
	}
}
