import java.util.ArrayList;

public class PrintService {
	public void printOptimum(ArrayList<String> seatOrder, int rating, String algorithm) {
		System.out.println("Optimum für " + algorithm + ": ");
		System.out.println(seatOrder);
		System.out.println(rating);
	}
}
