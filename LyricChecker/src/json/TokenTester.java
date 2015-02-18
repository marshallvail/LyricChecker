package json;

import java.io.IOException;

public class TokenTester {
	public static void main (String[] args) {
		try {
//			JSONTokenizer t = new JSONTokenizer(System.in);
//			while (true) {
//				System.out.println(t.peek()+((t.peekValue() != null)?(": "+t.peekValue()):""));
//				t.pop();
//			}
			JSONParser p = new JSONParser(System.in);
			System.out.println(p.value());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
