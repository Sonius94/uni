package blindsearch;

import java.util.ArrayList;
import java.util.Arrays;

public class PrintService {
	public void printFieldStatus(Field field) {
		System.out.println("The Maze:");
		printField(field.getCharField());
		printFrontshare(field.getFrontshare());
	}
	
	public void printGoalPath(char[][] charField, Path goalPath) {
		char[][] goalField = createMazeWithPath(charField, goalPath);
		System.out.println("The Goal Maze:");
		printField(goalField);
		System.out.println();
	}
	
	public void printNoPathFound() {
		System.out.println("There is no path found in this maze");
	}
	
	/*
	 * Erstellt abhängig von einem Feld und einem Pfad ein neues Feld,
	 * auf dem der Pfad eingezeichnet ist.
	 */
	private char[][] createMazeWithPath(char[][] charField, Path goalPath) {
		char[][] fieldCopy = copyCharField(charField);
		for (int index = 1; index < goalPath.length() - 1; index++) {
			Position position = goalPath.getPositionAtIndex(index);
			Position oldPosition = goalPath.getPositionAtIndex(index - 1);
			Position nextPosition = goalPath.getPositionAtIndex(index + 1);
			
			if (oldPosition.getX() == position.getX() && position.getX() == nextPosition.getX()) {
				fieldCopy[position.getX()][position.getY()] = '\u2500';
			}
			else if (oldPosition.getY() == position.getY() && position.getY() == nextPosition.getY()) {
				fieldCopy[position.getX()][position.getY()] = '\u2502';
			}
			else if ((position.getY() + 1 == nextPosition.getY() && position.getX() + 1 == oldPosition.getX()) ||
					 (position.getY() + 1 == oldPosition.getY() && position.getX() + 1 == nextPosition.getX())) {
				fieldCopy[position.getX()][position.getY()] = '\u250c';
			}
			else if ((position.getY() - 1 == nextPosition.getY() && position.getX() + 1 == oldPosition.getX()) ||
					 (position.getY() - 1 == oldPosition.getY() && position.getX() + 1 == nextPosition.getX())) {
				fieldCopy[position.getX()][position.getY()] = '\u2510';
			}
			else if ((position.getY() + 1 == nextPosition.getY() && position.getX() - 1 == oldPosition.getX()) ||
					 (position.getY() + 1 == oldPosition.getY() && position.getX() - 1 == nextPosition.getX())) {
				fieldCopy[position.getX()][position.getY()] = '\u2514';
			}
			else if ((position.getY() - 1 == nextPosition.getY() && position.getX() - 1 == oldPosition.getX()) ||
					 (position.getY() - 1 == oldPosition.getY() && position.getX() - 1 == nextPosition.getX())) {
				fieldCopy[position.getX()][position.getY()] = '\u2518';
			}
		}
		return fieldCopy;
	}

	private char[][] copyCharField(char[][] charField) {
		char[][] newCharField = new char[charField.length][charField[0].length];
		for(int i = 0; i < charField.length; i++) {
			for(int smallIndex = 0; smallIndex < charField[0].length; smallIndex++) {
				newCharField[i][smallIndex] = charField[i][smallIndex];
			}
		}
		return newCharField;
	}

	private void printField(char[][] charField) {
		for (char[] c : charField) {
			System.out.println(c);
		}
	}
	
	private void printFrontshare(ArrayList<Path> frontshare) {
		System.out.println("The Frontshare:");
		for (int index = 0; index < frontshare.size(); index++) {
			System.out.print("Path " + Integer.toString(index) + ": ");
			frontshare.get(index).printPath();
			System.out.println();
		}
	}
}
