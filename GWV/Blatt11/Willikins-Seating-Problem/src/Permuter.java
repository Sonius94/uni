import java.util.ArrayList;
import java.util.Collections;

public class Permuter {
	/*
	 * Generiert f�r die �bergebene ArrayList an Personen s�mtliche
	 * Permutationen und gibt diese zusammengefasst in einer ArrayList wieder
	 * zur�ck
	 */
	public ArrayList<ArrayList<String>> generatePerm(ArrayList<String> original) {
		// Scheiss Benennung, ich weiss, aber sonst zerschiesst ihr euch die Input
		// ArrayList in der Willikins Klasse
		// TODO Den Kommentar da oben entfernen, kommt sonst schlecht an evtl ;)
		ArrayList<String> Original = new ArrayList<String>(original);
		if (Original.size() == 0) {
			ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
			result.add(new ArrayList<String>());
			return result;
		}

		String firstElement = Original.remove(0);
		ArrayList<ArrayList<String>> returnValue = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> permutations = generatePerm(Original);

		for (ArrayList<String> smallerPermutated : permutations) {
			for (int index = 0; index <= smallerPermutated.size(); index++) {
				ArrayList<String> temp = new ArrayList<String>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}

	// Gereniert lediglich diejenigen Permutationen, die mit einer Vertauschung
	// erreichbar sind
	public ArrayList<ArrayList<String>> generateNachbarn(ArrayList<String> original) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < original.size() - 1; i++) {
			for (int j = i + 1; j < original.size(); j++) {
				ArrayList<String> copy = new ArrayList<String>(original);
				Collections.swap(copy, i, j);
				if (result.contains(copy) == false && !copy.equals(original)) {
					result.add(copy);
				}
			}
		}
		return result;
	}
}
