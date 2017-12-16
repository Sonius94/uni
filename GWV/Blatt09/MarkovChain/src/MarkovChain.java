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
	
	public static void main(String[] args) {
		MarkovChain markov = new MarkovChain();
		markov.learnPredicitons();
		markov.generateSentence("ich",10);
	}

	public void generateSentence(String startWord, int sentenceLenght) {
		String sentence = "";
		String lastWord = startWord;
		for (int i = 0; i < sentenceLenght; i++) {
			if (lastWord == null) {
				sentence += ". ";
				lastWord = "ich";
			}
			sentence += " " + lastWord;
			lastWord = predictNextWord(lastWord);
		}
		System.out.println(sentence);
	}
	
	/*
	 * inspired by veryphatic on github
	 */
	public void trainMarkov(ArrayList<String> comment) {
		for (int i = 0; i < comment.size(); i++) {
			if (i == 0 && comment.size() > 1) {
				ArrayList<String> suffix = wordPredicts.get(comment.get(i));
				if (suffix == null) {
					suffix = new ArrayList<String>();
				}
				suffix.add(comment.get(i+1));
				wordPredicts.put(comment.get(i), suffix);
			} else if (i == comment.size()-1) {
				// Do nothing because there will be no next word
			} else {
				ArrayList<String> suffix = wordPredicts.get(comment.get(i));
				if (suffix == null) {
					suffix = new ArrayList<String>();
				}
				suffix.add(comment.get(i+1));
				wordPredicts.put(comment.get(i), suffix);
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
		double r  = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
		double sum_of_probs = 0;
		if (wordPredicts.containsKey(lastWord)) {
			ArrayList<String> possibleWords = wordPredicts.get(lastWord);
			Hashtable<String, Double> wordPredicitions = getPredicitonValues(possibleWords);
			
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
			return str;
		}
		return null;
	}
	
	// TODO Sehr viel unnötige Berechnungen, muss optimiert werden
	private Hashtable<String, Double> getPredicitonValues(ArrayList<String> words) {
		Hashtable<String, Double> returnValue = new Hashtable<String, Double>();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			int count = 0;
			for (int s = 0; s < words.size(); s++) { 
				if (word == words.get(s)) {
					count++;
				}
			}
			double percentage = count / words.size();
			returnValue.put(word, percentage);
		}
		return returnValue;
	}
	
	private BufferedReader getReader() throws IOException {
		URL path = MarkovChain.class.getResource(filename);
		File file = new File(path.getFile());
		return new BufferedReader(new FileReader(file));
	}
}
