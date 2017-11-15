package blindsearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*
 * Dieser Service soll eine Datei einlesen und den Inhalt in ein char[][] konvertieren.
 */
public class FileService {
	
	/*
	 * Methode, die eine Datei in ein char[][] wandelt.
	 * @param path Der Pfad der Datei, die gelesen werden soll.
	 * @return Der Inhalt der Datei als char[][].
	 */
	public char[][] getCharArrayByFile(String path) throws IOException {
		List<String> lines = readFile(path);
		char[][] strArr = convertStringListToArray(lines);
		return strArr;
	}
	
	/*
	 * Methode, die eine Datei ausliest und wiedergibt.
	 * @param path Der Dateipfad, der gelesen werden soll.
	 * @return Gibt den Inhalt der Datei als List<String> wieder.
	 */
	private List<String> readFile(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		return lines;
	}
	
	/*
	 * Methode, die eine Liste an Strings in ein char[][] wandelt. Die erste Dimension
	 * ist dabei ein String der Liste, die zweite Dimension ist der String in ein
	 * Char Array konvertiert. Wir gehen von rechteckigen Feldern aus.
	 * @param Lines ist die StringListe, die konvertiert werden soll.
	 * @return Gibt die Liste konvertiert in ein char[][] wieder.
	 */
	private char[][] convertStringListToArray(List<String> lines) {
		char[][] twoDimArr = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			twoDimArr[i] = lines.get(i).toCharArray();
		}
		return twoDimArr;
	}
}
