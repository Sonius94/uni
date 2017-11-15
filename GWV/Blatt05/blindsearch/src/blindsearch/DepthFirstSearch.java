package blindsearch;

public class DepthFirstSearch {
	Field field;
	
	public DepthFirstSearch(Field field) {
		this.field = field;
	}
	
	public void search() {
		while(!field.isFrontshareEmpty()) {
			if (field.checkPath(field.getIndexOfLastPathInFrontshare())) { return; };
		}
		System.out.println("There is no path found in this maze");
	}
}
