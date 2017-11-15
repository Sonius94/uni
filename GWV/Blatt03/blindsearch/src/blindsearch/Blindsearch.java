package blindsearch;

import java.io.IOException;

/*
 * Main-Methode. Hier wird ein Dateipfad genommen und in ein Feld umgewandelt
 * Das Feld wird gespeichert und es kann nun ein Pfad gesucht werden.
 */
public class Blindsearch {
	// Datei-Name, der durchsucht werden soll.
	private static String fileName = "blatt3_environment.txt";
	
	public static void main(String[] args) {
		 char[][] fieldArray = createField();
		
		/*
		 * Das Feld hat für Blatt 3 noch keine Funktion, außer dem Ausliefern des Zustandes, da noch kein Algorithmus
		 * zum Durchlaufen von Search Spaces zum Einsatz kommt
		 */
		Field field = new Field(fieldArray);
		field.printField();
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
