package blindsearch;

/*
 * Das Feld repräsentiert den Zustand und ermöglicht eine Suche auf dem Feld.
 */
public class Field {
	//Das Feld wird als zweidimensionales Array gespeichert.
	private char[][] charField;
	
	/*
	 * Erzeugen einer Klasse
	 * @param charArray gibt das Array an, auf dem gesucht werden soll.
	 */
	public Field(char[][] charArray) {
		charField = charArray;
	}
	
	/*
	 * Methode, um den aktuellen Zustand auszugeben
	 * Spielfigur = s
	 * Ziel = g
	 * nicht passierbar = x
	 * TODO mit Aufgabe 4: bereits besucht = b
	 */
	public void printField() {
		for (char[] c : charField) {
			System.out.println(c);
		}
	}
	
	//TODO mit Aufgabe 4 Suchalgorithmen hinzufügen
}
