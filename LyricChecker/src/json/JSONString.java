package json;

public class JSONString extends JSONValue {
	private String value;
	
	public JSONString (String value) {
		this.value = value;
	}
	
	public String getValue () {
		return value;
	}
	
	public String toString () {
		return "\"" + value + "\"";
	}
	
}
