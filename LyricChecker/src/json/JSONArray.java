package json;
import java.util.ArrayList;

public class JSONArray extends JSONValue {
	private ArrayList<JSONValue> values;
	
	public JSONArray () {
		values = new ArrayList<JSONValue>();
	}
	
	public void add (JSONValue value) {
		values.add(value);
	}
	
	public JSONValue get (int i) {
		return values.get(i);
	}
	
	public String toString () {
		return values.toString();
	}
}
