import java.util.ArrayList;

public class PrintService {
	public void printOptimum(ArrayList<String> seatOrder, int rating, String algorithm) {
		System.out.println("Optimum f�r " + algorithm + ": ");
		System.out.println(seatOrder);
		System.out.println(rating);
	}
}
