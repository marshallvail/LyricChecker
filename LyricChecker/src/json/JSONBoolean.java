package json;

public class JSONBoolean extends JSONValue {
	private boolean value;
	
	public JSONBoolean (boolean value) {
		this.value = value;
	}
	
	public boolean getValue () {
		return value;
	}
	
	public String toString () {
		return value ? "true" : "false";
	}

}
