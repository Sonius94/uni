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
		while(!field.isFrontshareEmpty()) {
			if (field.checkPath(field.getClosestPathToGoal())) { return; };
		}
		printer.printNoPathFound();
	}
	
	// TODO reduce code duplicate
	// private void search() {}
}