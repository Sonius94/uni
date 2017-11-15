package blindsearch;

/*
 * Ein Portal ist ein Zusammenschluss zweier Positionen. 
 */
public class Portal {
	private Position firstPortalEnding;
	private Position secondPortalEnding;
	
	public Portal(Position firstPoint, Position secondPoint) {
		firstPortalEnding = firstPoint;
		secondPortalEnding = secondPoint;
	}
	
	public Position getFirstPortalEnding() {
		return firstPortalEnding;
	}
	
	public Position getSecondPortalEnding() {
		return secondPortalEnding;
	}
}
