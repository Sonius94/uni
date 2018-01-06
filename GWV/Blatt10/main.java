package inputtoarraylist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	private static Scanner scan;

	public static void main(String[] args) throws IOException {
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
		//hier die Möglichkeit sich die ganze Arraylist nochmal ausgeben zu lassen.
        //for (int i = 0; i < lines.size(); i++) {
        //    System.out.println(lines.get(i));
        //}

	}

}
