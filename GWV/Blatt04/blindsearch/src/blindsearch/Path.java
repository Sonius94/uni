package blindsearch;

import java.util.ArrayList;

public class Path {
	ArrayList<Position> path;
	
	/*
	 * Erstellen eines Pfades, welcher nur einen Punkt enthält.
	 */
	public Path(Position startPoint) {
		path = new ArrayList<>();
		path.add(startPoint);
	}
	
	/*
	 * Erstellt einen neuen Pfad aus einer exisitieren ArrayList an Positionen.
	 */
	public Path(ArrayList<Position> path) {
		this.path = path;
	}
	
	/*
	 * Erstellt einen neuen Pfad aus einem alten Pfad und einer weiteren Position,
	 * die als Kopf angefügt wird.
	 */
	public Path(Path oldPath, Position newPosition) {
		path = new ArrayList<>(oldPath.getPathArrayList());
		path.add(newPosition);
	}

	public Position getHead() {
		return path.get(path.size() - 1);
	}
	
	public void printPath() {
		for (int index = 0; index < path.size(); index ++) {
			if(index == 0) {
				path.get(index).printPosition();
			} else {
				System.out.print(", ");
				path.get(index).printPosition();
			}
		}
	}
	
	/*
	 * @return true, falls der Pfad eine Position abdeckt.
	 */
	public boolean containsPosition(Position position ) {
		for (Position positionInPath : path) {
			if (position.getX() == positionInPath.getX() && 
					position.getY() == positionInPath.getY()) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Position> getPathArrayList() {
		return path;
	}
	
	public void addPosition(Position position) {
		path.add(position);
	}
	
	public int length() {
		return path.size();
	}

	public Position getPositionAtIndex(int index) {
		return path.get(index);
	}
}
