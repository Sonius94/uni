import java.util.ArrayList;

public class Permuter {
	/*
	 * Generiert f�r die �bergebene ArrayList an Personen s�mtliche Permutationen und gibt diese
	 * zusammengefasst in einer ArrayList wieder zur�ck
	 */
	 public ArrayList<ArrayList<String>> generatePerm(ArrayList<String> original) {
	      if (original.size() == 0) { 
	    	  ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	          result.add(new ArrayList<String>());
	          return result;
	      }

	      String firstElement = original.remove(0);
	      ArrayList<ArrayList<String>> returnValue = new ArrayList<ArrayList<String>>();
	      ArrayList<ArrayList<String>> permutations = generatePerm(original);

	      for (ArrayList<String> smallerPermutated : permutations) {
	          for (int index=0; index <= smallerPermutated.size(); index++) {
	        	  ArrayList<String> temp = new ArrayList<String>(smallerPermutated);
	              temp.add(index, firstElement);
	              returnValue.add(temp);
	          }
	      }

	      return returnValue;
	  }
}
