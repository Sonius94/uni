package blindsearch;

public class Position {
	private int x;
	private int y;
	
	/*
	 * erstellt eine Position mit den Punkten x und y.
	 */
	public Position(int xPoint, int yPoint) {
		this.x = xPoint;
		this.y = yPoint;
	}

	public void printPosition() {
		System.out.print("(" + x + "," + y + ")");
	}
	
	/*
	 * Vergleichsmethode, um zwei gleiche Punkte zu identifizieren.
	 * @return true, falls x und y der Punkte gleich sind.
	 */
	public boolean isSamePositionAs(Position comparedPos) {
		if (this.x != comparedPos.getX()) { return false; }
		if (this.y != comparedPos.getY()) { return false; }
		return true;
	}
	
	public Position up() {
		return new Position(x - 1, y);
	}
	
	public Position down() {
		return new Position(x + 1, y);
	}
	
	public Position left() {
		return new Position(x, y - 1);
	}
	
	public Position right() {
		return new Position(x, y + 1);
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
