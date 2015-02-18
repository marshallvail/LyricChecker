package json;
import java.util.HashMap;

public class JSONObject extends JSONValue {
	private HashMap<String, JSONValue> data;
	
	public JSONObject () {
		data = new HashMap<String, JSONValue>();
	}
	
	public void set (String key, JSONValue value) {
		data.put(key, value);
	}
	
	public JSONValue get (String key) {
		return data.get(key);
	}

	public String[] keys () {
		return data.keySet().toArray(new String[data.size()]);
	}
	
	public String toString () {
		String str = "{\n";
		String[] keys = keys();
		for (String key : keys) {
			str += "\""+key+"\": "+data.get(key)+",\n";
		}
		str += "}";
		return str;
	}
	
}
