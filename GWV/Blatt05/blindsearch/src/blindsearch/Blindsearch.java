package blindsearch;

import java.io.IOException;

/*
 * Main-Methode. Hier wird ein Dateipfad genommen und in ein Feld umgewandelt
 * Das Feld wird gespeichert und es kann nun ein Pfad gesucht werden.
 */
public class Blindsearch {
	// Datei-Name, der durchsucht werden soll.
	// private static String fileName = "blatt4_difference_maze.txt";
	private static String fileName = "small_field.txt";
	
	public static void main(String[] args) {
		Field field = new Field(createField());
		SearchService searchService = new SearchService(field);
		/*
		 * Alle Suchen hintereinander ausführen würde dazu führen, dass 
		 * die zweite Suche die abschließende Frontier der ersten Suche nutzt.
		 * Daher muss man sich vorher überlegen, welche Suche man präferiert.
		 */
		//searchService.breadthSearch();
		//searchService.depthSearch();
		//searchService.aStarSearch();
		searchService.findAllPaths();
	}
	
	/*
	 * Private Methode um ein Array-Feld gegeben eines Datei-Namens, welche im Resourcen-Ordner des
	 * Projekts liegen, zu erzeugen.
	 * @return Der Inhalt der Datei in einem char[][] verpackt.
	 */
	private static  char[][] createField() {
		//Service, um Datei in Feld zu wandeln
		FileService fileService = new FileService();
		//Array als Zischenablage für das Feld
		char[][] fieldArray;
		
		try {
			fieldArray = fileService.getCharArrayByFile(System.getProperty("user.dir") + "/resources/" + fileName);
			return fieldArray;
		} catch (IOException e) {
			System.out.println("Wrong usage");
			return null;
		}
	}
}
