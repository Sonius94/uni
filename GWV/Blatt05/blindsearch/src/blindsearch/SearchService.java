package blindsearch;

public class SearchService {
	private Field field;
	private PrintService printer;
	
	public SearchService(Field field) {
		this.field = field;
		printer = new PrintService();
	}
	
	public void depthSearch() {
		while(!field.isFrontshareEmpty()) {
			if (field.checkPath(field.getIndexOfLastPathInFrontier())) { return; };
		}
		printer.printNoPathFound();
	}
	
	public void breadthSearch() {
		while(!field.isFrontshareEmpty()) {
			if (field.checkPath(0)) { return; };
		}
		printer.printNoPathFound();
	}
	
	public void aStarSearch() {
		Position goal = field.getClosestGoal();
		while(!field.isFrontshareEmpty()) {
			if (field.checkPath(field.getClosestPathToGoal(goal))) { return; };
		}
		printer.printNoPathFound();
	}
	
	/*
	 * Shouldnt be used on too big fields.
	 */
	public void findAllPaths() {
		boolean pathFound = false;
		while(!field.isFrontshareEmpty()) {
			// Wir benutzen Tiefensuche, damit bei größeren Pfaden regelmäßig
			// ein Pfad ausgegeben wird.
			if (field.checkPath(field.getIndexOfLastPathInFrontier())) {
				pathFound = true;
			}
		}
		if (!pathFound) { printer.printNoPathFound(); }
	}
	
	// TODO reduce code duplicate
	// private void search() {}
}
