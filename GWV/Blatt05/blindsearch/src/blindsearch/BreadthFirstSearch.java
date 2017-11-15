package blindsearch;

public class BreadthFirstSearch {
	Field field;
	
	public BreadthFirstSearch(Field field) {
		this.field = field;
	}
	
	public void search() {
		while(!field.isFrontshareEmpty()) {
			if (field.checkPath(0)) { return; };
		}
		System.out.println("There is no path found in this maze");
	}
}
