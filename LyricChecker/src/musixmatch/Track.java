package musixmatch;

public class Track {
	private String name;
	private int id;
	
	public Track (String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName () {
		return name;
	}
	
	public int getId () {
		return id;
	}
	
	public String toString () {
		return getName();
	}
		
}
