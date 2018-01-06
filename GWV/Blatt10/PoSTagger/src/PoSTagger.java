import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class PoSTagger {
	Hashtable<String, ArrayList<String>> tagPredicts = new Hashtable<String,ArrayList<String>>();
	Hashtable<String, ArrayList<String>> wordPredicts = new Hashtable<String,ArrayList<String>>();
	Hashtable<String, String> tagForWords = new Hashtable<String,String>();
	static final String filename = "hdt-1-10000-train.tags";
	private Scanner scan;
	
	public static void main(String[] args) throws IOException {
		PoSTagger posTagger = new PoSTagger();
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("1->file 2->input 0->abort");
		int choice;
		choice = Integer.parseInt(bf.readLine());
	    switch(choice) {
	    	case 1:
	    		posTagger.learnPredicitonsFromFile();
	            break;
	    	case 2:
	            posTagger.learnPredicitonsFromInput();
	            break;
	    	case 0:
	            break;
	    	default:break;
	    }
	        
		/*
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("die");
		testList.add("Aktie");
		testList.add("des");
		testList.add("Todes");
		System.out.println(posTagger.generateTaggedString(testList.get(0),testList.size()));
		*/
		// LESE SATZ AUS COMMANDLINE AUS
	}
	
	public void learnPredicitonsFromFile() {
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
	
	public void learnPredicitonsFromInput() {
		ArrayList<String> br = null;
		try {
			br = getCmdInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trainMarkov(br);
	}
	
	/*
	 * Es wird jeweils der Tag, der am häufigsten auf einen Tag folgt wieder gegeben.
	 * Es wird jeweils das Wort für einen Tag wieder gegeben, welches am häufigsten diesem zugeordnet ist.
	 */
	public String generateTaggedString(String startString, int sentenceLength) {
		StringBuilder result = new StringBuilder();
		String currentTag = tagForWords.get(startString);
		String currentWord = startString;
		for (int i = 0; i < sentenceLength; i++) {
			// Füge Wort + Tag hinzu hinzu
			result.append(currentWord);
			result.append("\\");
			result.append(currentTag); 
			result.append(" ");
			
			// Berechne einen möglichen kommenden Tag
			currentTag = predictNextTag(currentTag);
			// Berechne das Wort, das möglicherweise kommen kann, abhängig vom Tag
			currentWord = predictNextWord(currentTag);
			
		}
		return result.toString();
	}
	
	public void trainMarkov(ArrayList<String> sentence) {
		for (int i = 0; i < sentence.size(); i++) {
			String word = sentence.get(i).split("\t")[0];
			String tag = sentence.get(i).split("\t")[1];
			saveTagForWord(word, tag);
			addWordToTag(tag,word);
			if (i != sentence.size()-1 ) {
				String followingTag = sentence.get(i+1).split("\t")[1];
				addTagToToken(followingTag,tag);
			}
		}
	}
	/*
	 * To have a start List. The first word needs to get a tag
	 */
	private void saveTagForWord(String word, String tag) {
		String tagForWord = tagForWords.get(word);
		if (tagForWord == null) {
			tagForWords.put(word, tag);
		}
	}
	
	/*
	 * To predict which word will be a output for a tag
	 */
	private void addWordToTag(String tag, String word) {
		ArrayList<String> wordsForTag = wordPredicts.get(tag);
		if (wordsForTag == null) {
			wordsForTag = new ArrayList<String>();
		}
		wordsForTag.add(word);
		wordPredicts.put(tag, wordsForTag);
	}
	
	/*
	 * To predict what tag will follow on another tag.
	 */
	private void addTagToToken(String extraValue, String key) {
		ArrayList<String> followerTags = tagPredicts.get(key);
		if (followerTags == null) {
			followerTags = new ArrayList<String>();
		}
		followerTags.add(extraValue);
		tagPredicts.put(key, followerTags);
	}
	
	private BufferedReader getReader() throws IOException {
		URL path = PoSTagger.class.getResource(filename);
		File file = new File(path.getFile());
		return new BufferedReader(new FileReader(file));
	}
	
	private ArrayList<String> getCmdInput() throws IOException {
		scan = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        String s;
        while (true) {
            s = scan.nextLine();
            if (s.equals("")) {
                break;
            }
            lines.add(s);
        }
		return lines;
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
	
	private String predictNextWord(String currentTag) {
		ArrayList<String> possibleWordsForTag = wordPredicts.get(currentTag);
		Map<String, Integer> map = new HashMap<String, Integer>();

		for(int i = 0; i < possibleWordsForTag.size(); i++){
		   if(map.get(possibleWordsForTag.get(i)) == null){
		      map.put(possibleWordsForTag.get(i),1);
		   }else{
		      map.put(possibleWordsForTag.get(i), map.get(possibleWordsForTag.get(i)) + 1);
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
