package blindsearch;

import java.util.ArrayList;

/*
 * Das Feld repräsentiert den Zustand und ermöglicht eine Suche auf dem Feld.
 */
public class Field {
	//Das Feld wird als zweidimensionales Array gespeichert.
	private char[][] charField;
	private Position start;
	private Position goal;
	private ArrayList<Path> frontier;
	private ArrayList<Portal> portals;
	private PrintService printer;
	
	/*
	 * Erzeugen einer Klasse
	 * @param charArray gibt das Array an, auf dem gesucht werden soll.
	 */
	public Field(char[][] charArray) {
		charField = charArray;
		setStartStatus();
	}

	/*
	 * Diese Methode prüft einen übergebenen Pfad auf dem Feld.
	 * Falls der Pfad am Ziel ist, ist unsere Suche fertig und 
	 * wir können einen Zielpfad ausgeben.
	 * Ansonsten fügen wir neu erreichbare Pfade in die frontshare.
	 * @return true, falls wir ein Ziel erreicht haben.
	 */
	public boolean checkPath(int index) {
		Path pathToCheck = frontier.remove(index);
		if (isPathAtGoal(pathToCheck)) { 
			printGoalPath(pathToCheck);
			
			return true; 
		}
		addNewPathsToFrontshare(pathToCheck);
		return false;
	}

	/*
	 * Vom Pfad aus, erstellen wir neue Pfade jeweils
	 * eine Position weiter als unser aktueller Kopf des Pfades
	 */
	private void addNewPathsToFrontshare(Path oldPath) {
		Position position = oldPath.getHead();
		addPathToFrontshare(position.up(), oldPath);
		addPathToFrontshare(position.right(), oldPath);
		addPathToFrontshare(position.down(), oldPath);
		addPathToFrontshare(position.left(), oldPath);
	}

	/*
	 * Falls ein neuer Pfad erstellt werden kann,
	 * wird dieser unserer Frontshare hinzugefügt.
	 * @param oldPath Der Pfad, der erweitert wird
	 * @param position der Kopf des neuen Pfades
	 */
	private void addPathToFrontshare(Position position, Path oldPath) {
		if (newPositionIsMoveable(position,oldPath)) {
			Path path = new Path(oldPath, position);
			path = usePortal(path);
			frontier.add(path);
		}
	}
	
	/*
	 * Falls der Kopf unseres Pfades auf einem Portal steht,
	 * wird unser Pfad auf das andere Ende des Portals weiter
	 * geführt und der Pfad hat damit einen neuen Kopf.
	 */
	private Path usePortal(Path path) {
		if (portals == null) { return path; }
		for (int index = 0; index < portals.size(); index ++) {
			Position head = path.getHead();
			Position portalStart = portals.get(index).getFirstPortalEnding();
			Position portalEnd = portals.get(index).getSecondPortalEnding();
			if (head.isSamePositionAs(portalStart)) {
				path.addPosition(portalEnd);
			} else if (head.isSamePositionAs(portalEnd)) {
				path.addPosition(portalStart);
			}
		}
		return path;
	}
	
	/*
	 * Methode, um zu prüfen, ob eine Position einem Pfad auf dem aktuellen
	 * Feld hinzugefügt werden kann. 
	 */
	private boolean newPositionIsMoveable(Position position, Path oldPath) {
		return position.getX() <= getWidth() && 
				position.getY() <= getHeight() &&
				!positionIsX(position) &&
				!oldPath.containsPosition(position);
	}
	
	/*
	 * Methode, um zu ermitteln, ob der Kopf eines Pfades sich auf dem Ziel des
	 * Labyrinths befindet.
	 */
	private boolean isPathAtGoal(Path path) {
		Position position = path.getHead();
		if (position.getX() == goal.getX() && 
				position.getY() == goal.getY()) { 
			return true; 
		}
		return false;
	}

	/*
	 * Gibt die zuerst gefundene Positon eines Chars auf dem Feld wieder
	 */
	private Position getPosition(char searchedChar) {
		return getPositionFromIndex(searchedChar, 0, 0);
	}

	/*
	 * True, falls das Feld eine Mauer ('x') ist.
	 */
	private boolean positionIsX(Position position) {
		if (charField[position.getX()][position.getY()] != 'x' ) { return false; }
		return true;
	}
	
	public void printStatus() {
		printer.printFieldStatus(this);
	}
	
	private void printGoalPath(Path goalPath) {
		printer.printGoalPath(charField, goalPath);
		goalPath.printPath();
	}
	
	/*
	 * Initialisierung des Anfang-Feldes.
	 */
	private void setStartStatus() {
		setStart();
		setGoal();
		setStartFrontshare();
		setPortals();
		printer = new PrintService();
	}
	
	private void setStartFrontshare() {
		frontier = new ArrayList<>();
		Path startPath = new Path(start);
		frontier.add(startPath);
	}
	
	private void setGoal() {
		goal = getPosition('g');
	}

	private void setStart() {
		start = getPosition('s');
	}
	
	/*
	 * Sucht im Labyrinth nach Zahlen als Portale. Wenn eine Zahl gefunden wurde
	 * wird diese als Portal zusammen mit einer weiteren Position derselben
	 * Zahl gespeichert. Funktioniert nur, falls das Labyrinth eine Zahl genau zweimal
	 * enthält. Also kann es bis zu 10 Portale (0-9) geben.
	 */
	private void setPortals() {
		for (int portalIndex = 0; portalIndex < 10; portalIndex++) {
			Position start = getPosition((char)(portalIndex + '0'));
			if (start != null) {
				Position end = getPositionFromIndex((char)(portalIndex + '0'), start.getX(), start.getY() + 1);
				Portal portal = new Portal(start,end);
				if (portals == null) {
					portals = new ArrayList<Portal>();
				}
				portals.add(portal);
			}
		}
	}
	
	// TODO remove code duplicate
	/*
	 * Methode, um ein Zeichen ab einer bestimmten Position im Labyrinth zu suchen. 
	 */
	private Position getPositionFromIndex(char searchedChar, int x, int y) {
		for (int yAxis = y; yAxis < charField[x].length; yAxis++) {
			if (charField[x][yAxis] == searchedChar) {
				return new Position(x,yAxis);
			}
		}
		for (int xAxis = x + 1; xAxis < charField.length; xAxis++) {
			for (int yAxis = 0; yAxis < charField[xAxis].length; yAxis++) {
				if (charField[xAxis][yAxis] == searchedChar) {
					return new Position(xAxis,yAxis);
				}
			}
		}
		return null;
	}

	private int getHeight() {
		return charField[0].length;
	}

	private int getWidth() {
		return charField.length;
	}
	
	public int getIndexOfLastPathInFrontier() {
		return frontier.size() -1;
	}
	
	public int getClosestPathToGoal() {
		int indexOfCLosestPath = 0;
		int closestDistance = getWidth() + getHeight();
		for (int i = 0; i < frontier.size(); i++) {
			Path path = frontier.get(i);
			Position head = path.getHead();
			int distance = head.getDistance(goal);
			// Bei gleicher distanz wird der zuerst gefundene gewöhlt
			if (distance < closestDistance) {
				closestDistance = distance;
				indexOfCLosestPath = i;
			}
		}
		return indexOfCLosestPath;
	}

	public boolean isFrontshareEmpty() {
		return frontier.size() == 0;
	}
	
	public ArrayList<Path> getFrontshare() {
		return frontier;
	}
	
	public char[][] getCharField() {
		return charField;
	}
}
